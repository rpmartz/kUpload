package com.ryanpmartz.kupload.service;

import com.ryanpmartz.kupload.domain.User;
import com.ryanpmartz.kupload.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

}
