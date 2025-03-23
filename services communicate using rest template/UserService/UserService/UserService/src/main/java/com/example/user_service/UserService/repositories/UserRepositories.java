package com.example.user_service.UserService.repositories;

import com.example.user_service.UserService.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositories extends JpaRepository<User,String> {
}
