package com.bookingSystem.reservationRequest;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bookingSystem.timeSlot.TimeSlot;
import com.bookingSystem.user.Admin;
import com.bookingSystem.user.Employee;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "reservation_request")
public class ReservationRequest {

    @EmbeddedId
    private ReservationRequestId id;

    private String purpose;

    private String status;

    private String deniedReason;



    @ManyToOne
    @JoinColumn(
        name = "admin_id", 
        referencedColumnName = "id", 
        nullable = true)
    private Admin admin;

    @ManyToOne
    @JoinColumn(
        name = "booked_by", 
        insertable = false, 
        updatable = false)
    private Employee employee;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "reservation_request_timeslot", 
        joinColumns = { 
            @JoinColumn(name = "reservation_date"),
            @JoinColumn(name = "booked_by"), 
            @JoinColumn(name = "room_id") }, 
        inverseJoinColumns = { 
            @JoinColumn(name = "timeslot_id") }
    )
    List<TimeSlot> timeSlots = new ArrayList<>();

}