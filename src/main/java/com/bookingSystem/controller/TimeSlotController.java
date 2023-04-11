package com.bookingSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookingSystem.timeSlot.TimeSlot;
import com.bookingSystem.timeSlot.TimeSlotRepository;

@RestController
@RequestMapping("/timeslot")
public class TimeSlotController {

    @Autowired
    private TimeSlotRepository repo;

    public TimeSlotController(TimeSlotRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/all")
    List<TimeSlot> all() {
      return repo.findAll();
    }
}
