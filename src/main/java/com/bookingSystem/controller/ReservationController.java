package com.bookingSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookingSystem.reservation.Reservation;
import com.bookingSystem.reservation.ReservationRepository;
import com.bookingSystem.reservationRequest.ReservationRequest;
import com.bookingSystem.reservationRequest.ReservationRequestRepository;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private ReservationRepository resRepo;

    @Autowired
    private ReservationRequestRepository reqRepo;

    public ReservationController(ReservationRepository resRepo, ReservationRequestRepository reqRepo) {
        this.resRepo = resRepo;
        this.reqRepo = reqRepo;
    }

    @GetMapping("/all")
    List<Reservation> reservationAll() {
      return resRepo.findAll();
    }

    @GetMapping("/request/all")
    List<ReservationRequest> requestAll() {
      return reqRepo.findAll();
    }

}