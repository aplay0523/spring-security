package com.api.dataHub.userTest;

import com.api.dataHub.common.repository.UserRepository;
import com.api.dataHub.controller.entity.User;
import com.api.dataHub.security.provider.JwtTokenProvider;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class JwtProviderTest {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;


    @Test
    void createToken_validateToken() {

        Optional<User> opUser = userRepository.findByUserId("admin");

        User user = opUser.get();

        String token = jwtTokenProvider.createToken(user);

        String userId = jwtTokenProvider.getUserId(token);

        boolean lean = jwtTokenProvider.validateToken(token);

        System.out.print(lean);
        assertEquals(user.getUserId(), userId);
        assertTrue(lean);
    }
}
