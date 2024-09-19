package com.TravelBookingSystem_BE.Service;

import com.TravelBookingSystem_BE.Dto.HotelBookingDto;
import com.TravelBookingSystem_BE.Model.HotelBooking;

import java.util.List;

public interface HotelBookingService {
    HotelBooking bookHotel(HotelBookingDto hotelBookingDto);

    List<HotelBookingDto> manageBookings();

    List<HotelBookingDto> findBookingsByCustomerEmail(String email);

    List<HotelBookingDto> findBookingsByCustPhone(Long custPhone);

    List<HotelBookingDto> findByHotelId(Long hotelId);

    void cancelBooking(Long bookingId);
}
