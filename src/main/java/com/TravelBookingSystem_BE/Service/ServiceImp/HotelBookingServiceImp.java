package com.TravelBookingSystem_BE.Service.ServiceImp;

import com.TravelBookingSystem_BE.Convertor.BookingConvertor;
import com.TravelBookingSystem_BE.Convertor.HotelBookingConvertor;
import com.TravelBookingSystem_BE.Dto.BookingDto;
import com.TravelBookingSystem_BE.Dto.HotelBookingDto;
import com.TravelBookingSystem_BE.Exception.HotelNotFoundException;
import com.TravelBookingSystem_BE.Exception.ResourceNotFoundException;
import com.TravelBookingSystem_BE.Model.Booking;
import com.TravelBookingSystem_BE.Model.Hotel;
import com.TravelBookingSystem_BE.Model.HotelBooking;
import com.TravelBookingSystem_BE.Repository.BookingRepository;
import com.TravelBookingSystem_BE.Repository.HotelBookingRepository;
import com.TravelBookingSystem_BE.Repository.HotelRepository;
import com.TravelBookingSystem_BE.Service.HotelBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class HotelBookingServiceImp implements HotelBookingService {

    @Autowired
    private HotelBookingRepository hotelBookingRepository;

    @Autowired
    private HotelBookingConvertor hotelBookingConvertor;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingConvertor bookingConvertor;


    @Override
    public HotelBooking bookHotel(HotelBookingDto hotelBookingDto) {
        Hotel hotel = hotelRepository.findById(hotelBookingDto.getHotelId()).orElseThrow(() -> new HotelNotFoundException("Hotel not found with ID: " + hotelBookingDto.getHotelId()));
        HotelBooking hotelBooking = hotelBookingConvertor.convertHotelBookingDtoToHotelBooking(hotelBookingDto, hotel);
        hotelBooking.setTotalCost(calculateTotalCost(hotelBooking.getHotel(), hotelBooking));
        hotelBooking.setPaymentStatus(true);
        hotelBookingRepository.save(hotelBooking);

        BookingDto bookingDto = new BookingDto(
                hotelBooking.getCustName(),
                hotelBooking.getTotalCost(),
                hotelBooking.getCheckInDate(),
                hotelBooking.getHotel().getLocation(),
                "HotelBooking",
                hotelBooking.getHotel().getUser().getId()
        );

        Booking booking = bookingConvertor.convertToBooking(bookingDto, hotelBooking.getHotel().getUser());
        bookingRepository.save(booking);
        return hotelBooking;
    }

    @Override
    public List<HotelBookingDto> manageBookings() {
        List<HotelBooking> hotelBookings = hotelBookingRepository.findAll();
        return hotelBookings.stream()
                .map(hotelBookingConvertor::convertHotelBookingToHotelBookingDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<HotelBookingDto> findBookingsByCustomerEmail(String email) {
        List<HotelBooking> hotelBookingDB = hotelBookingRepository.findByCustEmail(email);
        return hotelBookingDB.stream()
                .map(hotelBookingConvertor::convertHotelBookingToHotelBookingDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<HotelBookingDto> findBookingsByCustPhone(Long custPhone) {
        List<HotelBooking> hotelBookingDB = hotelBookingRepository.findByCustPhone(custPhone);
        return hotelBookingDB.stream()
                .map(hotelBookingConvertor::convertHotelBookingToHotelBookingDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<HotelBookingDto> findByHotelId(Long hotelId) {
        if (hotelId == null) {
            throw new IllegalArgumentException("Hotel ID must not be null");
        }
        Hotel hotelDB = hotelRepository.findById(hotelId).orElseThrow(()->new HotelNotFoundException("Hotel with id: " + hotelId + " could not be found"));
        List<HotelBooking> bookings = hotelDB.getBookings();
        return bookings.stream()
                .map(hotelBookingConvertor::convertHotelBookingToHotelBookingDto)
                .collect(Collectors.toList());
    }

    @Override
    public void cancelBooking(Long bookingId) {
        HotelBooking hotelBooking = hotelBookingRepository.findById(bookingId).orElseThrow(()-> new ResourceNotFoundException("Booking not found with Id: "+bookingId));
        hotelBookingRepository.delete(hotelBooking);
    }


    private double calculateTotalCost(Hotel hotel, HotelBooking hotelBooking) {
        long dayStay = ChronoUnit.DAYS.between(hotelBooking.getCheckInDate(), hotelBooking.getCheckOutDate());
        if(dayStay <= 0)
        {
            throw new IllegalArgumentException("Check-out date must be after check-in date.");
        }
        double price = hotel.getPrice();
        return dayStay * price;
    }





}
