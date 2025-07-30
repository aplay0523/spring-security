package com.api.dataHub.common.repository;

import com.api.dataHub.controller.entity.User;
import com.api.dataHub.controller.vo.request.UserDetailDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUserId(String userId);

    List<User> findByGroupRoleAndUseYn(String groupRole, String useYn);

}
