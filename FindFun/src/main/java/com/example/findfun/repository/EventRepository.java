package com.example.findfun.repository;

import com.example.findfun.model.Event;
import com.example.findfun.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAll();

    Optional<Event> findById(Long id);

    List<Event> findAllByCreatedUser(User user);

    List<Event> findAllByNameContainingOrAboutContaining(String name, String about);

    Optional<Event> findByName(String name);

    List<Event> findAllByName(String name);

}
