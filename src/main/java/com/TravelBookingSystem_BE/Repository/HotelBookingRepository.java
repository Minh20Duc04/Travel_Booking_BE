package com.TravelBookingSystem_BE.Repository;

import com.TravelBookingSystem_BE.Model.HotelBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelBookingRepository extends JpaRepository<HotelBooking, Long> {
    List<HotelBooking> findByCustPhone(Long custPhone);
    List<HotelBooking> findByCustEmail(String custEmail);
}
