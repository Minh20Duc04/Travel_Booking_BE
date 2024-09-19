package com.TravelBookingSystem_BE.Convertor;

import com.TravelBookingSystem_BE.Dto.FlightBookingDto;
import com.TravelBookingSystem_BE.Model.Flight;
import com.TravelBookingSystem_BE.Model.FlightBooking;
import org.springframework.stereotype.Component;

@Component
public class FlightBookingConvertor {

    public FlightBooking convertFlightBookingDtoToFlightBooking(FlightBookingDto flightBookingDto, Flight flight)
    {
        return FlightBooking.builder()
                .custName(flightBookingDto.getCustName())
                .custGender(flightBookingDto.getCustGender())
                .custEmail(flightBookingDto.getCustEmail())
                .custPhone(flightBookingDto.getCustPhone())
                .custAddress(flightBookingDto.getCustAddress())
                .departureDate(flightBookingDto.getDepartureDate())
                .fromLocation(flightBookingDto.getFromLocation())
                .toLocation(flightBookingDto.getToLocation())
                .totalCost(flightBookingDto.getTotalCost())
                .selectedSeats(flightBookingDto.getSelectedSeats())
                .flight(flight)
                .build();
    }

    public FlightBookingDto convertFlightBookingToFlightBookingDto(FlightBooking flightBooking) {
        return new FlightBookingDto(
                flightBooking.getFlight().getFlightId(),
                flightBooking.getCustName(),
                flightBooking.getCustGender(),
                flightBooking.getCustEmail(),
                flightBooking.getCustPhone(),
                flightBooking.getCustAddress(),
                flightBooking.getDepartureDate(),
                flightBooking.getFromLocation(),
                flightBooking.getToLocation(),
                flightBooking.getTotalCost(),
                flightBooking.getSelectedSeats()
        );
    }

}
