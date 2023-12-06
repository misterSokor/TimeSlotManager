package com.petersokor.TimeSlotManager.service;

import com.petersokor.TimeSlotManager.repository.BookingRepository;
import com.petersokor.TimeSlotManager.repository.TimeSlotRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest

class BookingServiceTest {

    private static final Logger logger = LogManager.getLogger(BookingService.class);

    @Autowired
    private BookingService bookingService;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void testBookTimeSlot() {
        logger.info("Running testBookTimeSlot");


        logger.info("testBookTimeSlot completed");
    }

}


//
//    //next two unit tests are using Mockito annotations to mock the
//    // TimeSlotRepository and BookingRepository
//    // Mocking the TimeSlotRepository
//    @Mock
//    private TimeSlotRepository timeSlotRepository;
//
//    // Mocking the BookingRepository
//    @Mock
//    private BookingRepository bookingRepository;
//
//    // injects these objects into BookingService
//    @InjectMocks
//    private BookingService bookingService;
//
//    // Constructor initializing Mockito mocks
//    public BookingServiceTest() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    // Testing the method to retrieve all available time slots
//    @Test
//    void getAllAvailableTimeSlots() {
//        // sets up a stub for the findByStatus method
//        when(timeSlotRepository.findByStatus("free"))
//                .thenReturn(Arrays.asList(createTimeSlot("free"), createTimeSlot("free")));
//
//        // internally uses the mocked timeSlotRepository to retrieve
//        // available time slots.
//        List<TimeSlot> availableTimeSlots = bookingService.getAllAvailableTimeSlots();
//
//        // Assertion: Checking if the result is as expected
//        assertEquals(2, availableTimeSlots.size());
//    }
//
//    // Testing the method to retrieve all time slots
//    @Test
//    void getAllSlots() {
//        // Stubbing the TimeSlotRepository response
//        when(timeSlotRepository.findAll())
//                .thenReturn(Arrays.asList(createTimeSlot("free"), createTimeSlot("free")));
//
//        // Stubbing the BookingRepository response
//        when(bookingRepository.existsByTimeSlot(any()))
//                .thenReturn(true);
//
//        // Calling the method under test
//        List<TimeSlot> allSlots = bookingService.getAllSlots();
//
//        // Assertions: Checking if the results are as expected
//        assertEquals(2, allSlots.size());
//        assertEquals(true, allSlots.get(0).isOccupied());
//    }
//
//    // Helper method for creating a fake TimeSlot
//    private TimeSlot createTimeSlot(String status) {
//        TimeSlot timeSlot = new TimeSlot();
//        timeSlot.setStatus(status);
//        timeSlot.setId(1L); // Set some fictitious ID value
//        return timeSlot;
//    }
//
//
//}
