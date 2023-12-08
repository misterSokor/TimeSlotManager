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
class BookingServiceTest {

    private static final Logger logger = LogManager.getLogger(BookingService.class);

    @Autowired
    BookingService  bookingService = new BookingService();
    @Autowired
    private TimeSlotRepository timeSlotRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    void getAllAvailableTimeSlots() {
        // for instance, I have free time slots in the table
        TimeSlot freeTimeSlot1 = new TimeSlot();
        TimeSlot freeTimeSlot2 = new TimeSlot();
        timeSlotRepository.save(freeTimeSlot1);
        timeSlotRepository.save(freeTimeSlot2);

        // here I get the actual number time slots
        List<TimeSlot> initialFreeSlots = bookingService.getAllAvailableTimeSlots();
        int initialFreeSlotCount = initialFreeSlots.size();

        // check if they are free
        assertNotNull(initialFreeSlots);
        assertTrue(initialFreeSlots.stream().allMatch(slot -> "free".equals(slot.getStatus())));

        // invoke method getAllAvailableTimeSlots that I want to test
        List<TimeSlot> availableTimeSlots = bookingService.getAllAvailableTimeSlots();

        //  get the number of free slots after possible changes
        List<TimeSlot> updatedFreeSlots = bookingService.getAllAvailableTimeSlots();
        int updatedFreeSlotCount = updatedFreeSlots.size();

        // check that I still have free slots and the number has not changed
        assertNotNull(availableTimeSlots);
        assertTrue(availableTimeSlots.stream().allMatch(slot -> "free".equals(slot.getStatus())));
        assertEquals(initialFreeSlotCount, updatedFreeSlotCount);
    }

    @Test
    void getAllSlots() {

        TimeSlot freeTimeSlot1 = new TimeSlot();
        TimeSlot bookedTimeSlot1 = new TimeSlot();
        timeSlotRepository.saveAll(List.of(freeTimeSlot1, bookedTimeSlot1));


        List<TimeSlot> initialAllSlots = bookingService.getAllSlots();
        int initialSlotCount = initialAllSlots.size();


        assertNotNull(initialAllSlots);
        assertTrue(initialAllSlots.stream().anyMatch(slot -> "free".equals(slot.getStatus())));
        assertTrue(initialAllSlots.stream().anyMatch(slot -> "booked".equals(slot.getStatus())));


        List<TimeSlot> allTimeSlots = bookingService.getAllSlots();


        List<TimeSlot> updatedAllSlots = bookingService.getAllSlots();
        int updatedSlotCount = updatedAllSlots.size();


        assertNotNull(allTimeSlots);
        assertTrue(allTimeSlots.stream().anyMatch(slot -> "free".equals(slot.getStatus())));
        assertTrue(allTimeSlots.stream().anyMatch(slot -> "booked".equals(slot.getStatus())));
        assertEquals(initialSlotCount, updatedSlotCount);
    }

    @Test
    @Transactional
    void bookTimeSlot() {

        TimeSlot freeTimeSlot = new TimeSlot();
        timeSlotRepository.save(freeTimeSlot);
        entityManager.flush();

        logger.info("Before invoking bookTimeSlot. Free TimeSlot ID: {}", freeTimeSlot.getId());


        bookingService.bookTimeSlot(freeTimeSlot.getId(), "David");

        logger.info("After invoking bookTimeSlot. Free TimeSlot ID: {}", freeTimeSlot.getId());

        TimeSlot bookedTimeSlot = timeSlotRepository.findById(freeTimeSlot.getId()).orElse(null);
        Booking booking = bookingRepository.findByTimeSlot(bookedTimeSlot);

        logger.info("bookedTimeSlot: {}", bookedTimeSlot);
        logger.info("booking: {}", booking);

        assertNotNull(bookedTimeSlot);
        assertEquals("booked", bookedTimeSlot.getStatus());
        assertEquals("David", bookedTimeSlot.getName());

        assertNotNull(booking);
        assertEquals("David", booking.getUserName());
        assertEquals(bookedTimeSlot, booking.getTimeSlot());
    }


    @Test
    @Transactional
    void cancelBooking() {

        TimeSlot bookedTimeSlot = new TimeSlot();
        bookedTimeSlot.setName("Jessica");
        timeSlotRepository.save(bookedTimeSlot);


        bookingService.cancelBooking(bookedTimeSlot.getId());


        TimeSlot canceledTimeSlot = timeSlotRepository.findById(bookedTimeSlot.getId()).orElse(null);
        Booking canceledBooking = bookingRepository.findByTimeSlot(canceledTimeSlot);

        assertNotNull(canceledTimeSlot);
        assertEquals("free", canceledTimeSlot.getStatus());
        assertNull(canceledTimeSlot.getName());

        assertNull(canceledBooking);
    }

}