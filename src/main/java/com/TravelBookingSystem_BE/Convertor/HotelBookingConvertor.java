package com.TravelBookingSystem_BE.Convertor;

import com.TravelBookingSystem_BE.Dto.HotelBookingDto;
import com.TravelBookingSystem_BE.Exception.HotelNotFoundException;
import com.TravelBookingSystem_BE.Model.Hotel;
import com.TravelBookingSystem_BE.Model.HotelBooking;
import com.TravelBookingSystem_BE.Repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HotelBookingConvertor {

    @Autowired
    private HotelRepository hotelRepository;

    public HotelBooking convertHotelBookingDtoToHotelBooking(HotelBookingDto hotelBookingDto, Hotel hotel) {
        return HotelBooking.builder()
                .hotelBookingId(hotelBookingDto.getHotelId())
                .hotel(hotel)  // Thiết lập khách sạn
                .custName(hotelBookingDto.getCustName())
                .custGender(hotelBookingDto.getCustGender())
                .custEmail(hotelBookingDto.getCustEmail())
                .custPhone(hotelBookingDto.getCustPhone())
                .custAddress(hotelBookingDto.getCustAddress())
                .checkInDate(hotelBookingDto.getCheckInDate())
                .checkOutDate(hotelBookingDto.getCheckOutDate())
                .build();
    }

    public HotelBookingDto convertHotelBookingToHotelBookingDto(HotelBooking hotelBooking) {
        return new HotelBookingDto(
                hotelBooking.getHotel().getHotelId(),  // Lấy hotelId từ đối tượng Hotel
                hotelBooking.getCustName(),
                hotelBooking.getCustGender(),
                hotelBooking.getCustEmail(),
                hotelBooking.getCustPhone(),
                hotelBooking.getCustAddress(),
                hotelBooking.getCheckInDate(),
                hotelBooking.getCheckOutDate()
        );
    }
}
