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
    private static final Logger logger = LogManager.getLogger(BookingService.class);
    @Autowired
    private TimeSlotRepository timeSlotRepository;
    @Autowired
    private BookingRepository bookingRepository;

    public BookingService() {
    }

    public List<TimeSlot> getAllAvailableTimeSlots() {
        logger.info("GET ALL SLOTS AVAILABLE: {}", timeSlotRepository.findByStatus("free"));

        return timeSlotRepository.findByStatus("free");

    }
    public List<TimeSlot> getAllSlots() {
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
            logger.info("STATUS OF SLOT IS : {}", timeSlot.getStatus());
            timeSlot.setName(username);
            logger.info("USERNAME IS THAT BOOKED THIS IS : {}",
                    timeSlot.getName());
            timeSlotRepository.save(timeSlot);
            logger.info("TimeSlot booked: {}", timeSlot);

            // Creates a new booking and saves it
            Booking booking = new Booking();
            logger.info("CREATS NEW BOOKINGS: {}", booking);
            booking.setUserName(username);
            logger.info("USER with NAME : {}", booking.getUserName());
            booking.setTimeSlot(timeSlot);
            try {
                logger.info("Before saving Booking: {}", booking);
                bookingRepository.save(booking);
                logger.info("After saving Booking: {}", booking);
            } catch (Exception e) {
                logger.error("Error saving booking information", e);
                // Дополнительные действия, например, выброс исключения или логика восстановления
            }
        }
    }
    public void cancelBooking(Long timeSlotId) {
        TimeSlot timeSlot = timeSlotRepository.findById(timeSlotId).orElse(null);
        if (timeSlot != null && "booked".equals(timeSlot.getStatus())) {
            timeSlot.setStatus("free");
            timeSlot.setName(null); // Clear the username
            timeSlotRepository.save(timeSlot);
            logger.info("Booking was canceled for TimeSlot: {}", timeSlot);

            // Update the corresponding booking entity
            Booking booking = bookingRepository.findByTimeSlot(timeSlot);
            if (booking != null) {
                bookingRepository.deleteByTimeSlot(timeSlot); // Delete the booking record
                logger.info("Booking record deleted for TimeSlot: {}", timeSlot);
            } else {
                logger.warn("Booking not found for TimeSlot: {}", timeSlot);
            }
        } else {
            logger.warn("Unable to cancel booking for TimeSlot: {}", timeSlotId);
        }
    }

}
