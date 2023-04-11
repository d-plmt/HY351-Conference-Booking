package com.bookingSystem.reservationRequest;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.bookingSystem.room.Room;
import com.bookingSystem.user.Employee;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ReservationRequestId implements Serializable {

    private Date date;
    
    @ManyToOne
    @JoinColumn(name = "booked_by", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

}
