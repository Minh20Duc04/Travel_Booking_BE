package com.TravelBookingSystem_BE.Controller;

import com.TravelBookingSystem_BE.Dto.CabBookingDto;
import com.TravelBookingSystem_BE.Exception.CabNotFoundException;
import com.TravelBookingSystem_BE.Model.Cab;
import com.TravelBookingSystem_BE.Model.CabBooking;
import com.TravelBookingSystem_BE.Service.CabBookingService;
import com.TravelBookingSystem_BE.Service.CabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/cabs")
@CrossOrigin("https://8081-example")
public class CabController {

    @Autowired
    private CabService cabService;

    @Autowired
    private CabBookingService cabBookingService;

    @PostMapping("/create")
    public ResponseEntity<Cab> addCab(@RequestBody Cab cab) {
        try {
            Cab savedCab = cabService.saveCab(cab);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCab);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Cab>> searchCabs(
            @RequestParam(value = "pickUpLocation", required = false) String pickUpLocation,
            @RequestParam(value = "dropLocation", required = false) String dropLocation,
            @RequestParam(value = "pickUpDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate pickUpDate,
            @RequestParam(value = "dropOffDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dropOffDate) {
        List<Cab> cabs = cabService.searchCabs(pickUpLocation, dropLocation, pickUpDate, dropOffDate);
        return cabs.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(cabs);
    }

    @GetMapping("/{cabId}")
    public ResponseEntity<Cab> getCab(@PathVariable Long cabId) {
        try {
            Cab cab = cabService.getCabById(cabId);
            return ResponseEntity.ok(cab);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{cabId}/update")
    public ResponseEntity<Cab> updateCab(@RequestBody Cab cabRequest, @PathVariable Long cabId) {
        try {
            Cab updatedCab = cabService.updateCab(cabRequest, cabId);
            return ResponseEntity.ok(updatedCab);
        } catch (CabNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{cabId}/delete")
    public ResponseEntity<Void> deleteCab(@PathVariable Long cabId) {
        try {
            cabService.deleteCab(cabId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/bookings")
    public ResponseEntity<CabBooking> bookCab(@RequestBody CabBookingDto bookingDto) {
        try {
            CabBooking booking = cabBookingService.bookCab(bookingDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(booking);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/bookings/manage")
    public ResponseEntity<List<CabBookingDto>> getAllBookings() {
        List<CabBookingDto> bookings = cabBookingService.manageBookings();
        return bookings.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(bookings);
    }

    @GetMapping("/bookings/email/{custEmail}")
    public ResponseEntity<List<CabBookingDto>> getBookingsByCustomerEmail(@PathVariable String custEmail) {
        List<CabBookingDto> bookings = cabBookingService.findByCustEmail(custEmail);
        return bookings.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(bookings);
    }

    @GetMapping("/bookings/phone/{custPhone}")
    public ResponseEntity<List<CabBookingDto>> getBookingsByCustomerPhone(@PathVariable Long custPhone) {
        List<CabBookingDto> bookings = cabBookingService.findByCustPhone(custPhone);
        return bookings.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(bookings);
    }

    @GetMapping("/bookings/cabId/{cabId}")
    public ResponseEntity<List<CabBookingDto>> getBookingsByCabId(@PathVariable Long cabId) {
        List<CabBookingDto> bookings = cabBookingService.findByCabId(cabId);
        return bookings.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(bookings);
    }

    @GetMapping("/searchByTypeAndPrice")
    public ResponseEntity<List<Cab>> getCabsByTypeAndPrice(
            @RequestParam("cabType") String cabType, @RequestParam("cabPrice") double cabPrice) {
        List<Cab> cabs = cabService.findByCabTypeAndCabPrice(cabType, cabPrice);
        return cabs.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(cabs);
    }

    @DeleteMapping("/bookings/{bookingId}/cancel")
    public ResponseEntity<Void> cancelBooking(@PathVariable("bookingId") Long bookingId) {
        try {
            cabBookingService.cancelBooking(bookingId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}

