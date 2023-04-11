package com.bookingSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookingSystem.room.Room;
import com.bookingSystem.room.RoomRepository;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomRepository repo;

    RoomController(RoomRepository roomRepository) {
        this.repo = roomRepository;
    }

    @GetMapping("/all")
    List<Room> all() {
      return repo.findAll();
    }

}
