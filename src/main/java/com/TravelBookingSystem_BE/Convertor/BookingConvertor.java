package com.TravelBookingSystem_BE.Convertor;

import com.TravelBookingSystem_BE.Dto.BookingDto;
import com.TravelBookingSystem_BE.Model.Booking;
import com.TravelBookingSystem_BE.Model.User;
import org.springframework.stereotype.Component;

@Component
public class BookingConvertor {

    public Booking convertToBooking(BookingDto bookingDto, User user)
    {
        Booking booking = Booking.builder()
                .customerName(bookingDto.getCustomerName())
                .date(bookingDto.getDate())
                .user(user)
                .type(bookingDto.getType())
                .price(bookingDto.getPrice())
                .location(bookingDto.getLocation())
                .build();
        return booking;
    }

    public BookingDto convertToBookingDto(Booking booking)
    {
        return new BookingDto(
                booking.getCustomerName(),
                booking.getPrice(),
                booking.getDate(),
                booking.getLocation(),
                booking.getType(),
                booking.getUser().getId()
        );
    }







}
