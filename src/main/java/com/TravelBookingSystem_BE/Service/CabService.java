package com.TravelBookingSystem_BE.Service;

import com.TravelBookingSystem_BE.Model.Cab;

import java.time.LocalDate;
import java.util.List;

public interface CabService {
    Cab saveCab(Cab cab);

    List<Cab> searchCabs(String pickUpLocation, String dropLocation, LocalDate pickUpDate, LocalDate dropOffDate);

    Cab getCabById(Long cabId);

    String deleteCab(Long cabId);

    Cab updateCab(Cab cabRequest, Long cabId);

    List<Cab> findByCabTypeAndCabPrice(String cabType, double cabPrice);

}
