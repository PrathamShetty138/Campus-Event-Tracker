package com.campus.eventtracker.service;

import com.campus.eventtracker.model.Event;
import com.campus.eventtracker.model.RSVP;
import com.campus.eventtracker.model.Reminder;
import com.campus.eventtracker.repository.ReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReminderService {
    
    @Autowired
    private ReminderRepository reminderRepository;
    
    @Autowired
    private EmailService emailService;
    
    // Create reminder when user RSVPs (24 hours before event)
    public void createReminder(RSVP rsvp, int hoursBefore) {
        Event event = rsvp.getEvent();
        LocalDateTime eventDateTime = LocalDateTime.of(event.getDate(), event.getTime());
        LocalDateTime reminderTime = eventDateTime.minusHours(hoursBefore);
        
        // Only create reminder if event is in the future
        if (reminderTime.isAfter(LocalDateTime.now())) {
            Reminder reminder = new Reminder(rsvp, reminderTime, hoursBefore);
            reminderRepository.save(reminder);
        }
    }
    
    // Delete reminders when RSVP is cancelled
    @Transactional
    public void deleteRemindersForRSVP(RSVP rsvp) {
        reminderRepository.deleteByRsvp(rsvp);
    }
    
    // Check and send reminders every 5 minutes
    @Scheduled(fixedRate = 300000) // 5 minutes = 300000 ms
    public void sendPendingReminders() {
        LocalDateTime now = LocalDateTime.now();
        List<Reminder> pendingReminders = reminderRepository.findByReminderTimeBeforeAndSentFalse(now);
        
        for (Reminder reminder : pendingReminders) {
            sendReminder(reminder);
        }
    }
    
    private void sendReminder(Reminder reminder) {
        try {
            RSVP rsvp = reminder.getRsvp();
            Event event = rsvp.getEvent();
            String userEmail = rsvp.getUser().getEmail();
            String userName = rsvp.getUser().getName();
            
            String subject = "Reminder: " + event.getTitle() + " is coming up!";
            String body = String.format(
                "Hi %s,\n\n" +
                "This is a reminder that you have RSVP'd to the following event:\n\n" +
                "Event: %s\n" +
                "Date: %s\n" +
                "Time: %s\n" +
                "Location: %s\n\n" +
                "The event is happening in %d hours!\n\n" +
                "See you there!\n\n" +
                "Campus Event Tracker Team",
                userName,
                event.getTitle(),
                event.getDate(),
                event.getTime(),
                event.getLocation(),
                reminder.getHoursBefore()
            );
            
            emailService.sendReminderEmail(userEmail, subject, body);
            
            // Mark as sent
            reminder.setSent(true);
            reminderRepository.save(reminder);
            
        } catch (Exception e) {
            System.err.println("Error sending reminder: " + e.getMessage());
        }
    }
}