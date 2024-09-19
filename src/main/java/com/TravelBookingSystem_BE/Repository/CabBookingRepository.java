package com.TravelBookingSystem_BE.Repository;

import com.TravelBookingSystem_BE.Dto.CabBookingDto;
import com.TravelBookingSystem_BE.Model.CabBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CabBookingRepository extends JpaRepository<CabBooking, Long> {
    List<CabBooking> findByCustEmail(String custEmail);
    List<CabBooking> findByCustPhone(Long custPhone);

}
