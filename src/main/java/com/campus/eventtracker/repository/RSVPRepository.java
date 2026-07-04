package com.campus.eventtracker.repository;

import com.campus.eventtracker.model.RSVP;
import com.campus.eventtracker.model.Event;
import com.campus.eventtracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RSVPRepository extends JpaRepository<RSVP, Long> {
    Optional<RSVP> findByEventAndUser(Event event, User user);
    List<RSVP> findByUser(User user);
    List<RSVP> findByEvent(Event event);
    boolean existsByEventAndUser(Event event, User user);
}