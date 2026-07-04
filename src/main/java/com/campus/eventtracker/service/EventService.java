package com.campus.eventtracker.service;

import com.campus.eventtracker.model.Event;
import com.campus.eventtracker.model.User;
import com.campus.eventtracker.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    
    public Event createEvent(Event event, User user) {
        event.setCreatedBy(user);
        return eventRepository.save(event);
    }
    
    public List<Event> getUpcomingEvents() {
        return eventRepository.findByDateAfterOrderByDateAsc(LocalDate.now().minusDays(1));
    }

    public List<Event> getRecentPastEvents() {
        // Events from the last 30 days that have already passed
        return eventRepository.findByDateBetweenOrderByDateDesc(
            LocalDate.now().minusDays(30), LocalDate.now().minusDays(1));
    }
    
    public List<Event> getEventsByType(String type) {
        return eventRepository.findByTypeAndDateAfterOrderByDateAsc(type, LocalDate.now().minusDays(1));
    }
    
    public List<Event> searchEvents(String query) {
        return eventRepository.findByTitleContainingIgnoreCaseAndDateAfterOrderByDateAsc(query, LocalDate.now().minusDays(1));
    }
    
    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }
    
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}