package com.example.findfun.service.impl;

import com.example.findfun.VO.LocalComment;
import com.example.findfun.model.Comment;
import com.example.findfun.model.Event;
import com.example.findfun.model.User;
import com.example.findfun.repository.CommentRepository;
import com.example.findfun.repository.EventRepository;
import com.example.findfun.service.EventService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository repository;
    private final CommentRepository commentRepository;
    private final RestTemplate restTemplate;

    public EventServiceImpl(EventRepository repository, CommentRepository commentRepository, RestTemplate restTemplate) {
        this.repository = repository;
        this.commentRepository = commentRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Event> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Event> findAllOver() {
        return repository.findAll().stream().filter(Event::isOver).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        Event event = this.findById(id).get();
        repository.delete(event);
    }

    @Override
    public Optional<Event> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Event> save(Long id, String name, String about, String imgPath, Double lat, Double lng, LocalDateTime date, User createdUser, String category) {

        Event event = null;

        if (id != 0){
            event = repository.findById(id).get();
            Long rating = event.getRating();
            User user = event.getCreatedUser();
            List<User> invited = event.getInvitedUsers();
            List<User> interested = event.getInterestedUsers();
            List<Comment> comments = event.getComments();
            repository.delete(event);
            event = new Event(id, name, about, imgPath, lat, lng, category, rating, date, user, invited, interested, comments);
        }
        else {
            event = new Event(name, about, imgPath, lat, lng, date, createdUser, category);
        }

        return Optional.of(repository.save(event));
    }


    @Override
    public List<Event> searchEvents(String text) {
        return repository.findAllByNameContainingOrAboutContaining(text, text);
    }

    @Override
    public List<Event> findAllByName(String name) {
        return repository.findAllByName(name);
    }

    @Override
    public void addInterestedUserToEvent(Event event, User user) {

        if (event == null || user == null){
            throw  new IllegalArgumentException();
        }

        List<User> users = event.getInterestedUsers();

        for (User user1 : users) {
            if (user1.getUsername().equals(user.getUsername())){
                return;
            }
        }

        event.getInterestedUsers().add(user);

        repository.save(event);
    }

    @Override
    public void addInvitedUserToEvent(Event event, User user) {
        if (event == null || user == null){
            throw  new IllegalArgumentException();
        }
        event.getInvitedUsers().add(user);
        repository.save(event);
    }

    @Override
    public List<Event> findInvites(User user) {
        return null;
    }

    @Override
    public List<Event> sortRecent() {
        List<Event> events = repository.findAll().stream().filter(event -> !(event.getDate() == null)).toList();
        events = events.stream().filter(Event::isNotOver).collect(Collectors.toList());
        return events.stream().sorted(Comparator.comparing(Event::recent, Comparator.reverseOrder())).collect(Collectors.toList());
    }

    @Override
    public List<Event> sortPopular() {
        return repository.findAll().stream().sorted(Comparator.comparing(Event::getPopularity, Comparator.reverseOrder())).collect(Collectors.toList());
    }

    @Override
    public void rateEvent(Event event, Long rating) {
        event.setRating(rating);
        repository.save(event);
    }


    @Override
    public List<Event> findAllByCreatedUser(User user){
        return repository.findAllByCreatedUser(user);
    }

    @Override
    public List<LocalComment> getComments(Long eventId) {
        String url = "http://comments:8081/comments/" + eventId;
        LocalComment[] commentsArr = restTemplate.getForObject(url, LocalComment[].class);
        List<LocalComment> comments = new ArrayList<>(Arrays.asList(commentsArr));
        return comments.stream().sorted(Comparator.comparing(LocalComment::getDateCreated, Comparator.reverseOrder())).collect(Collectors.toList());
    }
}
