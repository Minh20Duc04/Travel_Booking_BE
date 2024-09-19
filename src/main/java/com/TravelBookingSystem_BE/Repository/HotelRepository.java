package com.TravelBookingSystem_BE.Repository;

import com.TravelBookingSystem_BE.Model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    @Query("SELECT h FROM Hotel h WHERE " +
            "(:location IS NULL OR h.location = :location) " +
            "AND ((:checkInDate IS NULL AND :checkOutDate IS NULL) " +
            "OR (h.checkOutDate >= :checkInDate AND h.checkInDate <= :checkOutDate))")
    List<Hotel> searchHotels(@Param("location") String location,
                             @Param("checkInDate") LocalDate checkInDate,
                             @Param("checkOutDate") LocalDate checkOutDate);

    List<Hotel> findByLocationAndRoomType(String location, String roomType);


}
