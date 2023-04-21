package com.bookingSystem.reservation;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.bookingSystem.room.Room;
import com.bookingSystem.user.Employee;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ReservationId implements Serializable {

    private Date date;
    
    @ManyToOne
    @JoinColumn(name = "booked_by", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

}