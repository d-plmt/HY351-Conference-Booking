package com.bookingSystem.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bookingSystem.DTO.RoomDTO;
import com.bookingSystem.room.Room;
import com.bookingSystem.room.RoomRepository;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class RoomController {

    private final RoomDTO roomDTO;

    @Autowired
    private final RoomRepository repo;

    @GetMapping("/rooms")
    List<RoomDTO> all() {
        List<Room> rooms = repo.findAll();
        List<RoomDTO> dtos = new ArrayList<>();
        for (Room room: rooms) {
            dtos.add(roomDTO.convertToDTO(room));
        }
        return dtos;
    }

    @GetMapping("/rooms/{id}")
    Room find(@PathVariable Integer id) {
        return repo.findById(id)
        .orElseThrow(() -> new RuntimeException());
    }

    @PostMapping("/rooms")
    Room newRoom(@RequestBody Room newRoom) {
        return repo.save(newRoom);
    }

    @PutMapping("/rooms/{id}")
    Room updateRoom(@RequestBody Room newRoom, @PathVariable Integer id) {
        return repo.findById(id)
        .map(room -> {
            room.setCapacity(newRoom.getCapacity());
            room.setAccessibility(newRoom.isAccessibility());
            room.setFloor(newRoom.getFloor());
            room.setReservations(newRoom.getReservations());
            return repo.save(room);
        })
        .orElseGet(() -> {
            newRoom.setId(id);
            return repo.save(newRoom);
        });
    }

}
