package com.example.findfun.service;

import com.example.findfun.model.Comment;
import com.example.findfun.model.Event;
import com.example.findfun.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CommentService {
    List<Comment> findAll();

    Optional<Comment> save(String content, User user,Event event);
}
