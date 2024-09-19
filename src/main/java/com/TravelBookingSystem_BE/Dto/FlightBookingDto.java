package com.TravelBookingSystem_BE.Dto;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class FlightBookingDto {

    @NotNull(message = "Flight_Id can not be null")
    private Long flightId;

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

    @NotNull(message = "Departure date is required")
    @FutureOrPresent(message = "Departure date must be today or in the future")
    private LocalDate departureDate;

    @NotEmpty(message = "fromLocation is required")
    @Size(min = 3, max = 255)
    private String fromLocation;

    @NotEmpty(message = "toLocation is required")
    @Size(min = 3, max = 255)
    private String toLocation;

    @NotNull(message = "totalCost is required")
    private double totalCost;

    @NotEmpty(message = "selectedSeats are required")
    @Size(min = 3, max = 10) //Economy và Business
    private String selectedSeats;

}
