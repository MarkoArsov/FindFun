package com.example.findfun.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class RestEvent {

    private Long id;

    private String name;

    private String about;

    private String imgPath;

    private Double lat;

    private Double lng;

    private String date;

    private String created;

    private int interested;

    public RestEvent(String name, String about, String imgPath, Double lat, Double lng, LocalDateTime date) {
        this.name = name;
        this.about = about;
        this.imgPath = imgPath;
        this.lat = lat;
        this.lng = lng;
        this.date = date.toString();
    }

    public RestEvent(Event event){
        this.id = event.getId();
        this.name = event.getName();
        this.about = event.getAbout();
        this.imgPath = event.getImgPath();
        this.lat = event.getLat();
        this.lng = event.getLng();
        this.created = event.getCreatedUser().getUsername();
        this.interested = event.getInterestedUsers().size();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, d MMM yyyy hh:mm");
        this.date = event.getDate().format(formatter);
    }

}
