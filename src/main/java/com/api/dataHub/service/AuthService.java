package com.api.dataHub.service;

import com.api.dataHub.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        return userRepository.findByUserId(userId)
                .map(user -> {
                    logger.debug(">>> userId: {}", user.getUserId());
                    logger.debug(">>> encoded password from DB: {}", user.getUserPwd());

                    if ("N".equalsIgnoreCase(user.getUseYn())) {
                        throw new DisabledException("비활성화된 사용자입니다.");
                    }
                    return user;
                })

                .orElseThrow(() -> new BadCredentialsException("회원정보를 확인해주세요."));
    }
}
