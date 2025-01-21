package com.doni.talk.user.service;

import com.doni.talk.user.domain.User;
import com.doni.talk.user.dto.request.UserSignUpDTO;
import com.doni.talk.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j @Service @NoArgsConstructor @AllArgsConstructor
public class UserServiceI implements UserService {
    @Autowired private UserRepository repository;

    @Override
    public User getUserBySnsId(String snsId) {
        Optional<User> user = repository.findBySnsId(snsId);
        return user.orElse(null);
    }

    @Override @Transactional
    public User join(UserSignUpDTO postInfo) {
        return repository.save(postInfo.to());
    }
}
