package com.TravelBookingSystem_BE.Repository;

import com.TravelBookingSystem_BE.Model.FlightBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightBookingRepository extends JpaRepository<FlightBooking, Long> {

    List<FlightBooking> findByCustPhone(Long custPhone);
    List<FlightBooking> findByCustEmail(String custEmail);

}
