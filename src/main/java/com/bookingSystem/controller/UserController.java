package com.bookingSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookingSystem.user.Admin;
import com.bookingSystem.user.AdminRepository;
import com.bookingSystem.user.Employee;
import com.bookingSystem.user.EmployeeRepository;
import com.bookingSystem.user.User;
import com.bookingSystem.user.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserRepository repo;

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private AdminRepository adminRepo;

    UserController(UserRepository userRepository, EmployeeRepository employeeRepo, AdminRepository adminRepo) {
        this.repo = userRepository;
        this.employeeRepo = employeeRepo;
        this.adminRepo = adminRepo;
    }
    
    @GetMapping("/all")
    List<User> all() {
        return repo.findAll();
    }

    @GetMapping("/employee/all")
    List<Employee> employeeAll() {
        return employeeRepo.findAll();
    }

    @GetMapping("/admin/all")
    List<Admin> adminAll() {
        return adminRepo.findAll();
        }

}
