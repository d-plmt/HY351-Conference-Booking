package com.bookingSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bookingSystem.timeSlot.TimeSlot;
import com.bookingSystem.timeSlot.TimeSlotRepository;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
// @RequestMapping("/timeslot")
public class TimeSlotController {

    @Autowired
    private final TimeSlotRepository repo;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/timeslots")
    List<TimeSlot> all() {
        return repo.findAll();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/timeslots/{id}")
    TimeSlot findTimeSlot(@PathVariable Integer id) {
        return repo.findById(id)
        .orElseThrow(() -> new RuntimeException());
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/timeslots")
    TimeSlot newTimeSlot(@RequestBody TimeSlot newTimeSlot) {
        return repo.save(newTimeSlot);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/timeslots/{id}")
    TimeSlot updateTimeSlot(@RequestBody TimeSlot newTimeSlot, @PathVariable Integer id) {
        return repo.findById(id)
        .map(timeSlot -> {
            timeSlot.setEndTime(newTimeSlot.getEndTime());
            timeSlot.setStartTime(newTimeSlot.getStartTime());
            return repo.save(timeSlot);
        })
        .orElseGet(() -> {
            newTimeSlot.setId(id);
            return repo.save(newTimeSlot);
        });
    }
}
