package com.example.findfun.service.impl;

import com.example.findfun.model.Comment;
import com.example.findfun.model.Event;
import com.example.findfun.model.User;
import com.example.findfun.repository.CommentRepository;
import com.example.findfun.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository repository;

    public CommentServiceImpl(CommentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Comment> findAll() {
        return repository.findAll();
    }

    public Optional<Comment> save(String content, User user, Event event) {
        Comment comment = new Comment(content,user,event);
        return Optional.of(repository.save(comment));
    }
}
