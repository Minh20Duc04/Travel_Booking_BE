package com.TravelBookingSystem_BE.Dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelBookingDto {

    @NotNull(message = "Hotel_Id can not be null")
    private Long hotelId;

    @NotEmpty(message = "Customer_Name is required")
    @Size(min = 2, max = 100)
    private String custName;

    @NotEmpty(message = "Customer_Gender is required")
    @Pattern(regexp = "Male|Female|Other", message = "Gender must be Male, Female, or Other")
    private String custGender;

    @NotEmpty(message = "Customer_email is required")
    @Email(message = "Email should be valid")
    private String custEmail;

    @NotNull
    private Long custPhone;

    @NotEmpty(message = "Customer_address is required")
    @Size(min = 5, max = 255)
    private String custAddress;


    @NotNull(message = "Check_in date can not be null")
    @FutureOrPresent(message = "Check_in date must be today or in the future")
    private LocalDate checkInDate;

    @NotNull(message = "Check-out date is required")
    @FutureOrPresent(message = "Check_out date must be today or in the future")
    private LocalDate checkOutDate;


}
