package com.bookingSystem.DTO;

import org.springframework.stereotype.Component;

import com.bookingSystem.user.Admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Component
public class AdminDTO {
    private Integer id;

    public AdminDTO convertToDTO(Admin admin) {
        AdminDTO dto = new AdminDTO();
        dto.setId(admin.getId());
        return dto;
    }
}
