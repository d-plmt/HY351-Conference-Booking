package com.bookingSystem.reservationRequest;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRequestRepository extends JpaRepository<ReservationRequest, ReservationRequestId> {
    
}
