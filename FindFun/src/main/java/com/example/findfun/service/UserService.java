package com.example.findfun.service;

import com.example.findfun.model.Role;
import com.example.findfun.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    User register(String username, String password, String repeatPassword, String email, Role role);

    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);

    User update(User user);

    List<User> findAll();


    User addFriend(String username1, String username2);

    boolean isUserInList(String username, List<User> userList);

    void deleteFriend(User user, String username);
}
