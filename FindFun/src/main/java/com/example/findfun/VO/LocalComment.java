package com.example.findfun.VO;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class LocalComment {

    private Long id;

    private String content;

    private LocalDateTime dateCreated;

    private Long userId;

    private String userName;

    private Long eventId;

    public LocalComment(Long id, String content, LocalDateTime dateCreated, Long userId, String userName, Long eventId) {
        this.id = id;
        this.content = content;
        this.dateCreated = dateCreated;
        this.userId = userId;
        this.userName = userName;
        this.eventId = eventId;
    }

    public LocalComment(){

    }

    public String printDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, d MMM yyyy hh:mm");
        return dateCreated.format(formatter);
    }
}
