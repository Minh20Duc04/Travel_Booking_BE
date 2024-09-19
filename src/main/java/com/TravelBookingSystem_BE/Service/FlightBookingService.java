package com.TravelBookingSystem_BE.Service;

import com.TravelBookingSystem_BE.Dto.FlightBookingDto;
import com.TravelBookingSystem_BE.Model.FlightBooking;

import java.util.List;

public interface FlightBookingService {
    FlightBooking bookFlight(FlightBookingDto flightBookingDto);

    List<FlightBookingDto> manageBookings();

    List<FlightBookingDto> findByCustPhone(Long custPhone);

    List<FlightBookingDto> findByCustEmail(String custEmail);

    List<FlightBookingDto> findByFlightNumber(Long flightNumber);

    void cancelBooking(Long bookingId);
}
