package com.petersokor.TimeSlotManager.service;

import com.petersokor.TimeSlotManager.entity.TimeSlot;
import com.petersokor.TimeSlotManager.repository.BookingRepository;
import com.petersokor.TimeSlotManager.repository.TimeSlotRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class BookingServiceTest {

    // Mocking the TimeSlotRepository
    @Mock
    private TimeSlotRepository timeSlotRepository;

    // Mocking the BookingRepository
    @Mock
    private BookingRepository bookingRepository;

    // Creating an instance of BookingService to be tested
    @InjectMocks
    private BookingService bookingService;

    // Constructor initializing Mockito mocks
    public BookingServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    // Testing the method to retrieve all available time slots
    @Test
    void getAllAvailableTimeSlots() {
        // Stubbing the TimeSlotRepository response
        when(timeSlotRepository.findByStatus("free"))
                .thenReturn(Arrays.asList(createTimeSlot("free"), createTimeSlot("free")));

        // Calling the method under test
        List<TimeSlot> availableTimeSlots = bookingService.getAllAvailableTimeSlots();

        // Assertion: Checking if the result is as expected
        assertEquals(2, availableTimeSlots.size());
    }

    // Testing the method to retrieve all time slots
    @Test
    void getAllSlots() {
        // Stubbing the TimeSlotRepository response
        when(timeSlotRepository.findAll())
                .thenReturn(Arrays.asList(createTimeSlot("free"), createTimeSlot("free")));

        // Stubbing the BookingRepository response
        when(bookingRepository.existsByTimeSlot(any()))
                .thenReturn(true);

        // Calling the method under test
        List<TimeSlot> allSlots = bookingService.getAllSlots();

        // Assertions: Checking if the results are as expected
        assertEquals(2, allSlots.size());
        assertEquals(true, allSlots.get(0).isOccupied());
    }

    // Helper method for creating a fake TimeSlot
    private TimeSlot createTimeSlot(String status) {
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setStatus(status);
        timeSlot.setId(1L); // Set some fictitious ID value
        return timeSlot;
    }
}
