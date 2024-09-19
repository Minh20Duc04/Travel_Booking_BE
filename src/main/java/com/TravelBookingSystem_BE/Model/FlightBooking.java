package com.TravelBookingSystem_BE.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "flight_booking")
public class FlightBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightBookingId;

    @Column(name = "cust_name", nullable = false, length = 100)
    private String custName;

    @Column(name = "cust_gender", nullable = false, length = 10)
    private String custGender;

    @Column(name = "cust_email", nullable = false, length = 100, unique = true)
    private String custEmail;

    @Column(name = "cust_phone", nullable = false)
    private Long custPhone;

    @Column(name = "cust_address", nullable = false, length = 255)
    private String custAddress;

    @Column(name = "payment_status", nullable = false)
    private boolean paymentStatus = false;

    @Column(name = "departure_date", nullable = false)
    private LocalDate departureDate;

    @Column(name = "from_location", nullable = false, length = 100)
    private String fromLocation;

    @Column(name = "to_location", nullable = false, length = 100)
    private String toLocation;

    @Column(name = "total_cost", nullable = false)
    private double totalCost;

    @Column(name = "selected_seats", nullable = false, length = 10)
    private String selectedSeats;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "flight_id", nullable = false) // Đổi từ flight_number sang flight_id
    private Flight flight;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;
}

