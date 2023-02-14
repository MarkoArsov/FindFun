package com.example.comments.service;

import com.example.comments.model.Comment;

import java.util.List;

public interface CommentService {
    public Comment save(String text, Long userId, String userName, Long eventId);

    public List<Comment> findAllByEventId(Long eventId);
}
