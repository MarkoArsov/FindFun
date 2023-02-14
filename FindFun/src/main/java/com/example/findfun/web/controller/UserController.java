package com.example.findfun.web.controller;

import com.example.findfun.model.Event;
import com.example.findfun.model.User;
import com.example.findfun.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public String userProfile(Model model, Authentication authentication, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (authentication != null && user == null) {
            String username = authentication.getName();
            user = userService.findByUsername(username);
            request.getSession().setAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("searchUser", "");
        return "mainProfile";
    }



    @GetMapping("/addFriend/add/{username}")
    public String addFriend(@PathVariable String username, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        userService.addFriend(user.getUsername(),username);
        User user1 = userService.findByUsername(user.getUsername());
        request.getSession().setAttribute("user", user1);
        return "redirect:/user/listFriends";
    }

    @GetMapping("/listFriends")
    public String listFriends(Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        List<User> friends = user.getFriends();
        model.addAttribute("user", user);
        model.addAttribute("friends", friends);
        return "listFriends";
    }

    @PostMapping("/addFriend")
    public String search(Model model, HttpServletRequest request, @RequestParam String searchUser) {
        User user = (User) request.getSession().getAttribute("user");
        User user1 = userService.findByUsername(searchUser);
        List<User> usersList = new ArrayList<>();

        if (searchUser != null && !searchUser.isEmpty() && !searchUser.equals(user.getUsername()) && !user.getFriends().contains(user1)) {
            usersList.add(user1);
        }
        model.addAttribute("user", user);
        model.addAttribute("usersList", usersList);
        return "addFriend";
    }

    @GetMapping("/addFriend")
    public String addFriendPage(Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("searchUser", "");
        return "addFriend";
    }

    @GetMapping("/listFriends/delete/{username}")
    public String deleteFriend(@PathVariable String username, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        userService.deleteFriend(user,username);
        User user1 = userService.findByUsername(user.getUsername());
        request.getSession().setAttribute("user", user1);
        return "redirect:/user/listFriends";
    }


}
