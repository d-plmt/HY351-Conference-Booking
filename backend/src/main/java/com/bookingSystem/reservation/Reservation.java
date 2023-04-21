package com.bookingSystem.reservation;

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
import com.bookingSystem.user.Employee;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Setter;
import lombok.AccessLevel;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "reservation")
public class Reservation {

    @EmbeddedId
    private ReservationId id;

    private String purpose;

    @ManyToOne
    @JoinColumn(
        name = "booked_by", 
        insertable = false, 
        updatable = false)
    private Employee employee;

    
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "reservation_timeslot", 
        joinColumns = { 
            @JoinColumn(name = "reservation_date"), 
            @JoinColumn(name = "booked_by"), 
            @JoinColumn(name = "room_id") }, 
        inverseJoinColumns = { 
            @JoinColumn(name = "timeslot_id") 
        }
    )
    @Getter(AccessLevel.NONE)
    List<TimeSlot> timeSlots = new ArrayList<>();

    public List<TimeSlot> getTimeSlots() {
        return this.timeSlots;
    }

}