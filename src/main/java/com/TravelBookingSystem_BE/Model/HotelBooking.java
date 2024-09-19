package com.TravelBookingSystem_BE.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Hotel_Booking")
@Builder
public class HotelBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotelBookingId;

    @Column(name = "customer_name", nullable = false, length = 100)
    private String custName;

    @Column(name = "customer_gender", length = 10)
    private String custGender;

    @Column(name = "customer_email", nullable = false, unique = true)
    private String custEmail;

    @Column(name = "customer_phone", nullable = false)
    private Long custPhone;

    @Column(name = "customer_address", length = 255)
    private String custAddress;

    @Column(name = "payment_status", nullable = false)
    private boolean paymentStatus = false;

    @Column(name = "check_in_date", nullable = false)
    private LocalDate checkInDate;

    @Column(name = "check_out_date", nullable = false)
    private LocalDate checkOutDate;

    @Column(name = "total_cost", nullable = false)
    private double TotalCost;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

}
