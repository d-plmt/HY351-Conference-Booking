package com.bookingSystem.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.bookingSystem.room.Room;
import com.bookingSystem.room.RoomRepository;
import com.bookingSystem.user.Employee;
import com.bookingSystem.user.EmployeeRepository;

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
    private final RoomRepository roomRepo;

    @Autowired
    private final EmployeeRepository employeeRepo;

    @Autowired
    private final RequestDTO reqDTO;

    @Autowired
    private final ReservationDTO resDTO;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/reservations")
    List<ReservationDTO> allRes() {
        List<Reservation> reservations = resRepo.findAll();
        List<ReservationDTO> dtos = new ArrayList<>();
        for (Reservation reservation: reservations) {
            dtos.add(resDTO.convertToDTO(reservation));
        }
        return dtos;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/reservations/{id}")
    Reservation findRes(@PathVariable ReservationId id) {
        return resRepo.findById(id)
        .orElseThrow(() -> new RuntimeException());
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/reservations")
    ReservationDTO newRes(@RequestBody Reservation newRes) {
        Room room = roomRepo.findById(newRes.getId().getRoom().getId())
                    .orElseThrow(() -> new RuntimeException());
        Employee employee = employeeRepo.findById(newRes.getId().getEmployee().getId())
                    .orElseThrow(() -> new RuntimeException());
        newRes.setId(new ReservationId(newRes.getId().getDate(), employee, room));
        ReservationDTO dto = new ReservationDTO();
        return dto.convertToDTO(resRepo.save(newRes));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/reservations/{date}-{employeeId}-{roomId}")
    Reservation updateRes(@RequestBody Reservation newRes, @PathVariable Date date, @PathVariable Employee employeeId, @PathVariable Room roomId) {
        Employee employee = employeeRepo.findById(employeeId.getId())
                    .orElseThrow(() -> new RuntimeException());
        Room room = roomRepo.findById(roomId.getId())
                    .orElseThrow(() -> new RuntimeException());
        ReservationId resId = new ReservationId(date, employee, room);
        return resRepo.findById(resId)
        .map(res -> {
          res.setPurpose(newRes.getPurpose());
          res.setTimeSlots(newRes.getTimeSlots());
          return resRepo.save(res);
        })
        .orElseGet(() -> {
            newRes.setId(resId);
            return resRepo.save(newRes);
        });
    }


    // REQUESTS //

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/requests")
    List<RequestDTO> allReq() {
        List<ReservationRequest> requests = reqRepo.findAll();
        List<RequestDTO> dtos = new ArrayList<>();
        for (ReservationRequest request: requests) {
            dtos.add(reqDTO.convertToDTO(request));
        }
        return dtos;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/requests/active")
    List<RequestDTO> allActiveReq() {
        List<ReservationRequest> requests = reqRepo.findAll();
        List<RequestDTO> dtos = new ArrayList<>();
        for (ReservationRequest request: requests) {
            if (!request.getStatus().equals("denied")) {
                dtos.add(reqDTO.convertToDTO(request));
            }
        }
        return dtos;
    }

    // @CrossOrigin(origins = "http://localhost:3000")
    // @GetMapping("/requestsByEmployee/{employeeId}")
    // RequestDTO findReqByEmployee(@PathVariable Employee employeeId) {
    //     Employee employee = employeeRepo.findById(employeeId.getId())
    //                 .orElseThrow(() -> new RuntimeException());
    //     ReservationRequestId reqId = new ReservationRequestId();
    //     reqId.setEmployee(employee);
    //     RequestDTO dto = new RequestDTO();
    //     return dto.convertToDTO(reqRepo.findById(reqId)
    //         .orElseThrow(() -> new RuntimeException("error")));
    // }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/requests/{id}")
    ReservationRequest findReq(@PathVariable ReservationRequestId id) {
        return reqRepo.findById(id)
        .orElseThrow(() -> new RuntimeException());
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/requests")
    ReservationRequest newReq(@RequestBody ReservationRequest newReq) {
        return reqRepo.save(newReq);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/requests/{date}-{employeeId}-{roomId}")
    RequestDTO updateReq(@RequestBody ReservationRequest newReq, @PathVariable Date date, @PathVariable Employee employeeId, @PathVariable Room roomId) {
        Employee employee = employeeRepo.findById(employeeId.getId())
                    .orElseThrow(() -> new RuntimeException());
        Room room = roomRepo.findById(roomId.getId())
                    .orElseThrow(() -> new RuntimeException());
        ReservationRequestId reqId = new ReservationRequestId(date, employee, room);
        RequestDTO dto = new RequestDTO();
        return reqRepo.findById(reqId)
        .map(req -> {
          req.setPurpose(newReq.getPurpose());
          req.setTimeSlots(newReq.getTimeSlots());
          req.setAdmin(newReq.getAdmin());
          req.setDeniedReason(newReq.getDeniedReason());
          req.setStatus(newReq.getStatus());
          return dto.convertToDTO(reqRepo.save(req));
        })
        .orElseGet(() -> {
            newReq.setId(reqId);
            return dto.convertToDTO(reqRepo.save(newReq));
        });
    }

    @PersistenceContext
    private EntityManager entityManager;

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/requests/{date}+{booked_by}+{room_id}")
    void deleteReservationRequestTimeSlots(@PathVariable Date date, @PathVariable Integer booked_by, @PathVariable Integer room_id) {
        String sql = "DELETE FROM reservation_request_timeslot WHERE reservation_date = '"+date+"' AND booked_by = "+booked_by+" AND room_id = "+room_id+";";
        Query query = entityManager.createNativeQuery(sql);
        query.executeUpdate();
    }

}