package com.example.comments.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateCreated;

    private Long userId;

    private String userName;

    private Long eventId;

    public Comment(String content, Long user, String userName, Long event) {
        this.content = content;
        this.userId = user;
        this.userName = userName;
        this.eventId = event;
        dateCreated = LocalDateTime.now();
    }

    public Comment() {

    }

    public String printDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, d MMM yyyy hh:mm");
        return dateCreated.format(formatter);
    }

}
