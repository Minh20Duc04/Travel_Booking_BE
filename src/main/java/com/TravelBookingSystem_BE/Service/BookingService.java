package com.TravelBookingSystem_BE.Service;

import com.TravelBookingSystem_BE.Dto.BookingDto;
import com.TravelBookingSystem_BE.Model.Booking;

import java.util.List;

public interface BookingService {
     Booking addBooking(Long userId, BookingDto bookingDto);

     List<BookingDto> getBookingByUserId(Long userId);

     String deleteBooking(Long userId, Long id);
}
