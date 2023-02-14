package com.example.findfun.web.controller;


import com.example.findfun.model.Event;
import com.example.findfun.model.User;
import com.example.findfun.service.CommentService;
import com.example.findfun.service.EventService;
import com.example.findfun.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/events")
public class EventController {

    private final EventService service;
    private final UserService userService;
    private final CommentService commentService;

    public EventController(EventService service, UserService userService, CommentService commentService) {
        this.service = service;
        this.userService = userService;
        this.commentService = commentService;
    }

    @GetMapping
    public String allEvents(Model model, Authentication authentication, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (authentication != null && user == null) {
            String username = authentication.getName();
            user = userService.findByUsername(username);
            request.getSession().setAttribute("user", user);
        }
        model.addAttribute("searchText", "");
        model.addAttribute("sortText", "all");
        return "home";
    }

    @GetMapping("/{id}")
    public String eventById(@PathVariable Long id, Model model, HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");

        Event event = null;
        boolean interested = false;
        if (service.findById(id).isPresent()) {
            event = service.findById(id).get();
            interested = event.getInterestedUsers().contains(user);
        }

        model.addAttribute("interested", interested);
        model.addAttribute("event", event);

        model.addAttribute("eventId", event.getId());
        model.addAttribute("eventDate", event.getDate());
        model.addAttribute("comments", service.getComments(event.getId()));
        model.addAttribute("currentUser", user);
        return "event";
    }


    @GetMapping("/edit/{id}")
    public String editEventPage(@PathVariable Long id, Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        Event event = service.findById(id).get();
        if (!user.equals(event.getCreatedUser())) {
            return "redirect:/events";
        }
        model.addAttribute("event", event);
        return "createEvent";
    }

    @GetMapping("/delete/{id}")
    public String deleteEventPage(@PathVariable Long id, Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        Event event = service.findById(id).get();
        if (!user.equals(event.getCreatedUser())) {
            return "redirect:/events";
        }
        service.delete(id);
        request.getSession().setAttribute("user", userService.findByUsername(user.getUsername()));
        return "redirect:/events";
    }

    @GetMapping("/add")
    public String addEventPage() {
        return "createEvent";
    }

    @GetMapping("/invite/{id}")
    public String invitePage(@PathVariable Long id, HttpServletRequest request, Model model) {
        Event event;
        if (service.findById(id).isPresent()) {
            event = service.findById(id).get();
        } else {
            return "redirect:/events";
        }

        User user = (User) request.getSession().getAttribute("user");
        List<User> friends = user.getFriends();
        List<User> invitedFriends = event.getInvitedUsers();


        for (int i = 0; i < friends.size(); i++) {
            for (User invitedFriend : invitedFriends) {
                if (friends.get(i).getUsername().equals(invitedFriend.getUsername())) {
                    friends.remove(i);
                    i--;
                    break;
                }
            }
        }
        model.addAttribute("notInvitedFriends", friends);
        model.addAttribute("invitedFriends", invitedFriends);
        model.addAttribute("eventId", id);

        return "invite";
    }

    @PostMapping("/add_friend")
    public String addFriend(@RequestParam Long id,
                            @RequestParam String friendUsername, HttpServletRequest request) {
        Event event = service.findById(id).get();
        User friend = userService.findByUsername(friendUsername);
        service.addInvitedUserToEvent(event, friend);

        return "redirect:/events/invite/" + id;
    }

    @GetMapping("/sort/{sortText}")
    public String sort(@PathVariable String sortText, Model model) {
        model.addAttribute("searchText", "");
        model.addAttribute("sortText", sortText);
        return "home";
    }

    @PostMapping("/add")
    public String addEvent(@RequestParam Long id,
                           @RequestParam Double lat,
                           @RequestParam Double lng,
                           @RequestParam String name,
                           @RequestParam String about,
                           @RequestParam String category,
                           @RequestParam String imgPath,
                           @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
                           HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");

        service.save(id, name, about, imgPath, lat, lng, date, user, category);

        request.getSession().setAttribute("user", userService.findByUsername(user.getUsername()));
        if (id == 0) id = service.findAllByName(name).get(0).getId();

        return "redirect:/events/invite/" + id;
    }

    @PostMapping("/search")
    public String search(@RequestParam String text, Model model) {
        model.addAttribute("searchText", text);
        model.addAttribute("sortText", "all");
        return "home";
    }

    @GetMapping("/myEvents")
    public String myEvents(Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        List<Event> events = service.findAllByCreatedUser(user);
        model.addAttribute("user",user);
        model.addAttribute("events", events);
        return "myEvents";
    }

    @PostMapping("/comment")
    public String addComment(@RequestParam String comment,
                             @RequestParam Long eventID,
                             HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");
        Event event = service.findById(eventID).get();
        commentService.save(comment, user, event);
        return "redirect:/events/" + eventID.toString();
    }

    @GetMapping("/interested/{id}")
    public String interested(@PathVariable Long id,
                             HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");

        Event event = null;
        if (service.findById(id).isPresent()) {
            event = service.findById(id).get();
        }

        service.addInterestedUserToEvent(event, user);

        request.getSession().setAttribute("user", userService.findByUsername(user.getUsername()));

        return "redirect:/events/" + id.toString();
    }

    @GetMapping("/rating")
    public String ratingPage(HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        request.getSession().setAttribute("user", userService.findByUsername(user.getUsername()));
        List<Event> interestedEvents = user.getInterestedEvents();
        List<Event> rateEvents = new ArrayList<>();
        for (Event interestedEvent : interestedEvents) {
            if (interestedEvent.getDate().isBefore(LocalDateTime.now())) {
                rateEvents.add(interestedEvent);
            }
        }
        model.addAttribute("events", rateEvents);
        return "rating";
    }

    @PostMapping("/rated")
    public String ratedEvent(@RequestParam String rate, @RequestParam Long id, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        Long RATE = Long.parseLong(rate);
        Event event = service.findById(id).get();
        service.rateEvent(event, RATE);
        request.getSession().setAttribute("user", userService.findByUsername(user.getUsername()));

        return "redirect:/events/rating";
    }

    @GetMapping("/invitations")
    public String invitation() {
        return "invitations";
    }

}
