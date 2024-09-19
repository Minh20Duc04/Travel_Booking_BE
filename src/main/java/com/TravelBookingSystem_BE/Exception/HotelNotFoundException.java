package com.TravelBookingSystem_BE.Exception;

public class HotelNotFoundException extends RuntimeException{
    public HotelNotFoundException(String msg)
    {
        super(msg);
    }
}
