package com.campus.eventtracker.controller;

import com.campus.eventtracker.model.RSVP;
import com.campus.eventtracker.model.User;
import com.campus.eventtracker.service.RSVPService;
import com.campus.eventtracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class DashboardController {
    @Autowired
    private RSVPService rsvpService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, Model model) {
        User user = userService.findByEmail(auth.getName()).orElseThrow();
        List<RSVP> rsvps = rsvpService.getUserRSVPs(user);
        
        model.addAttribute("user", user);
        model.addAttribute("rsvps", rsvps);
        return "dashboard";
    }
}