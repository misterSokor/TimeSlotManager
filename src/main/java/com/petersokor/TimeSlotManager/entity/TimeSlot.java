package com.petersokor.TimeSlotManager.entity;

import jakarta.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

/*
  This class representing a booking in the TimeSlotManager application.
  It is a JPA entity, and the fields are mapped to columns in the database.
  It defines a primary key (id) generated using the identity strategy, and
  establishes a Many-to-One relationship with the TimeSlot entity through the
  timeSlot field, which is mapped to the "slot_id" column in the database.
  Class serves for persisting booking information in the database.
*/
@Entity
public class TimeSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private String status;
    @Transient
    private boolean occupied;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }


    public LocalTime getStartTime() {
        return startTime;
    }

    // Устанавливаем какое-то фиктивное значение ID
    public void setId(long l) {
    }
}


