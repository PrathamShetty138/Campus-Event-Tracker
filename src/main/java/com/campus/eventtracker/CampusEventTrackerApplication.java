package com.campus.eventtracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CampusEventTrackerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CampusEventTrackerApplication.class, args);
    }
}