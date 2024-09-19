package com.TravelBookingSystem_BE.Dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class CabBookingDto {

    @NotNull(message = "cabId must not be null")
    private Long cabId;

    @NotEmpty(message = "Customer_name is required")
    @Size(min = 2, max = 100)
    private String custName;

    @NotNull(message = "dropDate is required")
    @FutureOrPresent(message = "dropDate must be today or in the future")
    private LocalDate dropDate;

    @NotEmpty(message = "Customer_Gender is required")
    @Pattern(regexp = "Male|Female|Other", message = "Gender must be Male, Female, or Other")
    private String custGender;

    @NotEmpty(message = "Customer_Email is required")
    @Email(message = "Email should be valid")
    private String custEmail;

    @NotNull
    private Long custPhone;

    @NotEmpty(message = "Customer_address is required")
    @Size(min = 5, max = 255)
    private String custAddress;

    @NotNull(message = "pickUp_Date is required")
    @FutureOrPresent(message = "pickUp_Date must be today or in the future")
    private LocalDate pickUpDate;

    @NotEmpty(message = "pickUpLocation is required")
    @Size(min = 3, max = 255)
    private String pickUpLocation;

    @NotEmpty(message = "dropLocation is required")
    @Size(min = 3, max = 255)
    private String dropLocation;

    @NotNull(message = "totalCost is required")
    private double totalCost;

}
