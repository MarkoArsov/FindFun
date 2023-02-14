package com.example.findfun.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateCreated;

    @ManyToOne
    private User user;

    @ManyToOne
    private Event event;

    public Comment(String content, User user, Event event) {
        this.content = content;
        this.user = user;
        this.event = event;
        dateCreated = LocalDateTime.now();
    }

    public Comment() {

    }

    public String printDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, d MMM yyyy hh:mm");
        return dateCreated.format(formatter);
    }
}
