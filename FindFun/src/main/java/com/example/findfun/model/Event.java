package com.example.findfun.model;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String about;

    private String imgPath;

    private Double lat;

    private Double lng;

    private String category;

    private Long rating;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime date;

    @ManyToOne
    private User createdUser;

    @ManyToMany
    private List<User> invitedUsers;

    @ManyToMany()
    private List<User> interestedUsers;

    @OneToMany(mappedBy = "event", fetch = FetchType.EAGER)
    private List<Comment> comments;

    public Event(String name, String about, String imgPath, Double lat, Double lng, LocalDateTime date, User createdUser, String category) {
        this.name = name;
        this.about = about;
        this.imgPath = imgPath;
        this.lat = lat;
        this.lng = lng;
        this.date = date;
        this.category = category;
        this.createdUser = createdUser;
        this.invitedUsers = new ArrayList<>();
        this.interestedUsers = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public Event(Long id, String name, String about, String imgPath, Double lat, Double lng, String category, Long rating, LocalDateTime date, User createdUser, List<User> invitedUsers, List<User> interestedUsers, List<Comment> comments) {
        this.id = id;
        this.name = name;
        this.about = about;
        this.imgPath = imgPath;
        this.lat = lat;
        this.lng = lng;
        this.category = category;
        this.rating = rating;
        this.date = date;
        this.createdUser = createdUser;
        this.invitedUsers = invitedUsers;
        this.interestedUsers = interestedUsers;
        this.comments = comments;
    }

    public Event(String name, String about, String imgPath, Double lat, Double lng) {
        this.name = name;
        this.about = about;
        this.imgPath = imgPath;
        this.lat = lat;
        this.lng = lng;
    }

    public Event() {

    }

    public boolean isOver(){
        if (date == null) return false;
        return date.isBefore(LocalDateTime.now());
    }

    public boolean isNotOver(){
        return !this.isOver();
    }

    public int getPopularity(){
        if (this.getInterestedUsers() == null) return 0;
        return this.getInterestedUsers().size();
    }

    public int recent(){
        return LocalDateTime.now().compareTo(date);
    }

}
