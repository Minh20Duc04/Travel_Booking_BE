package com.TravelBookingSystem_BE.Service.ServiceImp;

import com.TravelBookingSystem_BE.Exception.CabNotFoundException;
import com.TravelBookingSystem_BE.Model.Cab;
import com.TravelBookingSystem_BE.Repository.CabRepository;
import com.TravelBookingSystem_BE.Service.CabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CabServiceImp implements CabService {

    @Autowired
    private CabRepository cabRepository;

    @Override
    public Cab saveCab(Cab cab) {
        validate(cab); // Validate before saving
        return cabRepository.save(cab);
    }

    @Override
    public List<Cab> searchCabs(String pickUpLocation, String dropLocation, LocalDate pickUpDate, LocalDate dropOffDate) {
        if (pickUpLocation == null && dropLocation == null && pickUpDate == null && dropOffDate == null) {
            return cabRepository.findAll().stream()
                    .filter(cab -> cab.getPickUpDate().isAfter(LocalDate.now())) // Filter cabs with future pick-up dates
                    .toList();
        }
        return cabRepository.searchCabs(pickUpLocation, dropLocation, pickUpDate, dropOffDate);
    }

    @Override
    public Cab getCabById(Long cabId) {
        return cabRepository.findById(cabId)
                .orElseThrow(() -> new CabNotFoundException(cabId));
    }

    @Override
    public String deleteCab(Long cabId) {
        Cab cabDB = getCabById(cabId);
        cabRepository.delete(cabDB);
        return "Cab with ID: " + cabId + " has been deleted successfully!";
    }

    @Override
    public Cab updateCab(Cab cabRequest, Long cabId) {
        Cab cabDB = getCabById(cabId);
        validate(cabRequest);

        // Update fields
        cabDB.setCabType(cabRequest.getCabType());
        cabDB.setCabPrice(cabRequest.getCabPrice());
        cabDB.setPickUpLocation(cabRequest.getPickUpLocation());
        cabDB.setDropLocation(cabRequest.getDropLocation());
        cabDB.setPickUpDate(cabRequest.getPickUpDate());
        cabDB.setDropOffDate(cabRequest.getDropOffDate());

        return cabRepository.save(cabDB);
    }

    @Override
    public List<Cab> findByCabTypeAndCabPrice(String cabType, double cabPrice) {
        return cabRepository.findByCabTypeAndCabPrice(cabType, cabPrice);
    }

    private void validate(Cab cab) {
        if (cab.getCabId() != null && cabRepository.findById(cab.getCabId()).isPresent()) {
            throw new IllegalArgumentException("Cab with ID " + cab.getCabId() + " already exists");
        }
        if (cab.getCabType() == null || cab.getCabType().isEmpty()) {
            throw new IllegalArgumentException("Cab type must not be null or empty");
        }
        if (cab.getCabPrice() <= 0) {
            throw new IllegalArgumentException("Cab price must be greater than 0");
        }
        if (cab.getPickUpLocation() == null || cab.getPickUpLocation().isEmpty()) {
            throw new IllegalArgumentException("Pick-up location must not be null or empty");
        }
        if (cab.getDropLocation() == null || cab.getDropLocation().isEmpty()) {
            throw new IllegalArgumentException("Drop-off location must not be null or empty");
        }
        if (cab.getPickUpDate() == null || cab.getDropOffDate() == null) {
            throw new IllegalArgumentException("Pick-up and drop-off dates must not be null");
        }
        if (cab.getDropOffDate().isBefore(cab.getPickUpDate())) {
            throw new IllegalArgumentException("Drop-off date must be after pick-up date");
        }
    }
}

