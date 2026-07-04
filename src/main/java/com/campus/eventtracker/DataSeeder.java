package com.campus.eventtracker;

import com.campus.eventtracker.model.Event;
import com.campus.eventtracker.model.User;
import com.campus.eventtracker.repository.EventRepository;
import com.campus.eventtracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired private EventRepository eventRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        // Clear old seed events (owned by admin) and re-seed with fresh data
        if (!userRepository.existsByEmail("admin@univibe.com")) {
            // First run — no admin yet, just seed
        } else {
            User existingAdmin = userRepository.findByEmail("admin@univibe.com").get();
            // Delete all events owned by admin so we can re-seed cleanly
            eventRepository.deleteAll(
                eventRepository.findAll().stream()
                    .filter(e -> e.getCreatedBy() != null &&
                                 "admin@univibe.com".equals(e.getCreatedBy().getEmail()))
                    .toList()
            );
        }

        // Create admin user to own the seed events
        User admin;
        if (!userRepository.existsByEmail("admin@univibe.com")) {
            admin = new User();
            admin.setName("UniVibe Admin");
            admin.setEmail("admin@univibe.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            admin = userRepository.save(admin);
        } else {
            admin = userRepository.findByEmail("admin@univibe.com").get();
        }

        seed(admin, "Hackathon 2026",
                "Academic", LocalDate.of(2026, 8, 15), LocalTime.of(9, 0),
                "Innovation Lab",
                "24-hour coding competition to build innovative software solutions. Open to all students. Form teams of up to 4 and compete for prizes and internship opportunities.");

        seed(admin, "AI & Machine Learning Workshop",
                "Workshop", LocalDate.of(2026, 8, 20), LocalTime.of(10, 0),
                "Seminar Hall A",
                "Hands-on workshop covering machine learning fundamentals and practical applications. Topics include supervised learning, neural networks, and model deployment. Runs 10:00 AM to 1:00 PM.");

        seed(admin, "Inter-Department Cricket Tournament",
                "Sports", LocalDate.of(2026, 8, 25), LocalTime.of(8, 30),
                "College Cricket Ground",
                "Annual cricket tournament featuring teams from all departments. Knockout format. All matches played from 8:30 AM to 5:00 PM. Come cheer your department team!");

        seed(admin, "Cultural Fest - Utsav",
                "Cultural", LocalDate.of(2026, 9, 5), LocalTime.of(17, 0),
                "Open Air Theatre",
                "Music, dance, drama, and talent performances by students. 5:00 PM to 9:00 PM. Food stalls, art displays, and an electrifying atmosphere. Do not miss it!");

        seed(admin, "Resume Building & Placement Training",
                "Academic", LocalDate.of(2026, 9, 10), LocalTime.of(14, 0),
                "Placement Cell",
                "Session on resume writing, aptitude preparation, and interview skills. Conducted by the placement team and industry mentors. 2:00 PM to 4:00 PM. All final-year students must attend.");

        seed(admin, "Photography Contest",
                "Cultural", LocalDate.of(2026, 9, 12), LocalTime.of(9, 0),
                "Campus Grounds",
                "Capture the best moments on campus and compete for exciting prizes. Theme: Campus Life. Submissions accepted from 9:00 AM to 3:00 PM. Results announced the same evening.");

        seed(admin, "Cyber Security Bootcamp",
                "Workshop", LocalDate.of(2026, 9, 18), LocalTime.of(10, 0),
                "Computer Lab 2",
                "Learn ethical hacking, network security, and cyber defense techniques. Hands-on labs included. Runs 10:00 AM to 4:00 PM. Bring your laptop. Certificate provided on completion.");

        seed(admin, "Badminton Championship",
                "Sports", LocalDate.of(2026, 9, 22), LocalTime.of(9, 0),
                "Indoor Sports Complex",
                "Singles and doubles badminton tournament for students. 9:00 AM to 5:00 PM. Register individually or as a pair. Prizes for winners and runners-up in both categories.");

        seed(admin, "Entrepreneurship Summit",
                "Academic", LocalDate.of(2026, 9, 28), LocalTime.of(11, 0),
                "MBA Block Auditorium",
                "Startup founders and industry experts share entrepreneurial insights. Panel discussions, Q&A sessions, and networking opportunities. 11:00 AM to 3:00 PM. Open to all students.");

        seed(admin, "Blood Donation Camp",
                "Social", LocalDate.of(2026, 10, 2), LocalTime.of(9, 0),
                "Health Center",
                "Voluntary blood donation drive organized in collaboration with a local hospital. 9:00 AM to 2:00 PM. All eligible students and staff are encouraged to participate. Refreshments provided.");

        seed(admin, "Web Development Bootcamp",
                "Workshop", LocalDate.of(2026, 10, 8), LocalTime.of(9, 30),
                "Computer Lab 1",
                "Learn HTML, CSS, JavaScript, and modern web development practices. Beginner friendly. 9:30 AM to 1:30 PM. Bring your laptop. Projects will be built during the session.");

        seed(admin, "Freshers' Talent Show",
                "Cultural", LocalDate.of(2026, 10, 15), LocalTime.of(18, 0),
                "Auditorium",
                "New students showcase their talents through music, dance, and performances. 6:00 PM to 9:00 PM. A wonderful evening to welcome the new batch and celebrate their talents.");

        seed(admin, "Chess Tournament",
                "Sports", LocalDate.of(2026, 10, 18), LocalTime.of(10, 0),
                "Indoor Games Hall",
                "Individual knockout chess competition open to all students. 10:00 AM to 4:00 PM. Standard FIDE rules apply. Register by the day before. Prizes for top 3 finishers.");

        seed(admin, "Cloud Computing Seminar",
                "Academic", LocalDate.of(2026, 10, 25), LocalTime.of(14, 0),
                "Seminar Hall B",
                "Industry experts discuss AWS, Azure, and cloud career opportunities. 2:00 PM to 5:00 PM. Topics include cloud architecture, certifications, and real-world case studies. Open to all.");

        seed(admin, "Diwali Celebration",
                "Cultural", LocalDate.of(2026, 11, 5), LocalTime.of(17, 30),
                "Central Quadrangle",
                "Festive celebration with cultural performances, rangoli, and traditional events. 5:30 PM to 8:30 PM. Lighting ceremony, music, and traditional sweets. Everyone is welcome!");

        // ── Past events (recently ended) ──────────────────────────────────

        seed(admin, "AI & Machine Learning Workshop",
                "Workshop", LocalDate.of(2026, 6, 15), LocalTime.of(10, 0),
                "Seminar Hall A",
                "Hands-on workshop on machine learning fundamentals. Covering supervised learning, model training, and real-world applications. 10:00 AM to 1:00 PM.");

        seed(admin, "Inter-Department Football Tournament",
                "Sports", LocalDate.of(2026, 6, 20), LocalTime.of(8, 30),
                "College Football Ground",
                "Annual football tournament between departments. Knockout format with teams from all departments competing. 8:30 AM to 5:00 PM. Great energy and team spirit!");

        seed(admin, "Resume Building Session",
                "Academic", LocalDate.of(2026, 6, 25), LocalTime.of(14, 0),
                "Placement Cell",
                "Training on resume writing and interview preparation. Conducted by the placement team. 2:00 PM to 4:00 PM. All final-year students attended.");

        seed(admin, "Photography Contest",
                "Cultural", LocalDate.of(2026, 6, 28), LocalTime.of(9, 0),
                "Campus Grounds",
                "Campus-wide photography competition with exciting prizes. Theme: Campus Life. Submissions from 9:00 AM to 3:00 PM. Winners announced the same evening.");

        seed(admin, "Cyber Security Bootcamp",
                "Workshop", LocalDate.of(2026, 6, 30), LocalTime.of(10, 0),
                "Computer Lab 2",
                "Learn ethical hacking, cybersecurity fundamentals, and network defense. Hands-on labs with real scenarios. 10:00 AM to 4:00 PM. Certificate provided.");

        System.out.println("UniVibe: 20 events seeded successfully (15 upcoming + 5 past)!");
    }

    private void seed(User admin, String title, String type, LocalDate date,
                      LocalTime time, String location, String description) {
        Event e = new Event();
        e.setTitle(title);
        e.setType(type);
        e.setDate(date);
        e.setTime(time);
        e.setLocation(location);
        e.setDescription(description);
        e.setCreatedBy(admin);
        eventRepository.save(e);
    }
}
