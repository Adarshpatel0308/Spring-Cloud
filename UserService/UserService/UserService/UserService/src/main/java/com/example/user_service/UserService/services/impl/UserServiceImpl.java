package com.example.user_service.UserService.services.impl;

import com.example.user_service.UserService.entities.User;
import com.example.user_service.UserService.exception.ResourceNotFoundException;
import com.example.user_service.UserService.repositories.UserRepositories;
import com.example.user_service.UserService.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepositories userRepositories;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public User saveUser(User user) {
        //Generate the unique UserId by using the (UUID) class which is present in util package
        String randomUserId = UUID.randomUUID().toString();
        logger.info("updating user id");
        user.setUserId(randomUserId);
        logger.info("user id updated");
        logger.info("user details {} {} {}", user.getName(), user.getEmail(), user.getUserId(), user.getAbout());
        return userRepositories.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepositories.findAll();
    }

    @Override
    public User getUser(String userId) {
        return userRepositories.findById(userId).orElseThrow(()->new ResourceNotFoundException("User with given Id is not found on server"+userId));
    }
}

