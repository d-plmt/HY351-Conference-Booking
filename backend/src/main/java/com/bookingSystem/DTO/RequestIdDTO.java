package com.bookingSystem.DTO;



import java.sql.Date;

import org.springframework.stereotype.Component;

import com.bookingSystem.reservationRequest.ReservationRequestId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Component
public class RequestIdDTO {
    private Date date;

    private Integer employeeId;

    private Integer roomId;

    public RequestIdDTO convertToDTO(ReservationRequestId reqId) {
        RequestIdDTO dto = new RequestIdDTO();
        dto.setDate(reqId.getDate());
        dto.setEmployeeId(reqId.getEmployee().getId());
        dto.setRoomId(reqId.getRoom().getId());
        return dto;
    }
}
