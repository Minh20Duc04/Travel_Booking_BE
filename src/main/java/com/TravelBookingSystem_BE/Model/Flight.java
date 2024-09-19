package com.TravelBookingSystem_BE.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Flight")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightId;

    @Column(name = "airline", nullable = false, length = 100)
    private String airline;

    @Column(name = "from_location", nullable = false, length = 100)
    private String fromLocation;

    @Column(name = "drop_location", nullable = false, length = 100)
    private String dropLocation;

    @Column(name = "departure_date", nullable = false)
    private LocalDate departureDate;

    @Column(name = "arrival_date", nullable = false)
    private LocalDate arrivalDate;

    @Column(name = "price", nullable = false)
    private double price;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
    private List<FlightBooking> bookings;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;
}
