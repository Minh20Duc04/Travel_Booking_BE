package com.TravelBookingSystem_BE.Controller;

import com.TravelBookingSystem_BE.Dto.BookingDto;
import com.TravelBookingSystem_BE.Exception.ResourceNotFoundException;
import com.TravelBookingSystem_BE.Model.Booking;
import com.TravelBookingSystem_BE.Service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin("https://8081-example")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/users/{userId}")
    public ResponseEntity<Booking> createBooking(@PathVariable("userId") Long userId, @Valid @RequestBody BookingDto bookingDto) {
        Booking booking = bookingService.addBooking(userId, bookingDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<BookingDto>> getBookingsByUser(@PathVariable("userId") Long userId) {
        List<BookingDto> bookings = bookingService.getBookingByUserId(userId);
        return ResponseEntity.ok(bookings);
    }

    @DeleteMapping("/users/{userId}/bookings/{bookingId}")
    public ResponseEntity<Void> deleteBooking(@PathVariable("userId") Long userId, @PathVariable("bookingId") Long bookingId) {
        try {
            bookingService.deleteBooking(userId, bookingId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

