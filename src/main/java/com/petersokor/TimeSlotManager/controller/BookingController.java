package com.petersokor.TimeSlotManager.controller;

import com.petersokor.TimeSlotManager.entity.TimeSlot;
import com.petersokor.TimeSlotManager.service.BookingService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/*
  This class handles web requests related to time
  slot booking and schedule display.

  The @Controller annotation designates that it as a controller bean,
  and it is responsible for mapping incoming HTTP requests to methods that
  perform actions and return the appropriate view.
*/
@Controller
public class BookingController {
    private final Logger logger = (Logger) LogManager.getLogger(this.getClass());

    @Autowired
    private BookingService bookingService;

    /*
    "/booking/schedule": Displays the booking schedule by retrieving
    all available time slots and adding them to the model before returning the
    "schedule" view.
     */
    @GetMapping("/booking/schedule")
    public String showBookingSchedule(Model model) {
        List<TimeSlot> availableTimeSlots = bookingService.getAllAvailableTimeSlots();
        model.addAttribute("availableTimeSlots", availableTimeSlots);
        return "schedule";
    }

    @PostMapping("/booking/book/{slotId}")
    public String bookTimeSlot(@PathVariable Long slotId, @RequestParam String username) {
        bookingService.bookTimeSlot(slotId, username);
        return "redirect:/schedule";
    }

    @PostMapping("/booking/cancel/{slotId}")
    public String cancelBooking(@PathVariable Long slotId) {
        bookingService.cancelBooking(slotId);
        return "redirect:/schedule";
    }

}
