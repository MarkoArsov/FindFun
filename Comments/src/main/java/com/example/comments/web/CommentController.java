package com.example.comments.web;

import com.example.comments.model.Comment;
import com.example.comments.service.CommentService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @PostMapping
    public void saveComment(@RequestParam Long eventID, @RequestParam Long userID, @RequestParam String userName, @RequestParam String comment, HttpServletResponse response) throws IOException {
        service.save(comment, userID, userName, eventID);
        String url = "http://localhost:8085/events/" + eventID.toString();
        response.sendRedirect(url);
    }

    @GetMapping("/{id}")
    public List<Comment> getComments(@PathVariable Long id){
        return service.findAllByEventId(id);
    }

}