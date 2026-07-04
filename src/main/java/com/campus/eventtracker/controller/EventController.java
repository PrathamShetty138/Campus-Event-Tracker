package com.campus.eventtracker.controller;

import com.campus.eventtracker.model.Event;
import com.campus.eventtracker.model.RSVP;
import com.campus.eventtracker.model.User;
import com.campus.eventtracker.service.EventService;
import com.campus.eventtracker.service.RSVPService;
import com.campus.eventtracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventService eventService;
    
    @Autowired
    private RSVPService rsvpService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/create")
    public String createEventPage(Model model) {
        model.addAttribute("event", new Event());
        return "event-create";
    }
    
    @PostMapping("/create")
    public String createEvent(@Valid @ModelAttribute Event event,
                             BindingResult result,
                             Authentication auth) {
        if (result.hasErrors()) {
            return "event-create";
        }

        // Manual future-date check (removed from model to allow seeder past events)
        if (event.getDate() != null && event.getDate().isBefore(java.time.LocalDate.now())) {
            result.rejectValue("date", "future", "Event date must be in the future");
            return "event-create";
        }

        User user = userService.findByEmail(auth.getName()).orElseThrow();
        eventService.createEvent(event, user);
        return "redirect:/";
    }
    
    @GetMapping("/{id}")
    public String eventDetails(@PathVariable Long id, Model model, Authentication auth) {
        Event event = eventService.getEventById(id).orElseThrow();
        List<RSVP> rsvps = rsvpService.getEventRSVPs(event);
        
        boolean hasRSVPd = false;
        if (auth != null) {
            User user = userService.findByEmail(auth.getName()).orElse(null);
            if (user != null) {
                hasRSVPd = rsvpService.hasUserRSVPd(event, user);
            }
        }
        
        model.addAttribute("event", event);
        model.addAttribute("rsvps", rsvps);
        model.addAttribute("hasRSVPd", hasRSVPd);
        return "event-details";
    }
    
    @PostMapping("/{id}/rsvp")
    public String rsvpToEvent(@PathVariable Long id, Authentication auth) {
        Event event = eventService.getEventById(id).orElseThrow();
        User user = userService.findByEmail(auth.getName()).orElseThrow();
        rsvpService.createRSVP(event, user);
        return "redirect:/events/" + id;
    }
    
    @PostMapping("/{id}/cancel-rsvp")
    public String cancelRSVP(@PathVariable Long id, Authentication auth) {
        Event event = eventService.getEventById(id).orElseThrow();
        User user = userService.findByEmail(auth.getName()).orElseThrow();
        rsvpService.cancelRSVP(event, user);
        return "redirect:/events/" + id;
    }
    
    @PostMapping("/{id}/delete")
    public String deleteEvent(@PathVariable Long id, Authentication auth) {
        Event event = eventService.getEventById(id).orElseThrow();
        User user = userService.findByEmail(auth.getName()).orElseThrow();
        
        if (event.getCreatedBy().getId().equals(user.getId()) || user.getRole().equals("ADMIN")) {
            eventService.deleteEvent(id);
        }
        return "redirect:/";
    }
}