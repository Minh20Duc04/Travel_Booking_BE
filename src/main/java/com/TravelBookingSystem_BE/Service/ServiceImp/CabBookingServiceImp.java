package com.TravelBookingSystem_BE.Service.ServiceImp;

import com.TravelBookingSystem_BE.Convertor.BookingConvertor;
import com.TravelBookingSystem_BE.Convertor.CabBookingConvertor;
import com.TravelBookingSystem_BE.Dto.BookingDto;
import com.TravelBookingSystem_BE.Dto.CabBookingDto;
import com.TravelBookingSystem_BE.Exception.CabNotFoundException;
import com.TravelBookingSystem_BE.Exception.ResourceNotFoundException;
import com.TravelBookingSystem_BE.Model.Booking;
import com.TravelBookingSystem_BE.Model.Cab;
import com.TravelBookingSystem_BE.Model.CabBooking;
import com.TravelBookingSystem_BE.Repository.BookingRepository;
import com.TravelBookingSystem_BE.Repository.CabBookingRepository;
import com.TravelBookingSystem_BE.Repository.CabRepository;
import com.TravelBookingSystem_BE.Service.CabBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CabBookingServiceImp implements CabBookingService {

    @Autowired
    private CabBookingRepository cabBookingRepository;

    @Autowired
    private CabRepository cabRepository;

    @Autowired
    private CabBookingConvertor cabBookingConvertor;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingConvertor bookingConvertor;

    @Override
    public CabBooking bookCab(CabBookingDto bookCab) {

        Cab cab = cabRepository.findById(bookCab.getCabId())
                .orElseThrow(() -> new CabNotFoundException(bookCab.getCabId()));

        CabBooking cabBooking = cabBookingConvertor.CabBookingDtotoCabBooking(bookCab, cab);
        cabBooking.setPaymentStatus(true);
        cabBookingRepository.save(cabBooking);

        // Create BookingDto from CabBooking and save Booking
        BookingDto bookingDto = new BookingDto(
                cabBooking.getCustName(),
                cabBooking.getCab().getCabPrice(),
                cabBooking.getPickUpDate(),
                cabBooking.getPickUpLocation(),
                "CabBooking",
                cabBooking.getCab().getUser().getId()
        );

        Booking booking = bookingConvertor.convertToBooking(bookingDto, cabBooking.getCab().getUser());
        bookingRepository.save(booking);

        return cabBooking;
    }

    @Override
    public List<CabBookingDto> manageBookings() {
        List<CabBooking> bookingList = cabBookingRepository.findAll();
        return bookingList.stream()
                .map(cabBookingConvertor::CabBookingtoCabBookingDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CabBookingDto> findByCustEmail(String custEmail) {
        if (custEmail == null || custEmail.isEmpty()) {
            throw new IllegalArgumentException("Customer email must not be null or empty");
        }

        List<CabBooking> cabBookingDB = cabBookingRepository.findByCustEmail(custEmail);
        return cabBookingDB.stream()
                .map(cabBookingConvertor::CabBookingtoCabBookingDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CabBookingDto> findByCustPhone(Long custPhone) {
        if (custPhone == null) {
            throw new IllegalArgumentException("Customer phone must not be null");
        }

        List<CabBooking> cabBookingDB = cabBookingRepository.findByCustPhone(custPhone);
        return cabBookingDB.stream()
                .map(cabBookingConvertor::CabBookingtoCabBookingDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CabBookingDto> findByCabId(Long cabId) {
        if (cabId == null) {
            throw new IllegalArgumentException("Cab ID must not be null");
        }

        Cab cab = cabRepository.findById(cabId)
                .orElseThrow(() -> new CabNotFoundException(cabId));
        List<CabBooking> bookings = cab.getBookings();
        return bookings.stream()
                .map(cabBookingConvertor::CabBookingtoCabBookingDto)
                .collect(Collectors.toList());
    }

    @Override
    public void cancelBooking(Long bookingId) {
        CabBooking cabBooking = cabBookingRepository.findById(bookingId).orElseThrow(()->new ResourceNotFoundException("Booking not found with id: "+bookingId));
        cabBookingRepository.delete(cabBooking);
    }
}

