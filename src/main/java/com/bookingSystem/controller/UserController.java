package com.bookingSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    //  USERS //
    
    @GetMapping("/users")
    List<User> all() {
        return userRepo.findAll();
    }

    @GetMapping("/users/{id}")
    User findUser(@PathVariable Integer id) {
        return userRepo.findById(id)
        .orElseThrow(() -> new RuntimeException());
    }

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return userRepo.save(newUser);
    }

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

    @GetMapping("/employees")
    List<Employee> employeeAll() {
        return employeeRepo.findAll();
    }

    @GetMapping("/employees/{id}")
    Employee findEmployee(@PathVariable Integer id) {
        return employeeRepo.findById(id)
        .orElseThrow(() -> new RuntimeException());
    }

    @PostMapping("/employees")
    Employee newEmployee(@RequestBody Employee newEmployee) {
        return employeeRepo.save(newEmployee);
    }

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

    @GetMapping("/admins")
    List<Admin> adminAll() {
        return adminRepo.findAll();
    }

    @GetMapping("/admins/{id}")
    Admin findAdmin(@PathVariable Integer id) {
        return adminRepo.findById(id)
        .orElseThrow(() -> new RuntimeException());
    }

    @PostMapping("/admins")
    Admin newAdmin(@RequestBody Admin newAdmin) {
        return adminRepo.save(newAdmin);
    }
}
