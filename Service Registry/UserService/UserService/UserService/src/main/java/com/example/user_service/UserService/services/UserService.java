package com.example.user_service.UserService.services;

import com.example.user_service.UserService.entities.User;

import java.util.List;

public interface UserService {

    //create
    User saveUser(User user);

    //get all users
    List<User> getAllUser();

    //get single user of given userId
    User getUser(String userId);
}
