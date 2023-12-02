package com.petersokor.TimeSlotManager.repository;

import com.petersokor.TimeSlotManager.entity.Booking;
import com.petersokor.TimeSlotManager.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
This interface is used for
interacting with the database for the Booking entity.
also acts as a DAO
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    boolean existsByTimeSlot(TimeSlot timeSlot);
}