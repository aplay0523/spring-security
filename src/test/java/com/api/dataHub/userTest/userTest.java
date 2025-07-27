package com.api.dataHub.userTest;

import com.api.dataHub.common.repository.UserRepository;
import com.api.dataHub.controller.entity.User;
import com.api.dataHub.service.AuthService;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

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
        user.setUserName("관리자01");
        user.setUserPwd(passwordEncoder.encode(pwd));
        user.setGroupRole("ROLE_ADMIN");

        userRepository.save(user);

        Optional<User> saved = userRepository.findByUserId("admin02");

        assertThat(passwordEncoder.matches(pwd, saved.get().getUserPwd())).isTrue();
        assertThat(saved.get().getGroupRole()).isEqualTo("ROLE_ADMIN");

        System.out.println("암호화된 비밀번호: " + saved.get().getUserPwd());
    }

}
