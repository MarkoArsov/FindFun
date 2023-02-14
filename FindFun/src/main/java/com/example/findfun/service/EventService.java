package com.example.findfun.service;

import com.example.findfun.VO.LocalComment;
import com.example.findfun.model.Comment;
import com.example.findfun.model.Event;
import com.example.findfun.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventService {

    List<Event> findAll();

    Optional<Event> findById(Long id);

    Optional<Event> save(Long id, String name, String about, String imgPath, Double lat, Double lng, LocalDateTime date, User createdUser, String category);

    List<Event> searchEvents(String text);

    List<Event> findAllByName(String name);

    void addInterestedUserToEvent(Event event, User user);

    void addInvitedUserToEvent(Event event, User user);

    List<Event> findInvites(User user);

    List<Event> sortRecent();

    List<Event> sortPopular();
    void rateEvent(Event event, Long rating);

    public List<Event> findAllOver();

    void delete(Long id);

    List<Event> findAllByCreatedUser(User user);

    List<LocalComment> getComments(Long eventId);

}
