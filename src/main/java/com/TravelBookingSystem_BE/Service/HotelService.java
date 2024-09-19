package com.TravelBookingSystem_BE.Service;

import com.TravelBookingSystem_BE.Model.Hotel;
import com.TravelBookingSystem_BE.Model.HotelBooking;

import java.time.LocalDate;
import java.util.List;

public interface HotelService {
    Hotel addHotel(Hotel newHotel);

    List<Hotel> getAllHotels();

    Hotel getHotelById(Long hotelId);

    Hotel updateById(Long hotelId, Hotel hotelRequest);

    String deleteById(Long hotelId);

    List<Hotel> searchHotels(String location, LocalDate checkInDate, LocalDate checkOutDate);

    List<Hotel> findHotelsByLocationAndRoomType(String location, String roomType);

}
