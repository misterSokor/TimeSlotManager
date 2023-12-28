package com.petersokor.TimeSlotManager.controller;

import com.petersokor.TimeSlotManager.entity.TimeSlot;
import com.petersokor.TimeSlotManager.service.BookingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
/*
  This class is responsible for handling web requests related to the display
   of the booking schedule.

  The @Controller annotation designates it as a controller bean,
  and it is used to map incoming HTTP requests
  to methods that perform actions and return the appropriate view.
  it is providing users with information about available time slots for booking.
*/
@Controller
public class ScheduleController {
    private final Logger logger = LogManager.getLogger(ScheduleController.class);


    @Autowired
    private BookingService bookingService;

    /*
    The @GetMapping annotation specifies that the showSchedule method
    should handle HTTP GET requests for the"/schedule" path.
    it retrieves all available time slots from the BookingService,
    adds them to the model, and returns the "schedule" view.
     */

    @GetMapping("/schedule")  //this is a home page when you open the app
    public String showSchedule(Model model) {
        List<TimeSlot> availableTimeSlots = bookingService.getAllAvailableTimeSlots();
        List<TimeSlot> allTimeSlots = bookingService.getAllSlots();

        model.addAttribute("availableTimeSlots", availableTimeSlots);
        model.addAttribute("allTimeSlots", allTimeSlots);
        logger.info("Application started. Here is the schedule for available" +
                        " time slots: {}",
                availableTimeSlots);

        return "schedule";
    }

    // this method is used to bring full list of users who booked the lesson
    @GetMapping("/booking/allTimeSlots")
    public String showAllTimeSlots(Model model) {
        List<TimeSlot> listOfUsers = bookingService.getAllSlots();
        model.addAttribute("listOfUsers", listOfUsers);
        logger.info("Here is the List of all users: {}", listOfUsers);
        return "listOfUsers";
    }
}
