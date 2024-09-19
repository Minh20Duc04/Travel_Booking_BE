package com.TravelBookingSystem_BE.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
@Table(name = "Cab")
public class Cab {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cabId;

    @Column(name = "cab_type")
    private String cabType;

    @Column(name = "pickup_location")
    private String pickUpLocation;

    @Column(name = "drop_location")
    private String dropLocation;

    @Column(name = "cab_price")
    private double cabPrice;

    @Column(name = "pickup_date")
    private LocalDate pickUpDate;

    @Column(name = "drop_off_date")
    private LocalDate dropOffDate;

    @OneToMany(mappedBy = "cab", cascade = CascadeType.ALL)
    private List<CabBooking> bookings;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;


}
