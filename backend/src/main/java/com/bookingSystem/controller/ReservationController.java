package com.bookingSystem.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bookingSystem.DTO.RequestDTO;
import com.bookingSystem.DTO.ReservationDTO;
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

    @Autowired
    private final RequestDTO reqDTO;

    @Autowired
    private final ReservationDTO resDTO;


    @GetMapping("/reservations")
    List<ReservationDTO> allRes() {
        List<Reservation> reservations = resRepo.findAll();
        List<ReservationDTO> dtos = new ArrayList<>();
        for (Reservation reservation: reservations) {
            dtos.add(resDTO.convertToDTO(reservation));
        }
        return dtos;
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
    List<RequestDTO> allReq() {
        List<ReservationRequest> requests = reqRepo.findAll();
        List<RequestDTO> dtos = new ArrayList<>();
        for (ReservationRequest request: requests) {
            dtos.add(reqDTO.convertToDTO(request));
        }
        return dtos;
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

    @PersistenceContext
    private EntityManager entityManager;

    @DeleteMapping("/requests/{date}+{booked_by}+{room_id}")
    void deleteReservationRequestTimeSlots(@PathVariable Date date, @PathVariable Integer booked_by, @PathVariable Integer room_id) {
        String sql = "DELETE FROM reservation_request_timeslot WHERE reservation_date = '"+date+"' AND booked_by = "+booked_by+" AND room_id = "+room_id+";";
        Query query = entityManager.createNativeQuery(sql);
        query.executeUpdate();
    }

}