package com.bookingSystem.user;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.bookingSystem.reservation.Reservation;
import com.bookingSystem.reservationRequest.ReservationRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String department;

    // @OneToOne(cascade = CascadeType.ALL)
    // @JoinColumn(name = "id", referencedColumnName = "id")
    // private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @MapsId
    private User user;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<ReservationRequest> reservationRequests;

    @Override
    public String toString() {
        return "Employee [id=" + id + ", department=" + department + ", user=" + user + ", reservations=" + reservations
                + ", reservationRequests=" + reservationRequests + "]";
    }

}