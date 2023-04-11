package com.bookingSystem.DTO;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bookingSystem.reservation.Reservation;
import com.bookingSystem.timeSlot.TimeSlot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Component
public class ReservationDTO {
    private ReservationIdDTO id;

    private String purpose;

    private List<TimeSlot> timeSlots;

    public ReservationDTO convertToDTO(Reservation reservation) {
        ReservationDTO dto = new ReservationDTO();
        ReservationIdDTO idDTO = new ReservationIdDTO();
        dto.setId(idDTO.convertToDTO(reservation.getId()));
        dto.setPurpose(reservation.getPurpose());
        dto.setTimeSlots(reservation.getTimeSlots());
        return dto;
    }
}
