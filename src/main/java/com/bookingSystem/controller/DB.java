package com.bookingSystem.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.bookingSystem.user.UserRepository;

import net.bytebuddy.asm.Advice.Local;

@RestController
@RequestMapping("/db")
public class DB {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserController userController;

    @Autowired 
    private RoomController roomController;

    @Autowired
    private TimeSlotController timeSlotController;

    @Autowired
    private ReservationController reservationController;
    
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


        user = userController.newUser(new User(null, null, null, "Eleni", "Papadopoulou", "elpap@gmail.com", "12345", "6912121215"));
        employee = userController.newEmployee(new Employee(user.getId(), "HR", user, null, null));
        userList.add(employee);


        user = userController.newUser(new User(null, null, null, "Dimitris", "Katsaros", "katsaros@gmail.com", "12345", "6911112233"));
        employee = userController.newEmployee(new Employee(user.getId(), "IT", user, null, null));
        userList.add(employee);

        user = userController.newUser(new User(null, null, null, "Stamatis", "Metaksas", "stam.metaksas@gmail.com", "12345", "6911112233"));
        employee = userController.newEmployee(new Employee(user.getId(), "Sales", user, null, null));
        userList.add(employee);

        user = userController.newUser(new User(null, null, null, "Giorgos", "Papadimitriou", "papadim.george@gmail.com", "12345", "6911112233"));
        employee = userController.newEmployee(new Employee(user.getId(), "Sales", user, null, null));
        userList.add(employee);

        user = userController.newUser(new User(null, null, null, "Xristina", "Ioannidi", "xristina@gmail.com", "12345", "6911112233"));
        employee = userController.newEmployee(new Employee(user.getId(), "IT", user, null, null));
        userList.add(employee);

        user = userController.newUser(new User(null, null, null, "Theodoros", "Papadakis", "teopapadakis123@gmail.com", "12345", "6911112233"));
        employee = userController.newEmployee(new Employee(user.getId(), "HR", user, null, null));
        userList.add(employee);

        user = userController.newUser(new User(null, null, null, "Apostolos", "Georgiou", "georgiou@gmail.com", "12345", "6911112233"));
        employee = userController.newEmployee(new Employee(user.getId(), "Accounting", user, null, null));
        userList.add(employee);

        user = userController.newUser(new User(null, null, null, "Valentina", "Vlamaki", "valentina123@gmail.com", "12345", "6911112233"));
        employee = userController.newEmployee(new Employee(user.getId(), "Accounting", user, null, null));
        userList.add(employee);

        user = userController.newUser(new User(null, null, null, "Giannis", "Mixelakis", "giannis.mix@gmail.com", "12345", "6911112233"));
        admin = userController.newAdmin(new Admin(user.getId(), user, null));
        userList.add(admin);

        user = userController.newUser(new User(null, null, null, "Maria", "Stavrakaki", "mariastavrakaki@gmail.com", "12345", "6911112233"));
        admin = userController.newAdmin(new Admin(user.getId(), user, null));
        userList.add(admin);
        
