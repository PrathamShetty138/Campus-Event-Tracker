package com.campus.eventtracker.controller;

import com.campus.eventtracker.model.Event;
import com.campus.eventtracker.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private EventService eventService;
    
    @GetMapping("/")
    public String home(@RequestParam(required = false) String type,
                      @RequestParam(required = false) String search,
                      Model model) {
        List<Event> events;
        
        if (search != null && !search.isEmpty()) {
            events = eventService.searchEvents(search);
        } else if (type != null && !type.isEmpty()) {
            events = eventService.getEventsByType(type);
        } else {
            events = eventService.getUpcomingEvents();
        }
        
        model.addAttribute("events", events);
        model.addAttribute("selectedType", type);
        model.addAttribute("searchQuery", search);
        // Past events (last 30 days) — only show when not filtering
        if ((search == null || search.isEmpty()) && (type == null || type.isEmpty())) {
            model.addAttribute("pastEvents", eventService.getRecentPastEvents());
        } else {
            model.addAttribute("pastEvents", java.util.List.of());
        }
        return "home";
    }
    
    @GetMapping("/about")
    public String about() {
        return "about";
    }
    
    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }
}