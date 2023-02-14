package com.example.findfun.web.rest;


import com.example.findfun.model.Event;
import com.example.findfun.model.RestEvent;
import com.example.findfun.model.User;
import com.example.findfun.service.EventService;
import com.example.findfun.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
public class EventRestController {

    private final EventService service;
    private final UserService userService;

    public EventRestController(EventService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @GetMapping
    public List<RestEvent> findAll(){
        List<RestEvent> restEvents = new ArrayList<>();
        List<Event> events = service.findAll();
        events.forEach(event -> restEvents.add(new RestEvent(event)));
        return restEvents;
    }

    @GetMapping("/sort/recent")
    public List<RestEvent> newest(){
        List<RestEvent> restEvents = new ArrayList<>();
        List<Event> events = service.sortRecent();
        events.forEach(event -> restEvents.add(new RestEvent(event)));
        return restEvents;
    }

    @GetMapping("/sort/popular")
    public List<RestEvent> popular(){
        List<RestEvent> restEvents = new ArrayList<>();
        List<Event> events = service.sortPopular();
        events.forEach(event -> restEvents.add(new RestEvent(event)));
        return restEvents;
    }

    @GetMapping("/search/{text}")
    public List<RestEvent> search(@PathVariable String text){
        if (text==null || text.isEmpty()){
            findAll();
        }
        List<RestEvent> restEvents = new ArrayList<>();
        List<Event> events = service.searchEvents(text);
        events.forEach(event -> restEvents.add(new RestEvent(event)));
        return restEvents;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestEvent> findById(@PathVariable Long id) {
        Event event = service.findById(id).get();
        RestEvent restEvent = new RestEvent(event);
        return Optional.of(restEvent)
                .map(e-> ResponseEntity.ok().body(e))
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @GetMapping("/invitations/{username}")
    public List<RestEvent> invitations(@PathVariable String username){
        User user = (User) userService.findByUsername(username);
        List<RestEvent> restEvents = new ArrayList<>();
        List<Event> events = user.getInvitedEvents();
        events.forEach(event -> restEvents.add(new RestEvent(event)));
        return restEvents;
    }
}
