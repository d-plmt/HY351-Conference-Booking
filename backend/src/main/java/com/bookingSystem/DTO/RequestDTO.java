package com.bookingSystem.DTO;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bookingSystem.reservationRequest.ReservationRequest;
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
public class RequestDTO {
    private RequestIdDTO id;

    private String purpose;

    private String status;

    private String deniedReason;

    private Integer adminId;

    private List<TimeSlot> timeSlots;

    public RequestDTO convertToDTO(ReservationRequest request) {
        RequestDTO dto = new RequestDTO();
        RequestIdDTO idDTO = new RequestIdDTO();
        dto.setId(idDTO.convertToDTO(request.getId()));
        dto.setPurpose(request.getPurpose());
        dto.setStatus(request.getStatus());
        dto.setDeniedReason(request.getDeniedReason());

        if (request.getAdmin() != null) {
            dto.setAdminId(request.getAdmin().getId());
        }
        dto.setTimeSlots(request.getTimeSlots());
        return dto;
    }
}
