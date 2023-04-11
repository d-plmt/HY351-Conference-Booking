package com.bookingSystem.timeSlot;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.bookingSystem.reservation.Reservation;
import com.bookingSystem.reservationRequest.ReservationRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "time_slot")
public class TimeSlot {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "timeslot_seq")
    @Column(name = "id")
    private Integer id;

    private Integer startTime;
    private Integer endTime;
    
    public TimeSlot(Integer startTime, Integer endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
    @Override
    public String toString() {
        return "TimeSlot [id=" + id + ", startTime=" + startTime + ", endTime=" + endTime + "]";
    }

    

    // @ManyToMany(cascade = CascadeType.ALL, mappedBy = "timeSlots")
    // private List<Reservation> reservations = new ArrayList<>();

    // @ManyToMany(cascade = CascadeType.ALL, mappedBy = "timeSlots")
    // private List<ReservationRequest> reservationRequests = new ArrayList<>();



}
