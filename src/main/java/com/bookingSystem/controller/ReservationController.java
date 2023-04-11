package com.bookingSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bookingSystem.reservation.Reservation;
import com.bookingSystem.reservation.ReservationId;
import com.bookingSystem.reservation.ReservationRepository;
import com.bookingSystem.reservationRequest.ReservationRequest;
import com.bookingSystem.reservationRequest.ReservationRequestId;
import com.bookingSystem.reservationRequest.ReservationRequestRepository;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
// @RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private final ReservationRepository resRepo;

    @Autowired
    private final ReservationRequestRepository reqRepo;

    @GetMapping("/reservations")
    List<Reservation> allRes() {
        return resRepo.findAll();
    }

    @GetMapping("/reservations/{id}")
    Reservation findRes(@PathVariable ReservationId id) {
        return resRepo.findById(id)
        .orElseThrow(() -> new RuntimeException());
    }

    @PostMapping("/reservations")
    Reservation newRes(@RequestBody Reservation newRes) {
        return resRepo.save(newRes);
    }

    @PutMapping("/reservations/{id}")
    Reservation updateRes(@RequestBody Reservation newRes, @PathVariable ReservationId id) {
        return resRepo.findById(id)
        .map(res -> {
          res.setPurpose(newRes.getPurpose());
          res.setTimeSlots(newRes.getTimeSlots());
          return resRepo.save(res);
        })
        .orElseGet(() -> {
            newRes.setId(id);
            return resRepo.save(newRes);
        });
    }


    // REQUESTS //

    @GetMapping("/requests")
    List<ReservationRequest> allReq() {
        return reqRepo.findAll();
    }

    @GetMapping("/requests/{id}")
    ReservationRequest findReq(@PathVariable ReservationRequestId id) {
        return reqRepo.findById(id)
        .orElseThrow(() -> new RuntimeException());
    }

    @PostMapping("/requests")
    ReservationRequest newReq(@RequestBody ReservationRequest newReq) {
        return reqRepo.save(newReq);
    }

    @PutMapping("/requests/{id}")
    ReservationRequest updateReq(@RequestBody ReservationRequest newReq, @PathVariable ReservationRequestId id) {
        return reqRepo.findById(id)
        .map(req -> {
          req.setPurpose(newReq.getPurpose());
          req.setTimeSlots(newReq.getTimeSlots());
          req.setAdmin(newReq.getAdmin());
          req.setDeniedReason(newReq.getDeniedReason());
          req.setStatus(newReq.getStatus());
          return reqRepo.save(req);
        })
        .orElseGet(() -> {
            newReq.setId(id);
            return reqRepo.save(newReq);
        });
    }

}