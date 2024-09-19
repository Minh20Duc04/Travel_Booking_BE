package com.TravelBookingSystem_BE.Repository;

import com.TravelBookingSystem_BE.Model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("SELECT h FROM Flight h WHERE "
            + "(:fromLocation IS NULL OR h.fromLocation = :fromLocation) "
            + "AND (:dropLocation IS NULL OR h.dropLocation = :dropLocation) "
            + "AND (:departureDate IS NULL OR h.departureDate = :departureDate)")
    List<Flight> searchFlights(@Param("fromLocation") String fromLocation,
                               @Param("dropLocation") String dropLocation,
                               @Param("departureDate") LocalDate departureDate);

    List<Flight> findByAirlineAndPrice(String airline, double price);
}
