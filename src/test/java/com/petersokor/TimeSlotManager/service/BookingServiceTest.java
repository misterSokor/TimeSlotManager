package com.petersokor.TimeSlotManager.service;

import com.petersokor.TimeSlotManager.entity.Booking;
import com.petersokor.TimeSlotManager.entity.TimeSlot;
import com.petersokor.TimeSlotManager.repository.BookingRepository;
import com.petersokor.TimeSlotManager.repository.TimeSlotRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BookingServiceTest {

    private static final Logger logger = LogManager.getLogger(BookingService.class);

    @Autowired
    BookingService bookingService = new BookingService();
    @Autowired
    private TimeSlotRepository timeSlotRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @PersistenceContext
    private EntityManager entityManager;

    // Common method for creating and saving time slots
    private TimeSlot createAndSaveTimeSlot(String status) {
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setStatus(status);
        timeSlotRepository.save(timeSlot);
        return timeSlot;
    }

    @Test
    @Transactional
    void getAllAvailableTimeSlots() {
        // Create free time slots in the table
        TimeSlot freeTimeSlot1 = createAndSaveTimeSlot("free");
        TimeSlot freeTimeSlot2 = createAndSaveTimeSlot("free");

        // Get the actual number of time slots
        List<TimeSlot> initialFreeSlots = bookingService.getAllAvailableTimeSlots();
        int initialFreeSlotCount = initialFreeSlots.size();

        // Check if they are free
        assertNotNull(initialFreeSlots);
        assertTrue(initialFreeSlots.stream().allMatch(slot -> "free".equals(slot.getStatus())));

        // Invoke method getAllAvailableTimeSlots that I want to test
        List<TimeSlot> availableTimeSlots = bookingService.getAllAvailableTimeSlots();

        // Get the number of free slots after possible changes
        List<TimeSlot> updatedFreeSlots = bookingService.getAllAvailableTimeSlots();
        int updatedFreeSlotCount = updatedFreeSlots.size();

        // Check that I still have free slots and the number has not changed
        assertNotNull(availableTimeSlots);
        assertTrue(availableTimeSlots.stream().allMatch(slot -> "free".equals(slot.getStatus())));
        assertEquals(initialFreeSlotCount, updatedFreeSlotCount);
    }

    @Test
    void getAllSlots() {
        // Create free and booked time slots
        TimeSlot freeTimeSlot1 = createAndSaveTimeSlot("free");
        TimeSlot bookedTimeSlot1 = createAndSaveTimeSlot("booked");

        // Get the initial number of all time slots
        List<TimeSlot> initialAllSlots = bookingService.getAllSlots();
        int initialSlotCount = initialAllSlots.size();

        assertNotNull(initialAllSlots);
        assertTrue(initialAllSlots.stream().anyMatch(slot -> "free".equals(slot.getStatus())));
        assertTrue(initialAllSlots.stream().anyMatch(slot -> "booked".equals(slot.getStatus())));

        // Invoke method getAllSlots that I want to test
        List<TimeSlot> allTimeSlots = bookingService.getAllSlots();

        // Get the number of all slots after possible changes
        List<TimeSlot> updatedAllSlots = bookingService.getAllSlots();
        int updatedSlotCount = updatedAllSlots.size();

        assertNotNull(allTimeSlots);
        assertTrue(allTimeSlots.stream().anyMatch(slot -> "free".equals(slot.getStatus())));
        assertTrue(allTimeSlots.stream().anyMatch(slot -> "booked".equals(slot.getStatus())));
        assertEquals(initialSlotCount, updatedSlotCount);
    }

    // old method
    @Test
    @Transactional
    void bookTimeSlot() {
        // Create a free time slot
        TimeSlot freeTimeSlot = createAndSaveTimeSlot("free");

        // Invoke method bookTimeSlot that I want to test
        bookingService.bookTimeSlot(freeTimeSlot.getId(), "David");

        // Retrieve the booked time slot and associated booking from the repository
        TimeSlot bookedTimeSlot = timeSlotRepository.findById(freeTimeSlot.getId()).orElse(null);
        Booking booking = bookingRepository.findByTimeSlot(bookedTimeSlot);

        // Check that the time slot is booked with the correct status and name
        assertNotNull(bookedTimeSlot);
        assertEquals("booked", bookedTimeSlot.getStatus());
        assertEquals("David", bookedTimeSlot.getName());

        // Check that the booking is created with the correct user name and associated time slot
        assertNotNull(booking);
        assertEquals("David", booking.getUserName());
        assertEquals(bookedTimeSlot, booking.getTimeSlot());
    }

    @Test
    @Transactional
    void cancelBooking() {
        // Create a booked time slot
        TimeSlot bookedTimeSlot = createAndSaveTimeSlot("booked");

        // Invoke method cancelBooking that I want to test
        bookingService.cancelBooking(bookedTimeSlot.getId());

        // Retrieve the canceled time slot and associated booking from the repository
        TimeSlot canceledTimeSlot = timeSlotRepository.findById(bookedTimeSlot.getId()).orElse(null);
        Booking canceledBooking = bookingRepository.findByTimeSlot(canceledTimeSlot);

        // Check that the time slot is now free with no name
        assertNotNull(canceledTimeSlot);
        assertEquals("free", canceledTimeSlot.getStatus());
        assertNull(canceledTimeSlot.getName());

        // Check that the associated booking is null, indicating cancellation
        assertNull(canceledBooking);
    }
}
