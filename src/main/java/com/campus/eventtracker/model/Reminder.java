package com.campus.eventtracker.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reminders")
public class Reminder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "rsvp_id", nullable = false)
    private RSVP rsvp;
    
    @Column(nullable = false)
    private LocalDateTime reminderTime;
    
    @Column(nullable = false)
    private boolean sent = false;
    
    @Column(nullable = false)
    private int hoursBefore = 24; // Default: 24 hours before event
    
    // Constructors
    public Reminder() {}
    
    public Reminder(RSVP rsvp, LocalDateTime reminderTime, int hoursBefore) {
        this.rsvp = rsvp;
        this.reminderTime = reminderTime;
        this.hoursBefore = hoursBefore;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public RSVP getRsvp() { return rsvp; }
    public void setRsvp(RSVP rsvp) { this.rsvp = rsvp; }
    
    public LocalDateTime getReminderTime() { return reminderTime; }
    public void setReminderTime(LocalDateTime reminderTime) { this.reminderTime = reminderTime; }
    
    public boolean isSent() { return sent; }
    public void setSent(boolean sent) { this.sent = sent; }
    
    public int getHoursBefore() { return hoursBefore; }
    public void setHoursBefore(int hoursBefore) { this.hoursBefore = hoursBefore; }
}