package com.doni.talk.user.repository;

import com.doni.talk.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySnsId(String snsId);
    Optional<User> findByPhone(String phone);
}
