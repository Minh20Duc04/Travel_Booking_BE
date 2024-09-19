package com.TravelBookingSystem_BE.Service.ServiceImp;

import com.TravelBookingSystem_BE.Convertor.BookingConvertor;
import com.TravelBookingSystem_BE.Convertor.FlightBookingConvertor;
import com.TravelBookingSystem_BE.Dto.BookingDto;
import com.TravelBookingSystem_BE.Dto.FlightBookingDto;
import com.TravelBookingSystem_BE.Exception.FlightNotFoundException;
import com.TravelBookingSystem_BE.Exception.HotelNotFoundException;
import com.TravelBookingSystem_BE.Exception.ResourceNotFoundException;
import com.TravelBookingSystem_BE.Model.Booking;
import com.TravelBookingSystem_BE.Model.Flight;
import com.TravelBookingSystem_BE.Model.FlightBooking;
import com.TravelBookingSystem_BE.Repository.BookingRepository;
import com.TravelBookingSystem_BE.Repository.FlightBookingRepository;
import com.TravelBookingSystem_BE.Repository.FlightRepository;
import com.TravelBookingSystem_BE.Service.FlightBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FlightBookingServiceImp implements FlightBookingService {

    @Autowired
    private FlightBookingRepository flightBookingRepository;

    @Autowired
    private FlightBookingConvertor flightBookingConvertor;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingConvertor bookingConvertor;

    @Override
    public FlightBooking bookFlight(FlightBookingDto flightBookingDto) {
        Flight flight = flightRepository.findById(flightBookingDto.getFlightId())
                .orElseThrow(() -> new FlightNotFoundException(flightBookingDto.getFlightId()));

        FlightBooking flightBooking = flightBookingConvertor.convertFlightBookingDtoToFlightBooking(flightBookingDto, flight);
        flightBooking.setPaymentStatus(true);
        flightBookingRepository.save(flightBooking);

        // Create BookingDto from FlightBooking and save Booking
        BookingDto bookingDto = new BookingDto(
                flightBooking.getCustName(),
                flightBooking.getFlight().getPrice(),
                flightBooking.getDepartureDate(),
                flightBooking.getFlight().getDropLocation(),
                "FlightBooking",
                flightBooking.getFlight().getUser().getId()
        );

        Booking booking = bookingConvertor.convertToBooking(bookingDto, flightBooking.getFlight().getUser());
        bookingRepository.save(booking);

        return flightBooking;
    }

    @Override
    public List<FlightBookingDto> manageBookings() {
        List<FlightBooking> bookingList = flightBookingRepository.findAll();
        return bookingList.stream()
                .map(flightBookingConvertor::convertFlightBookingToFlightBookingDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<FlightBookingDto> findByCustEmail(String custEmail) {
        if (custEmail == null || custEmail.isEmpty()) {
            throw new IllegalArgumentException("Customer email must not be null or empty");
        }

        List<FlightBooking> flightBookings = flightBookingRepository.findByCustEmail(custEmail);
        return flightBookings.stream()
                .map(flightBookingConvertor::convertFlightBookingToFlightBookingDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<FlightBookingDto> findByCustPhone(Long custPhone) {
        if (custPhone == null) {
            throw new IllegalArgumentException("Customer phone must not be null");
        }

        List<FlightBooking> flightBookings = flightBookingRepository.findByCustPhone(custPhone);
        return flightBookings.stream()
                .map(flightBookingConvertor::convertFlightBookingToFlightBookingDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<FlightBookingDto> findByFlightNumber(Long flightNumber) {
        if (flightNumber == null) {
            throw new IllegalArgumentException("Flight number must not be null");
        }

        Flight flight = flightRepository.findById(flightNumber)
                .orElseThrow(() -> new FlightNotFoundException(flightNumber));

        List<FlightBooking> flightBookings = flight.getBookings();
        return flightBookings.stream()
                .map(flightBookingConvertor::convertFlightBookingToFlightBookingDto)
                .collect(Collectors.toList());
    }

    @Override
    public void cancelBooking(Long bookingId) {
        FlightBooking flightBooking = flightBookingRepository.findById(bookingId).orElseThrow(()->new ResourceNotFoundException("Booking not found with id:" + bookingId));
        flightBookingRepository.delete(flightBooking);
    }
}

