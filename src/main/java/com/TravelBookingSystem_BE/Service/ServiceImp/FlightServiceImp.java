package com.TravelBookingSystem_BE.Service.ServiceImp;

import com.TravelBookingSystem_BE.Exception.FlightNotFoundException;
import com.TravelBookingSystem_BE.Model.Flight;
import com.TravelBookingSystem_BE.Repository.FlightRepository;
import com.TravelBookingSystem_BE.Service.FlightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class FlightServiceImp implements FlightService {

    @Autowired
    private FlightRepository flightRepository;


        @Override
        public List<Flight> fetchFlight() {
            return flightRepository.findAll().stream()
                    .filter(flight -> flight.getDepartureDate().isAfter(LocalDate.now())) // Chỉ lấy các chuyến bay có ngày khởi hành còn hiệu lực
                    .toList();
        }

        @Override
        public Flight getFlightById(Long flightNumber) {
            return flightRepository.findById(flightNumber)
                    .orElseThrow(() -> new FlightNotFoundException(flightNumber));
        }

        @Override
        public String deleteFlightById(Long flightNumber) {
            if(flightNumber == null)
            {
                throw new IllegalArgumentException("flightNumber must not be null");
            }
            Flight flightDB = getFlightById(flightNumber); // Kiểm tra xem chuyến bay có tồn tại không
            flightRepository.delete(flightDB);
            return "Flight with ID: " + flightNumber + " has been deleted successfully!";
        }

        @Override
        public Flight updateFlight(Long flightNumber, Flight flightRequest) {
            Flight flightDB = getFlightById(flightNumber); // Lấy dữ liệu chuyến bay từ database

            // Validate chuyến bay trước khi cập nhật
            validate(flightRequest);

            // Cập nhật tất cả các trường, không cần kiểm tra null
            flightDB.setAirline(flightRequest.getAirline());
            flightDB.setFromLocation(flightRequest.getFromLocation());
            flightDB.setDropLocation(flightRequest.getDropLocation());
            flightDB.setDepartureDate(flightRequest.getDepartureDate());
            flightDB.setArrivalDate(flightRequest.getArrivalDate());
            flightDB.setPrice(flightRequest.getPrice());

            return flightRepository.save(flightDB);
        }

        @Override
        public Flight saveFlight(Flight flight) {
            validate(flight); // Kiểm tra hợp lệ trước khi tạo mới
            return flightRepository.save(flight);
        }

        @Override
        public List<Flight> fetchFlightSeat() {
            return flightRepository.findAll().stream()
                    .filter(flight -> flight.getDepartureDate().isAfter(LocalDate.now())) // Lọc những chuyến bay có ngày khởi hành trong tương lai
                    .toList();
        }

        @Override
        public List<Flight> searchFlights(String fromLocation, String dropLocation, LocalDate departureDate) {
            if (fromLocation == null && dropLocation == null && departureDate == null) {
                return fetchFlightSeat();
            }
            return flightRepository.searchFlights(fromLocation, dropLocation, departureDate);
        }

        @Override
        public List<Flight> findByAirLineAndPrice(String airline, double price) {
            return flightRepository.findByAirlineAndPrice(airline, price);
        }

        // Phương thức kiểm tra hợp lệ dữ liệu chuyến bay
        private void validate(Flight flight) {
            if (flight.getFlightId() != null && flightRepository.findById(flight.getFlightId()).isPresent()) {
                throw new IllegalArgumentException("Flight with number " + flight.getFlightId() + " already exists");
            }
            if (flight.getAirline() == null || flight.getAirline().isEmpty()) {
                throw new IllegalArgumentException("Airline name must not be null or empty");
            }
            if (flight.getFromLocation() == null || flight.getFromLocation().isEmpty()) {
                throw new IllegalArgumentException("From location must not be null or empty");
            }
            if (flight.getDropLocation() == null || flight.getDropLocation().isEmpty()) {
                throw new IllegalArgumentException("Drop location must not be null or empty");
            }
            if (flight.getPrice() <= 0) {
                throw new IllegalArgumentException("Price must be greater than 0");
            }
            if (flight.getDepartureDate() == null || flight.getArrivalDate() == null) {
                throw new IllegalArgumentException("Departure and arrival dates must not be null");
            }
            if (flight.getArrivalDate().isBefore(flight.getDepartureDate())) {
                throw new IllegalArgumentException("Arrival date must be after departure date");
            }
        }


}