        return userList;
    }


    @Transactional
    @GetMapping("/init/rooms")
    public List<Object> initRooms() {

        Room room;
        List<Object> roomList = new ArrayList<>();

        room = roomController.newRoom(new Room(null, 15, 1, false, null));
        roomList.add(room);

        room = roomController.newRoom(new Room(null, 40, 2, false, null));
        roomList.add(room);

        room = roomController.newRoom(new Room(null, 35, 0, true, null));
        roomList.add(room);

        room = roomController.newRoom(new Room(null, 22, 3, true, null));
        roomList.add(room);

        room = roomController.newRoom(new Room(null, 60, 5, true, null));
        roomList.add(room);

        room = roomController.newRoom(new Room(null, 55, 1, false, null));
        roomList.add(room);

        room = roomController.newRoom(new Room(null, 20, 4, true, null));
        roomList.add(room);

        return roomList;
    }


    @Transactional
    @GetMapping("/init/timeslots")
    public List<Object> initTimeSlots() {
        TimeSlot timeSlot;
        List<Object> timeSlotList = new ArrayList<>();

        timeSlot = timeSlotController.newTimeSlot(new TimeSlot(null, 8, 9));
        timeSlotList.add(timeSlot);

        timeSlot = timeSlotController.newTimeSlot(new TimeSlot(null, 9, 10));
        timeSlotList.add(timeSlot);

        timeSlot = timeSlotController.newTimeSlot(new TimeSlot(null, 10, 11));
        timeSlotList.add(timeSlot);

        timeSlot = timeSlotController.newTimeSlot(new TimeSlot(null, 11, 12));
        timeSlotList.add(timeSlot);

        timeSlot = timeSlotController.newTimeSlot(new TimeSlot(null, 12, 13));
        timeSlotList.add(timeSlot);

        timeSlot = timeSlotController.newTimeSlot(new TimeSlot(null, 13, 14));
        timeSlotList.add(timeSlot);

        timeSlot = timeSlotController.newTimeSlot(new TimeSlot(null, 14, 15));
        timeSlotList.add(timeSlot);

        timeSlot = timeSlotController.newTimeSlot(new TimeSlot(null, 15, 16));
        timeSlotList.add(timeSlot);

        timeSlot = timeSlotController.newTimeSlot(new TimeSlot(null, 16, 17));
        timeSlotList.add(timeSlot);

        timeSlot = timeSlotController.newTimeSlot(new TimeSlot(null, 17, 18));
        timeSlotList.add(timeSlot);

        timeSlot = timeSlotController.newTimeSlot(new TimeSlot(null, 18, 19));
        timeSlotList.add(timeSlot);

        timeSlot = timeSlotController.newTimeSlot(new TimeSlot(null, 19, 20));
        timeSlotList.add(timeSlot);

        timeSlot = timeSlotController.newTimeSlot(new TimeSlot(null, 20, 21));
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
        ReservationRequestId requestId; 
        Employee employee;
        Room room;
        List<Object> requestList = new ArrayList<>();

        employee = userController.findEmployee(1);
        room = roomController.find(4);
        requestId = new ReservationRequestId(Date.valueOf(LocalDate.of(2023, 6, 15)), employee, room);
        request = reservationController.newReq(new ReservationRequest(requestId, "training for new employees", "pending", null, null, employee, addTimeSlots(new int[] {1,2,3})));

        requestList.add(request);

        employee = userController.findEmployee(4);
        room = roomController.find(4);
        requestId = new ReservationRequestId(Date.valueOf(LocalDate.of(2023, 6, 15)), employee, room);
        request = reservationController.newReq(new ReservationRequest(requestId,
            "annual product review", "pending", null, null, employee, addTimeSlots(new int[] {6, 7})));
        requestList.add(request);

        employee = userController.findEmployee(7);
        room = roomController.find(4);
        requestId = new ReservationRequestId(Date.valueOf(LocalDate.of(2023, 6, 15)), employee, room);
        request = reservationController.newReq(new ReservationRequest(requestId, "teleconference with US department", "pending", null, null, employee, addTimeSlots(new int[] {10, 11, 12, 13})));
        requestList.add(request);


        employee = userController.findEmployee(5);
        room = roomController.find(7);
        requestId = new ReservationRequestId(Date.valueOf(LocalDate.of(2023, 6, 16)), employee, room);
        request = reservationController.newReq(new ReservationRequest(
            requestId, "IT talk: phising scam awareness", "pending", null, null, employee, addTimeSlots(new int[] {5})));
        requestList.add(request);

        employee = userController.findEmployee(2);
        room = roomController.find(1);
        requestId = new ReservationRequestId(Date.valueOf(LocalDate.of(2023, 6, 16)), employee, room);
        request = reservationController.newReq(new ReservationRequest(requestId, "IT talk: 'you didn't really win an iphone 11'", "pending", null, null, employee, addTimeSlots(new int[] {4,5})));
        requestList.add(request);

        employee = userController.findEmployee(1);
        room = roomController.find(5);
        requestId = new ReservationRequestId(Date.valueOf(LocalDate.of(2023, 6, 14)), employee, room);
        request = reservationController.newReq(new ReservationRequest(requestId, "interviewing new candidates", "pending", null, null, employee, addTimeSlots(new int[] {1, 2, 3, 4, 5, 6, 7})));
        requestList.add(request);

        employee = userController.findEmployee(6);
        room = roomController.find(5);
        requestId = new ReservationRequestId(Date.valueOf(LocalDate.of(2023, 6, 14)), employee, room);
        request = reservationController.newReq(new ReservationRequest(requestId, "interviewing new candidates", "pending", null, null, employee, addTimeSlots(new int[] {8, 9, 10, 11, 12})));
        requestList.add(request);

        employee = userController.findEmployee(3);
        room = roomController.find(6);
        requestId = new ReservationRequestId(Date.valueOf(LocalDate.of(2023, 6, 14)), employee, room);
        request = reservationController.newReq(new ReservationRequest(requestId, "new product presentation", "pending", null, null, employee, addTimeSlots(new int[] {5,6,7,8})));
        requestList.add(request);

        employee = userController.findEmployee(8);
        room = roomController.find(6);
        requestId = new ReservationRequestId(Date.valueOf(LocalDate.of(2023, 6, 14)), employee, room);
        request = reservationController.newReq(new ReservationRequest(requestId,
            "new product cost analysis", "pending", null, null, employee, addTimeSlots(new int[] {9, 10, 11, 12})));
        requestList.add(request);

        employee = userController.findEmployee(6);
        room = roomController.find(2);
        requestId = new ReservationRequestId(Date.valueOf(LocalDate.of(2023, 6, 14)), employee, room);
        request = reservationController.newReq(new ReservationRequest(
            requestId, "conflict resolution talk", "pending", null, null, employee, addTimeSlots(new int[] {3,4})));
        requestList.add(request);

        employee = userController.findEmployee(7);
        room = roomController.find(1);
        requestId = new ReservationRequestId(Date.valueOf(LocalDate.of(2023, 6, 14)), employee, room);
        request = reservationController.newReq(new ReservationRequest(
            requestId, "budget crisis conference with CEO", "pending", null, null, employee, addTimeSlots(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9})));
        requestList.add(request);

        employee = userController.findEmployee(2);
        room = roomController.find(3);
        requestId = new ReservationRequestId(Date.valueOf(LocalDate.of(2023, 6, 14)), employee, room);
        request = new ReservationRequest(requestId, "IT talk: 'why IT is the best department in the company'", "pending", null, null, employee, addTimeSlots(new int[] {4,5,6,7,8}));
        requestList.add(request);

        return requestList;
    }

    @Transactional
    @GetMapping("/init/reservations")
    public List<Object> initReservations() {
        Reservation reservation;
        ReservationRequest request;
        ReservationRequestId requestId; 
        ReservationId reservationId;
        Employee employee;
        Room room;
        List<Object> reservationList = new ArrayList<>();

        employee = userController.findEmployee(7);
        room = roomController.find(4);

        reservationId = new ReservationId(Date.valueOf(LocalDate.of(2023, 6, 15)), employee, room);
        requestId = new ReservationRequestId(Date.valueOf(LocalDate.of(2023, 6, 15)), employee, room);
        request = reservationController.findReq(requestId);
        request.setAdmin(new Admin(9, null, null));
        request.setStatus("approved");
        reservationController.updateReq(request, requestId);
        reservation = reservationController.newRes(new Reservation(reservationId, request.getPurpose(), request.getEmployee(), request.getTimeSlots()));
        // reservationId = reservationController.findRes(new ReservationId(Date.valueOf(LocalDate.of(2023, 6, 15)), employee, room)).getId();

        // reservation = new Reservation(reservationId, "teleconference with US department", employee, addTimeSlots(new int[] {10, 11, 12, 13}));
        // request = entityManager.find(ReservationRequest.class, new ReservationRequestId(Date.valueOf(LocalDate.of(2023, 6, 15)), employee, room));
        // reservationList.add(request);
        // request.setStatus("approved");
        // entityManager.persist(reservation);
        // reservationList.add(reservation);

        return reservationList;
    }
}