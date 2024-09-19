package com.TravelBookingSystem_BE.Exception;

public class FlightNotFoundException extends RuntimeException{

    public FlightNotFoundException(Long flightNumber)
    {
        super("Could not found the flight with id" + flightNumber);
    }
}
