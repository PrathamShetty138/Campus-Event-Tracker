package com.campus.eventtracker.repository;

import com.campus.eventtracker.model.Reminder;
import com.campus.eventtracker.model.RSVP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    List<Reminder> findByReminderTimeBeforeAndSentFalse(LocalDateTime now);
    List<Reminder> findByRsvp(RSVP rsvp);
    void deleteByRsvp(RSVP rsvp);
}