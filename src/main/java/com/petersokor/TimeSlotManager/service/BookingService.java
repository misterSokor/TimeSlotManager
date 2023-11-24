/*
  This class provides logic related to booking time slots
  and interacts with the database through
  the TimeSlotRepository and BookingRepository.
  It includes methods for retrieving available
  time slots, booking a time slot,
  and fetching all available time slots.
*/

package com.petersokor.TimeSlotManager.service;

import com.petersokor.TimeSlotManager.entity.Booking;
import com.petersokor.TimeSlotManager.entity.TimeSlot;
import com.petersokor.TimeSlotManager.repository.BookingRepository;
import com.petersokor.TimeSlotManager.repository.TimeSlotRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
@Transactional annotation is used
to ensure that each method is wrapped in a transaction.
 */
@Service
@Transactional
public class BookingService {
    private final Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private TimeSlotRepository timeSlotRepository;
    @Autowired
    private BookingRepository bookingRepository;
    public List<TimeSlot> getAllAvailableTimeSlots() {
        return timeSlotRepository.findByStatus("free");
    }
    public List<TimeSlot> getAllSlotsWithAvailability() {
        List<TimeSlot> allTimeSlots = timeSlotRepository.findAll();

        // here we mark slots as occupied;
        for (TimeSlot timeSlot : allTimeSlots) {
            boolean isSlotOccupied = bookingRepository.existsByTimeSlot(timeSlot);
            timeSlot.setOccupied(isSlotOccupied);
        }
        return allTimeSlots;
    }

    public void bookTimeSlot(Long timeSlotId, String username) {
        TimeSlot timeSlot = timeSlotRepository.findById(timeSlotId).orElse(null);
        if (timeSlot != null && "free".equals(timeSlot.getStatus())) {

            timeSlot.setStatus("booked");
            timeSlot.setName(username);
            timeSlotRepository.save(timeSlot);
            logger.info("TimeSlot booked: {}", timeSlot);

            // Creates a new booking and saves it
            Booking booking = new Booking();
            booking.setUserName(username);
            booking.setTimeSlot(timeSlot);
            bookingRepository.save(booking);
        }
    }
}
