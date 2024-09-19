package com.TravelBookingSystem_BE.Controller;

import com.TravelBookingSystem_BE.Dto.HotelBookingDto;
import com.TravelBookingSystem_BE.Model.Hotel;
import com.TravelBookingSystem_BE.Model.HotelBooking;
import com.TravelBookingSystem_BE.Service.HotelBookingService;
import com.TravelBookingSystem_BE.Service.HotelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/hotels")
@CrossOrigin("https://8081-example")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private HotelBookingService hotelBookingService;

    @PostMapping("/create")
    public ResponseEntity<Hotel> addHotel(@RequestBody Hotel newHotel) {
        Hotel hotel = hotelService.addHotel(newHotel);
        return ResponseEntity.status(HttpStatus.CREATED).body(hotel);
    }

    @GetMapping
    public ResponseEntity<List<Hotel>> getAllHotels() {
        List<Hotel> hotels = hotelService.getAllHotels();
        return ResponseEntity.ok(hotels);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable("hotelId") Long hotelId) {
        Hotel hotel = hotelService.getHotelById(hotelId);
        return hotel != null ? ResponseEntity.ok(hotel) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/{hotelId}/update")
    public ResponseEntity<Hotel> updateHotel(@PathVariable("hotelId") Long hotelId, @RequestBody Hotel hotelRequest) {
        Hotel updatedHotel = hotelService.updateById(hotelId, hotelRequest);
        return ResponseEntity.ok(updatedHotel);
    }

    @DeleteMapping("/{hotelId}/delete")
    public ResponseEntity<String> deleteHotel(@PathVariable("hotelId") Long hotelId) {
        String response = hotelService.deleteById(hotelId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Hotel>> searchHotels(
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "checkInDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam(value = "checkOutDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate) {
        List<Hotel> hotels = hotelService.searchHotels(location, checkInDate, checkOutDate);
        return ResponseEntity.ok(hotels);
    }

    @GetMapping("/searchByLocationAndRoomType")
    public ResponseEntity<List<Hotel>> findHotelsByLocationAndRoomType(@RequestParam String location, @RequestParam String roomType) {
        List<Hotel> hotels = hotelService.findHotelsByLocationAndRoomType(location, roomType);
        return ResponseEntity.ok(hotels);
    }

    @PostMapping("/bookings")
    public ResponseEntity<HotelBooking> bookHotel(@Valid @RequestBody HotelBookingDto hotelBookingDto) {
        HotelBooking booking = hotelBookingService.bookHotel(hotelBookingDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }

    @GetMapping("/bookings/manage")
    public ResponseEntity<List<HotelBookingDto>> getAllHotelBookings() {
        List<HotelBookingDto> bookings = hotelBookingService.manageBookings();
        return bookings.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(bookings);
    }

    @GetMapping("/bookings/email")
    public ResponseEntity<List<HotelBookingDto>> findBookingsByCustomerEmail(@RequestParam String email) {
        List<HotelBookingDto> bookings = hotelBookingService.findBookingsByCustomerEmail(email);
        return bookings.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok(bookings);
    }

    @GetMapping("/bookings/phone")
    public ResponseEntity<List<HotelBookingDto>> findBookingsByCustomerPhone(@RequestParam Long custPhone) {
        List<HotelBookingDto> bookings = hotelBookingService.findBookingsByCustPhone(custPhone);
        return bookings.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok(bookings);
    }

    @GetMapping("/bookings/hotel/{hotelId}")
    public ResponseEntity<List<HotelBookingDto>> getBookingsByHotelId(@PathVariable("hotelId") Long hotelId) {
        List<HotelBookingDto> bookings = hotelBookingService.findByHotelId(hotelId);
        return bookings.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok(bookings);
    }

    @DeleteMapping("/bookings/{bookingId}/cancel")
    public ResponseEntity<Void> cancelBooking(@PathVariable("bookingId") Long bookingId) {
        try {
            hotelBookingService.cancelBooking(bookingId);
            return ResponseEntity.noContent().build();
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

