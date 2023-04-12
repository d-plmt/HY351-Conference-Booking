package com.bookingSystem.DTO;

import java.sql.Date;

import org.springframework.stereotype.Component;

import com.bookingSystem.reservation.ReservationId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Component
public class ReservationIdDTO {
    private Date date;

    private Integer employeeId;

    private Integer roomId;

    public ReservationIdDTO convertToDTO(ReservationId resId) {
        ReservationIdDTO dto = new ReservationIdDTO();
        dto.setDate(resId.getDate());
        dto.setEmployeeId(resId.getEmployee().getId());
        dto.setRoomId(resId.getRoom().getId());
        return dto;
    }
}
