package com.bookingSystem.DTO;

import org.springframework.stereotype.Component;

import com.bookingSystem.room.Room;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Component
public class RoomDTO {
    private Integer id;
    private Integer capacity;
    private Integer floor;
    private boolean accessibility;

    public RoomDTO convertToDTO(Room room) {
        RoomDTO dto = new RoomDTO();
        dto.setAccessibility(room.isAccessibility());
        dto.setCapacity(room.getCapacity());
        dto.setFloor(room.getFloor());
        dto.setId(room.getId());

        return dto;
    }
}
