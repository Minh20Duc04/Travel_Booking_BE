package com.TravelBookingSystem_BE.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "Cab_Booking")
public class CabBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cabBookingId;

    @Column(name = "customer_name")
    private String custName;

    @Column(name = "drop_date")
    private LocalDate dropDate;

    @Column(name = "customer_gender")
    private String custGender;

    @Column(name = "customer_email")
    private String custEmail;

    @Column(name = "customer_phone")
    private Long custPhone;

    @Column(name = "customer_address")
    private String custAddress;

    @Column(name = "payment_status")
    private boolean paymentStatus = false;

    @Column(name = "pick_up_date")
    private LocalDate pickUpDate;

    @Column(name = "pick_up_location")
    private String pickUpLocation;

    @Column(name = "drop_location")
    private String dropLocation;

    @Column(name = "total_cost")
    private double totalCost;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cab_id", nullable = false)
    private Cab cab;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

}
