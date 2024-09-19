package com.TravelBookingSystem_BE.Controller;

import com.TravelBookingSystem_BE.Dto.FlightBookingDto;
import com.TravelBookingSystem_BE.Exception.FlightNotFoundException;
import com.TravelBookingSystem_BE.Model.Flight;
import com.TravelBookingSystem_BE.Model.FlightBooking;
import com.TravelBookingSystem_BE.Service.FlightBookingService;
import com.TravelBookingSystem_BE.Service.FlightService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/flights")
@CrossOrigin("https://8081-example")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @Autowired
    private FlightBookingService flightBookingService;

    @GetMapping
    public ResponseEntity<List<Flight>> getAllFlights() {
        List<Flight> flights = flightService.fetchFlight();
        return flights.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(flights);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable("id") Long flightId) {
        try {
            Flight flight = flightService.getFlightById(flightId);
            return ResponseEntity.ok(flight);
        } catch (FlightNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight) {
        try {
            Flight createdFlight = flightService.saveFlight(flight);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdFlight);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Flight> updateFlight(@PathVariable("id") Long flightId, @RequestBody Flight flight) {
        try {
            Flight updatedFlight = flightService.updateFlight(flightId, flight);
            return ResponseEntity.ok(updatedFlight);
        } catch (FlightNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteFlight(@PathVariable("id") Long flightId) {
        try {
            flightService.deleteFlightById(flightId);
            return ResponseEntity.noContent().build();
        } catch (FlightNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/seats/confirmed")
    public ResponseEntity<List<Flight>> getConfirmedFlights() {
        List<Flight> flights = flightService.fetchFlightSeat();
        return flights.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(flights);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Flight>> searchFlights(
            @RequestParam(value = "fromLocation", required = false) String fromLocation,
            @RequestParam(value = "dropLocation", required = false) String dropLocation,
            @RequestParam(value = "departureDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate) {
        List<Flight> flights = flightService.searchFlights(fromLocation, dropLocation, departureDate);
        return flights.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(flights);
    }

    @GetMapping("/searchByAirlineAndPrice")
    public ResponseEntity<List<Flight>> getFlightsByAirlineAndPrice(
            @RequestParam("airline") String airline,
            @RequestParam("price") double price) {
        List<Flight> flights = flightService.findByAirLineAndPrice(airline, price);
        return flights.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(flights);
    }

    @PostMapping("/bookings")
    public ResponseEntity<FlightBooking> bookFlight(@Valid @RequestBody FlightBookingDto flightBookingDto) {
        try {
            FlightBooking booking = flightBookingService.bookFlight(flightBookingDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(booking);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/bookings/manage")
    public ResponseEntity<List<FlightBookingDto>> getAllFlightBookings() {
        List<FlightBookingDto> bookings = flightBookingService.manageBookings();
        return bookings.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(bookings);
    }

    @GetMapping("/bookings/phone/{custPhone}")
    public ResponseEntity<List<FlightBookingDto>> getBookingsByCustomerPhone(@PathVariable("custPhone") Long custPhone) {
        List<FlightBookingDto> bookings = flightBookingService.findByCustPhone(custPhone);
        return bookings.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(bookings);
    }

    @GetMapping("/bookings/email/{custEmail}")
    public ResponseEntity<List<FlightBookingDto>> getBookingsByCustomerEmail(@PathVariable("custEmail") String custEmail) {
        List<FlightBookingDto> bookings = flightBookingService.findByCustEmail(custEmail);
        return bookings.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(bookings);
    }

    @GetMapping("/bookings/flightNumber/{flightNumber}")
    public ResponseEntity<List<FlightBookingDto>> getBookingsByFlightNumber(@PathVariable("flightNumber") Long flightNumber) {
        List<FlightBookingDto> bookings = flightBookingService.findByFlightNumber(flightNumber);
        return bookings.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(bookings);
    }

    @DeleteMapping("/bookings/{bookingId}/cancel")
    public ResponseEntity<Void> cancelBooking(@PathVariable("bookingId") Long bookingId) {
        try {
            flightBookingService.cancelBooking(bookingId);
            return ResponseEntity.noContent().build();
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}

