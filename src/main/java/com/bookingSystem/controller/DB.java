package com.bookingSystem.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookingSystem.reservation.Reservation;
import com.bookingSystem.reservation.ReservationId;
import com.bookingSystem.reservationRequest.ReservationRequest;
import com.bookingSystem.reservationRequest.ReservationRequestId;
import com.bookingSystem.room.Room;
import com.bookingSystem.timeSlot.TimeSlot;
import com.bookingSystem.user.Admin;
import com.bookingSystem.user.Employee;
import com.bookingSystem.user.User;

import net.bytebuddy.asm.Advice.Local;

@RestController
@RequestMapping("/db")
public class DB {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Transactional
    @GetMapping("/init")
    public void initDB() {

        
    }

    @Transactional
    @GetMapping("/init/users")
    public List<Object> initUsers() {
        User user;
        Admin admin;
        Employee employee;
        List<Object> userList = new ArrayList<>();

        user = new User("Eleni", "Papadopoulou", "elpapa@gmail.com", "12345", "6911112233");
        employee = new Employee(user.getId(), "HR", user, null, null);
        entityManager.persist(user);
        entityManager.persist(employee);
        userList.add(employee);

        user = new User("Dimitris", "Katsaros", "katsaros@gmail.com", "12345", "6911112233");
        employee = new Employee(user.getId(), "IT", user, null, null);
        entityManager.persist(user);
        entityManager.persist(employee);
        userList.add(employee);

        user = new User("Stamatis", "Metaksas", "stam.metaksas@gmail.com", "12345", "6911112233");
        employee = new Employee(user.getId(), "Sales", user, null, null);
        entityManager.persist(user);
        entityManager.persist(employee);
        userList.add(employee);

        user = new User("Giorgos", "Papadimitriou", "papadim.george@gmail.com", "12345", "6911112233");
        employee = new Employee(user.getId(), "Sales", user, null, null);
        entityManager.persist(user);
        entityManager.persist(employee);
        userList.add(employee);

        user = new User("Xristina", "Ioannidi", "xristina@gmail.com", "12345", "6911112233");
        employee = new Employee(user.getId(), "IT", user, null, null);
        entityManager.persist(user);
        entityManager.persist(employee);
        userList.add(employee);

        user = new User("Theodoros", "Papadakis", "teopapadakis123@gmail.com", "12345", "6911112233");
        employee = new Employee(user.getId(), "HR", user, null, null);
        entityManager.persist(user);
        entityManager.persist(employee);
        userList.add(employee);

        user = new User("Apostolos", "Georgiou", "georgiou@gmail.com", "12345", "6911112233");
        employee = new Employee(user.getId(), "Accounting", user, null, null);
        entityManager.persist(user);
        entityManager.persist(employee);
        userList.add(employee);

        user = new User("Valentina", "Vlamaki", "valentina123@gmail.com", "12345", "6911112233");
        employee = new Employee(user.getId(), "Accounting", user, null, null);
        entityManager.persist(user);
        entityManager.persist(employee);
        userList.add(employee);

        user = new User("Giannis", "Mixelakis", "giannis.mix@gmail.com", "12345", "6911112233");
        admin = new Admin(user.getId());
        entityManager.persist(user);
        entityManager.persist(admin);
        userList.add(admin);

        user = new User("Maria", "Stavrakaki", "mariastavrakaki@gmail.com", "12345", "6911112233");
        admin = new Admin(user.getId());
        entityManager.persist(user);
        entityManager.persist(admin);
        userList.add(admin);
        
        return userList;
    }

    @Transactional
    @GetMapping("/init/rooms")
    public List<Object> initRooms() {

        Room room;
        List<Object> roomList = new ArrayList<>();

        room = new Room(15, 1, false);
        entityManager.persist(room);
        roomList.add(room);

        room = new Room(40, 2, false);
        entityManager.persist(room);
        roomList.add(room);

        room = new Room(35, 0, true);
        entityManager.persist(room);
        roomList.add(room);

        room = new Room(22, 3, true);
        entityManager.persist(room);
        roomList.add(room);

        room = new Room(60, 5, true);
        entityManager.persist(room);
        roomList.add(room);

        room = new Room(55, 1, false);
        entityManager.persist(room);
        roomList.add(room);

        room = new Room(20, 4, true);
        entityManager.persist(room);
        roomList.add(room);

        return roomList;
    }

    @Transactional
    @GetMapping("/init/timeslots")
    public List<Object> initTimeSlots() {
        TimeSlot timeSlot;
        List<Object> timeSlotList = new ArrayList<>();

        timeSlot = new TimeSlot(8, 9);
        entityManager.persist(timeSlot);
        timeSlotList.add(timeSlot);

        timeSlot = new TimeSlot(9, 10);
        entityManager.persist(timeSlot);
        timeSlotList.add(timeSlot);

        timeSlot = new TimeSlot(10, 11);
        entityManager.persist(timeSlot);
        timeSlotList.add(timeSlot);

        timeSlot = new TimeSlot(11, 12);
        entityManager.persist(timeSlot);
        timeSlotList.add(timeSlot);

        timeSlot = new TimeSlot(12, 13);
        entityManager.persist(timeSlot);
        timeSlotList.add(timeSlot);

        timeSlot = new TimeSlot(13, 14);
        entityManager.persist(timeSlot);
        timeSlotList.add(timeSlot);

        timeSlot = new TimeSlot(14, 15);
        entityManager.persist(timeSlot);
        timeSlotList.add(timeSlot);

        timeSlot = new TimeSlot(15, 16);
        entityManager.persist(timeSlot);
        timeSlotList.add(timeSlot);

        timeSlot = new TimeSlot(16, 17);
        entityManager.persist(timeSlot);
        timeSlotList.add(timeSlot);

        timeSlot = new TimeSlot(17, 18);
        entityManager.persist(timeSlot);
        timeSlotList.add(timeSlot);

        timeSlot = new TimeSlot(18, 19);
        entityManager.persist(timeSlot);
        timeSlotList.add(timeSlot);

        timeSlot = new TimeSlot(19, 20);
        entityManager.persist(timeSlot);
        timeSlotList.add(timeSlot);

        timeSlot = new TimeSlot(20, 21);
        entityManager.persist(timeSlot);
        timeSlotList.add(timeSlot);

        return timeSlotList;
    }

