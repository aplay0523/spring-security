package com.api.dataHub.service;

import com.api.dataHub.common.ErrorCode;
import com.api.dataHub.common.exception.BusinessException;
import com.api.dataHub.common.repository.UserRepository;
import com.api.dataHub.controller.entity.User;
import com.api.dataHub.controller.vo.request.UserDetailDto;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public User createUser(User user) {
        Optional<User> getUser = userRepository.findByUserId(user.getUserId());

        if (getUser.isPresent()) {
            throw new BusinessException(ErrorCode.CONFLICT);
        }

        return userRepository.save(user);
    }

    /** id 로 검색 **/
    public Optional<User> getUserById(String id) {
        return userRepository.findByUserId(id);
    }

    @Transactional
    public User updateUser(UserDetailDto userDetailDto) {
        return userRepository.findByUserId(userDetailDto.getUserId())
                .map(setUser -> {
                    try {
                        objectMapper.updateValue(setUser, userDetailDto);
                    } catch (JsonMappingException e) {
                        throw new BusinessException(ErrorCode.BAD_REQUEST);
                    }
                    return setUser;
                }).orElseThrow(() -> new RuntimeException("사용자 ID: " + userDetailDto.getUserId() + " 에 해당하는 사용자를 찾을 수 없습니다."));
    }

    public void deleteByUserId(String id) {
        Optional<User> user = userRepository.findByUserId(id);
        userRepository.deleteById(user.get().getUuid());
    }

    /** uuid 로 검색 **/
    public Optional<User> getUserByUuid(UUID uuid) {
        return userRepository.findById(uuid);
    }

    @Transactional
    public User updateByUserUuid(UserDetailDto userDetailDto) {
        return userRepository.findById(userDetailDto.getUuid())
                .map(setUser -> {
                    try {
                        objectMapper.updateValue(setUser, userDetailDto);
                    } catch (JsonMappingException e) {
                        throw new BusinessException(ErrorCode.BAD_REQUEST);
                    }
                    return userRepository.save(setUser); // 명시적 호출
                }).orElseThrow(() -> new RuntimeException("사용자 ID: \" + userDetailDto.getUserId() + \" 에 해당하는 사용자를 찾을 수 없습니다."));
    }

    public void deleteByUserUuid(UUID uuid) {
        userRepository.deleteById(uuid);
    }
}
