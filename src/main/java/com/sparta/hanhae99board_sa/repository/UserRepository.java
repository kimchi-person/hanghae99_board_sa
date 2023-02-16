package com.sparta.hanhae99board_sa.repository;

import com.sparta.hanhae99board_sa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

}
