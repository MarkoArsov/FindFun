package com.example.findfun.model;


import javax.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "event_users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String email;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> friends;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdTime;

    @OneToMany(mappedBy = "createdUser")
    private List<Event> createdEvents;

    @ManyToMany(mappedBy = "invitedUsers", fetch = FetchType.EAGER)
    private List<Event> invitedEvents;

    @ManyToMany(mappedBy = "interestedUsers", fetch = FetchType.EAGER)
    private List<Event> interestedEvents;

    private boolean isAccountNonExpired = true;

    private boolean isAccountNonLocked = true;

    private boolean isCredentialsNonExpired = true;

    private boolean isEnabled = true;

    public User(String username, String password, String email, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        createdTime = LocalDateTime.now();
    }

    public User() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }


    public double getRating(){
        List<Event> events = new ArrayList<>();
        for (Event interestedEvent : interestedEvents) {
            if (interestedEvent.isOver()){
                events.add(interestedEvent);
            }
        }
        double ratingSum = 0.0;
        int count = 0;
        for (Event event : events) {
            if (event.getRating()!=null){
                ratingSum+=event.getRating();
                count++;
            }
        }
        return ratingSum/count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return this.getUsername().equals(user.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, email, role, friends, createdTime, createdEvents, invitedEvents, interestedEvents, isAccountNonExpired, isAccountNonLocked, isCredentialsNonExpired, isEnabled);
    }


}
