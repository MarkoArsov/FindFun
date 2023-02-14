package com.example.comments.service.impl;

import com.example.comments.model.Comment;
import com.example.comments.repository.CommentRepository;
import com.example.comments.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;

    public CommentServiceImpl(CommentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Comment save(String text, Long userId, String userName, Long eventId) {
        return repository.save(new Comment(text, userId, userName, eventId));
    }

    @Override
    public List<Comment> findAllByEventId(Long eventId) {
        return repository.findAllByEventId(eventId);
    }
}
