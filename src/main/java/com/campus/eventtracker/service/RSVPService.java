package com.campus.eventtracker.service;

import com.campus.eventtracker.model.RSVP;
import com.campus.eventtracker.model.Event;
import com.campus.eventtracker.model.User;
import com.campus.eventtracker.repository.RSVPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RSVPService {
    @Autowired
    private RSVPRepository rsvpRepository;
    
    @Autowired
    private ReminderService reminderService;
    
    public RSVP createRSVP(Event event, User user) {
        if (rsvpRepository.existsByEventAndUser(event, user)) {
            return null;
        }
        RSVP rsvp = new RSVP(event, user);
        RSVP savedRsvp = rsvpRepository.save(rsvp);
        
        // Create reminder 24 hours before event
        reminderService.createReminder(savedRsvp, 24);
        
        return savedRsvp;
    }
    
    public void cancelRSVP(Event event, User user) {
        rsvpRepository.findByEventAndUser(event, user).ifPresent(rsvp -> {
            // Delete associated reminders
            reminderService.deleteRemindersForRSVP(rsvp);
            rsvpRepository.delete(rsvp);
        });
    }
    
    public List<RSVP> getUserRSVPs(User user) {
        return rsvpRepository.findByUser(user);
    }
    
    public List<RSVP> getEventRSVPs(Event event) {
        return rsvpRepository.findByEvent(event);
    }
    
    public boolean hasUserRSVPd(Event event, User user) {
        return rsvpRepository.existsByEventAndUser(event, user);
    }
}