package com.TravelBookingSystem_BE.Dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {

    @NotEmpty(message = "Customer_name is required")
    @Size(min = 2, max = 100)
    private String customerName;

    @NotNull(message = "price is required")
    private double price;

    @NotNull(message = "date is required")
    private LocalDate date;

    @NotEmpty(message = "location is required")
    @Size(min = 3, max = 255)
    private String location;

    @NotEmpty(message = "Type is required")
    @Pattern(regexp = "CabBooking|FlightBooking|HotelBooking", message = "Type must be CabBooking or FlightBooking or HotelBooking")
    private String type;

    @NotNull(message = "user_id can not be null")
    private Long userId;
}
