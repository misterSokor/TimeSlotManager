package com.petersokor.TimeSlotManager.entity;

import jakarta.persistence.*;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "slot_id")
    private TimeSlot timeSlot;
    @Column(name = "user_name")
    private String userName;


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public void setOccupied(boolean isSlotOccupied) {
    }
}