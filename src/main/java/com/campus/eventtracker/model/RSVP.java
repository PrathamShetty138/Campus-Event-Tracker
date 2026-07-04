package com.campus.eventtracker.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rsvps", uniqueConstraints = @UniqueConstraint(columnNames = {"event_id", "user_id"}))
public class RSVP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();
    
    @OneToMany(mappedBy = "rsvp", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.Set<Reminder> reminders = new java.util.HashSet<>();
    
    // Constructors
    public RSVP() {}
    
    public RSVP(Event event, User user) {
        this.event = event;
        this.user = user;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Event getEvent() { return event; }
    public void setEvent(Event event) { this.event = event; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public java.util.Set<Reminder> getReminders() { return reminders; }
    public void setReminders(java.util.Set<Reminder> reminders) { this.reminders = reminders; }
}