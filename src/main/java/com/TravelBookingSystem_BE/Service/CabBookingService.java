package com.TravelBookingSystem_BE.Service;

import com.TravelBookingSystem_BE.Dto.CabBookingDto;
import com.TravelBookingSystem_BE.Model.CabBooking;

import java.util.List;

public interface CabBookingService {

    CabBooking bookCab(CabBookingDto bookCab);

    List<CabBookingDto> manageBookings();  //(Long customerId)

    List<CabBookingDto> findByCustEmail(String custEmail);

    List<CabBookingDto> findByCustPhone(Long custPhone);

    List<CabBookingDto> findByCabId(Long cabId);

    void cancelBooking(Long bookingId);
}
