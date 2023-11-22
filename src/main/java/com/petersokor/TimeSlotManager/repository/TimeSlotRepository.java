package com.petersokor.TimeSlotManager.repository;

// TimeSlotRepository.java
import com.petersokor.TimeSlotManager.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

/*
This interface is used to perform database operations
related to the TimeSlot entity.
The interface extends JpaRepository, which is a Spring Data
interface providing generic CRUD, acts as a DAO
 */
@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {

    List<TimeSlot> findByDayOfWeekAndStatus(DayOfWeek dayOfWeek, String status);

    List<TimeSlot> findByStatus(String status);
}
