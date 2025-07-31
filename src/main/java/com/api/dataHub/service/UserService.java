package com.api.dataHub.service;

import com.api.dataHub.common.ErrorCode;
import com.api.dataHub.common.exception.BusinessException;
import com.api.dataHub.common.repository.UserRepository;
import com.api.dataHub.controller.entity.User;
import com.api.dataHub.controller.vo.request.UserDetailDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(User user) {
        Optional<User> getUser = userRepository.findByUserId(user.getUserId());

        if (getUser.isPresent()) {
            throw new BusinessException(ErrorCode.CONFLICT);
        }

        return userRepository.save(user);
    }

    /**
     * id 로 검색
     **/
    public Optional<User> getUserById(String id) {
        return userRepository.findByUserId(id);
    }

    @Transactional
    public void updateUser(UserDetailDto userDetailDto) {
        userRepository.findByUserId(userDetailDto.getUserId())
                .map(setUser -> {
                    try {

                        if (userDetailDto.getUserPwd() != null && userDetailDto.getUserPwd().isEmpty()) {
                            userDetailDto.setUserPwd(
                                    passwordEncoder.encode(userDetailDto.getUserPwd())
                            );
                        }

                        objectMapper.updateValue(setUser, userDetailDto);
                    } catch (JsonMappingException e) {
                        throw new BusinessException(ErrorCode.BAD_REQUEST);
                    }
                    return setUser;
                }).orElseThrow(() -> new RuntimeException(
                        "사용자 ID: " + userDetailDto.getUserId() + " 에 해당하는 사용자를 찾을 수 없습니다."));
    }

    public void deleteByUserId(String id) {
        Optional<User> user = userRepository.findByUserId(id);
        userRepository.deleteById(user.get().getUuid());
    }

    /**
     * uuid 로 검색
     **/
    public UserDetailDto getDetailUserByUuid(UUID uuid) {
        User user = userRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다."));
        return new UserDetailDto(user);
    }

    public List<UserDetailDto> getDetailUserList(String groupRole, String useYn) {
        List<User> userList = userRepository.findByGroupRoleAndUseYn(groupRole, useYn);
        return userList.stream()
                .map(user -> new UserDetailDto(user))
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateByUserUuid(UserDetailDto userDetailDto) {
        userRepository.findById(userDetailDto.getUuid())
                .map(setUser -> {
                    try {

                        if (userDetailDto.getUserPwd() != null && userDetailDto.getUserPwd().isEmpty()) {
                            userDetailDto.setUserPwd(
                                    passwordEncoder.encode(userDetailDto.getUserPwd())
                            );
                        }

                        objectMapper.updateValue(setUser, userDetailDto);
                    } catch (JsonMappingException e) {
                        throw new BusinessException(ErrorCode.BAD_REQUEST);
                    }
                    return userRepository.save(setUser); // 명시적 호출
                }).orElseThrow(() -> new RuntimeException(
                        "사용자 ID: \" + userDetailDto.getUserId() + \" 에 해당하는 사용자를 찾을 수 없습니다."));
    }

    public void deleteByUserUuid(UUID uuid) {
        userRepository.findById(uuid)
                .map(user -> {
                    user.setUseYn("N");
                    return user;
                }).orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다."));
    }
}