    public List<TimeSlot> addTimeSlots(int[] timeSlotIds) {
        List<TimeSlot> timeSlots = new ArrayList<>();

        for (Integer id: timeSlotIds) {
            timeSlots.add(entityManager.find(TimeSlot.class, id));
        }
        return timeSlots;
    }

    @Transactional
    @GetMapping("/init/requests")
    public List<Object> initRequests() {
        ReservationRequest request;
        List<Object> requestList = new ArrayList<>();

        request = new ReservationRequest(
            new ReservationRequestId(Date.valueOf(LocalDate.of(2023, 6, 15)), 1, 4), 
            "training for new employees", "pending", null, null, addTimeSlots(new int[] {1,2,3})
        );
        entityManager.persist(request);
        requestList.add(request);

        request = new ReservationRequest(
            new ReservationRequestId(Date.valueOf(LocalDate.of(2023, 6, 15)), 4, 4),
            "annual product review", "pending", null, null, addTimeSlots(new int[] {6, 7})
        );
        entityManager.persist(request);
        requestList.add(request);

        request = new ReservationRequest(
            new ReservationRequestId(Date.valueOf(LocalDate.of(2023, 6, 15)), 7, 4),
            "teleconference with US department", "pending", null, null, addTimeSlots(new int[] {10, 11, 12, 13})
        );
        entityManager.persist(request);
        requestList.add(request);

        request = new ReservationRequest(
            new ReservationRequestId(Date.valueOf(LocalDate.of(2023, 6, 16)), 5, 7),
            "IT talk: phising scam awareness", "pending", null, null, addTimeSlots(new int[] {5})
        );
        entityManager.persist(request);
        requestList.add(request);

        request = new ReservationRequest(
            new ReservationRequestId(Date.valueOf(LocalDate.of(2023, 6, 16)), 2, 1),
            "IT talk: 'you didn't really win an iphone 11'", "pending", null, null, addTimeSlots(new int[] {4,5})
        );
        entityManager.persist(request);
        requestList.add(request);

        request = new ReservationRequest(
            new ReservationRequestId(Date.valueOf(LocalDate.of(2023, 6, 14)), 1, 5),
            "interviewing new candidates", "pending", null, null, addTimeSlots(new int[] {1, 2, 3, 4, 5, 6, 7})
        );
        entityManager.persist(request);
        requestList.add(request);

        request = new ReservationRequest(
            new ReservationRequestId(Date.valueOf(LocalDate.of(2023, 6, 14)), 6, 5),
            "interviewing new candidates", "pending", null, null, addTimeSlots(new int[] {8, 9, 10, 11, 12})
        );
        entityManager.persist(request);
        requestList.add(request);

        request = new ReservationRequest(
            new ReservationRequestId(Date.valueOf(LocalDate.of(2023, 6, 14)), 3, 6),
            "new product presentation", "pending", null, null, addTimeSlots(new int[] {5,6,7,8})
        );
        entityManager.persist(request);
        requestList.add(request);

        request = new ReservationRequest(
            new ReservationRequestId(Date.valueOf(LocalDate.of(2023, 6, 14)), 8, 6),
            "new product cost analysis", "pending", null, null, addTimeSlots(new int[] {9, 10, 11, 12})
        );
        entityManager.persist(request);
        requestList.add(request);

        request = new ReservationRequest(
            new ReservationRequestId(Date.valueOf(LocalDate.of(2023, 6, 14)), 6, 2),
            "conflict resolution talk", "pending", null, null, addTimeSlots(new int[] {3,4})
        );
        entityManager.persist(request);
        requestList.add(request);

        request = new ReservationRequest(
            new ReservationRequestId(Date.valueOf(LocalDate.of(2023, 6, 14)), 7, 1),
            "budget crisis conference with CEO", "pending", null, null, addTimeSlots(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9})
        );
        entityManager.persist(request);
        requestList.add(request);

        request = new ReservationRequest(
            new ReservationRequestId(Date.valueOf(LocalDate.of(2023, 6, 14)), 2, 3),
            "IT talk: 'why IT is the best department in the company'", "pending", null, null, addTimeSlots(new int[] {4,5,6,7,8})
        );
        entityManager.persist(request);
        requestList.add(request);

        return requestList;
    }

    // @Transactional
    // @GetMapping("/init/reservations")
    // public List<Object> initReservations() {
    //     // Reservation reservation;
    //     // ReservationRequest request;
    //     // List<Object> reservationList = new ArrayList<>();

        
    //     // reservation = new Reservation(
    //     //     new ReservationId(Date.valueOf(LocalDate.of(2023, 6, 15)), 7, 4),
    //     //     "teleconference with US department", addTimeSlots(new int[] {10, 11, 12, 13})
    //     // );
    //     // request = entityManager.find(ReservationRequest.class, new ReservationRequestId(Date.valueOf(LocalDate.of(2023, 6, 15)), 7, 4));
    //     // reservationList.add(request);
    //     // request.setStatus("approved");
    //     // request.setAdmin(new Admin(9));
    //     // entityManager.persist(reservation);
    //     // reservationList.add(reservation);

    //     // return reservationList;
    // }
}