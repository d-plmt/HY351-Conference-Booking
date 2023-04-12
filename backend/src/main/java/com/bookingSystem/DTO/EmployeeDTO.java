package com.bookingSystem.DTO;

import org.springframework.stereotype.Component;

import com.bookingSystem.user.Employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Component
public class EmployeeDTO {
    private Integer id;

    private String department;

    public EmployeeDTO convertToDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setDepartment(employee.getDepartment());
        dto.setId(employee.getId());
        return dto;
    }
}
