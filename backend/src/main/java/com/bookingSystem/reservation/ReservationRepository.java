package com.bookingSystem.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ReservationRepository extends JpaRepository<Reservation, ReservationId> {
    
}

