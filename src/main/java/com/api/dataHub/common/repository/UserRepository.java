package com.api.dataHub.common.repository;

import com.api.dataHub.controller.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUserId(String userId);
}
