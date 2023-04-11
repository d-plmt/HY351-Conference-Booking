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

import com.bookingSystem.reservationRequest.ReservationRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @MapsId
    private User user;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "admin")
    private List<ReservationRequest> reservationRequests;

    @Override
    public String toString() {
        return "Admin [id=" + id + ", user=" + user + ", reservationRequests=" + reservationRequests + "]";
    }

    public Admin(Integer id) {
        this.id = id;
    }
}