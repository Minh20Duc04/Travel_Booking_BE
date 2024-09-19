package com.TravelBookingSystem_BE.Service;

import com.TravelBookingSystem_BE.Model.Flight;

import java.time.LocalDate;
import java.util.List;

public interface FlightService {
    List<Flight> fetchFlight();

    Flight getFlightById(Long flightNumber);

    String deleteFlightById(Long flightNumber);

    Flight updateFlight(Long flightNumber, Flight flight);


    Flight saveFlight(Flight flight);

    List<Flight> fetchFlightSeat();

    List<Flight> searchFlights(String fromLocation, String dropLocation, LocalDate departureDate);

    List<Flight> findByAirLineAndPrice(String airline, double price);
}
