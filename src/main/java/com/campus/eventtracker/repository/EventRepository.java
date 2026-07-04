package com.campus.eventtracker.repository;

import com.campus.eventtracker.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByDateAfterOrderByDateAsc(LocalDate date);
    List<Event> findByTypeAndDateAfterOrderByDateAsc(String type, LocalDate date);
    List<Event> findByTitleContainingIgnoreCaseAndDateAfterOrderByDateAsc(String title, LocalDate date);
    List<Event> findByDateBetweenOrderByDateDesc(LocalDate from, LocalDate to);
}