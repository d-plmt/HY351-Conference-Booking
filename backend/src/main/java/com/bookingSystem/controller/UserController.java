package com.bookingSystem.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bookingSystem.DTO.AdminDTO;
import com.bookingSystem.DTO.EmployeeDTO;
import com.bookingSystem.DTO.UserDTO;
import com.bookingSystem.user.Admin;
import com.bookingSystem.user.AdminRepository;
import com.bookingSystem.user.Employee;
import com.bookingSystem.user.EmployeeRepository;
import com.bookingSystem.user.User;
import com.bookingSystem.user.UserRepository;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
// @RequestMapping("/user")
public class UserController {
    
    @Autowired
    private final UserRepository userRepo;

    @Autowired
    private final EmployeeRepository employeeRepo;

    @Autowired
    private final AdminRepository adminRepo;

    @Autowired
    private final UserDTO userDTO;

    @Autowired
    private final EmployeeDTO employeeDTO;

    @Autowired
    private final AdminDTO adminDTO;

    //  USERS //
    
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/users")
    List<UserDTO> all() {
        List<User> users = userRepo.findAll();
        List<UserDTO> dtos = new ArrayList<>();
        for (User user: users) {
            dtos.add(userDTO.convertToDTO(user));
        }
        return dtos;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/users/{id}")
    User findUser(@PathVariable Integer id) {
        return userRepo.findById(id)
        .orElseThrow(() -> new RuntimeException());
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return userRepo.save(newUser);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/users/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable Integer id) {
        return userRepo.findById(id)
        .map(user -> {
            user.setPassword(newUser.getPassword());
            user.setEmail(newUser.getEmail());
            user.setPhone(newUser.getPhone());
            return userRepo.save(user);
        })
        .orElseGet(() -> {
            newUser.setId(id);
            return userRepo.save(newUser);
        });
    }

    // EMPLOYEES //

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/employees")
    List<EmployeeDTO> employeeAll() {
        List<Employee> employees = employeeRepo.findAll();
        List<EmployeeDTO> dtos = new ArrayList<>();
        for (Employee employee: employees) {
            dtos.add(employeeDTO.convertToDTO(employee));
        }
        return dtos;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/employees/{id}")
    Employee findEmployee(@PathVariable Integer id) {
        return employeeRepo.findById(id)
        .orElseThrow(() -> new RuntimeException());
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/employees")
    Employee newEmployee(@RequestBody Employee newEmployee) {
        return employeeRepo.save(newEmployee);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/employees/{id}")
    Employee updateEmployee(@RequestBody Employee newEmployee, @PathVariable Integer id) {
        return employeeRepo.findById(id)
        .map(employee -> {
            employee.setDepartment(newEmployee.getDepartment());
            return employeeRepo.save(employee);
        })
        .orElseGet(() -> {
            newEmployee.setId(id);
            return employeeRepo.save(newEmployee);
        });
    }

    // ADMINS //

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/admins")
    List<AdminDTO> adminAll() {
        List<Admin> admins = adminRepo.findAll();
        List<AdminDTO> dtos = new ArrayList<>();
        for (Admin admin: admins) {
            dtos.add(adminDTO.convertToDTO(admin));
        }
        return dtos;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/admins/{id}")
    Admin findAdmin(@PathVariable Integer id) {
        return adminRepo.findById(id)
        .orElseThrow(() -> new RuntimeException());
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/admins")
    Admin newAdmin(@RequestBody Admin newAdmin) {
        return adminRepo.save(newAdmin);
    }
}
