package com.api.dataHub.userTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.api.dataHub.common.repository.UserRepository;
import com.api.dataHub.controller.entity.User;
import com.api.dataHub.controller.vo.request.UserDetailDto;
import com.api.dataHub.service.AuthService;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class userTest {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void userSave() {
        String pwd = "admin";
        User user = new User();
        user.setUserId("admin02");
        user.setName("관리자01");
        user.setUserPwd(passwordEncoder.encode(pwd));
        user.setGroupRole("ROLE_ADMIN");

        userRepository.save(user);

        Optional<User> saved = userRepository.findByUserId("admin02");

        assertThat(passwordEncoder.matches(pwd, saved.get().getUserPwd())).isTrue();
        assertThat(saved.get().getGroupRole()).isEqualTo("ROLE_ADMIN");

        System.out.println("암호화된 비밀번호: " + saved.get().getUserPwd());
    }

    @Test
    void userUpdate() {
        UUID uuid = UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11");
        User user = userRepository.findById(uuid).get();

        user.setName("adminTest");
        userRepository.save(user);

        User detailUser = userRepository.findById(uuid).orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다."));

        UserDetailDto userDetailDto = new UserDetailDto(detailUser);

        assertEquals(userDetailDto.getName(), "adminTest");

    }
}
