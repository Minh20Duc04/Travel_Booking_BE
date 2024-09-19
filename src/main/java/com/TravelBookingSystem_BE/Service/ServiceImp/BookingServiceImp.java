package com.TravelBookingSystem_BE.Service.ServiceImp;

import com.TravelBookingSystem_BE.Convertor.BookingConvertor;
import com.TravelBookingSystem_BE.Dto.BookingDto;
import com.TravelBookingSystem_BE.Exception.ResourceNotFoundException;
import com.TravelBookingSystem_BE.Model.Booking;
import com.TravelBookingSystem_BE.Model.User;
import com.TravelBookingSystem_BE.Repository.BookingRepository;
import com.TravelBookingSystem_BE.Repository.UserRepository;
import com.TravelBookingSystem_BE.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImp implements BookingService {

    @Autowired
    private BookingConvertor bookingConvertor;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Booking addBooking(Long userId, BookingDto bookingDto) {
        if (bookingDto == null) {
            throw new IllegalArgumentException("BookingDto must not be null");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        Booking booking = bookingConvertor.convertToBooking(bookingDto, user);
        return bookingRepository.save(booking);
    }

    @Override
    public List<BookingDto> getBookingByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
        List<Booking> bookingList = bookingRepository.findByUserId(userId);
        return bookingList.stream()
                .map(bookingConvertor::convertToBookingDto)
                .collect(Collectors.toList());
    }

    @Override
    public String deleteBooking(Long userId, Long id) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + id));

        bookingRepository.delete(booking);
        return "Booking with ID: " + id + " has been deleted successfully!";
    }
}

