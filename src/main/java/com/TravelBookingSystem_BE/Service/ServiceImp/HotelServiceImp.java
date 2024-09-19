package com.TravelBookingSystem_BE.Service.ServiceImp;

import com.TravelBookingSystem_BE.Exception.HotelNotFoundException;
import com.TravelBookingSystem_BE.Model.Hotel;
import com.TravelBookingSystem_BE.Model.HotelBooking;
import com.TravelBookingSystem_BE.Model.User;
import com.TravelBookingSystem_BE.Repository.HotelRepository;
import com.TravelBookingSystem_BE.Repository.UserRepository;
import com.TravelBookingSystem_BE.Service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class HotelServiceImp implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Hotel addHotel(Hotel newHotel) {
        if (newHotel.getUser() == null || newHotel.getUser().getId() == null) {
            throw new IllegalArgumentException("User is missing or invalid");
        }

        User user = userRepository.findById(newHotel.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + newHotel.getUser().getId()));

        newHotel.setUser(user);
        validate(newHotel);
        return hotelRepository.save(newHotel);
    }

    @Override
    public List<Hotel> getAllHotels() {
        List<Hotel> hotelList = hotelRepository.findAll();
        return hotelList.stream()
                .filter(hotel -> hotel.getCheckInDate() != null && hotel.getCheckOutDate() != null
                        && hotel.getCheckOutDate().isAfter(LocalDate.now())).toList();
    }

    @Override
    public Hotel getHotelById(Long hotelId) {
        return hotelRepository.findById(hotelId)
                .orElseThrow(() -> new HotelNotFoundException("Hotel with id: " + hotelId + " could not be found"));
    }

    @Override
    public Hotel updateById(Long hotelId, Hotel hotelRequest) {
        Hotel hotelDB = getHotelById(hotelId);
        validate(hotelRequest);
        hotelDB.setLocation(hotelRequest.getLocation());
        hotelDB.setCheckInDate(hotelRequest.getCheckInDate());
        hotelDB.setCheckOutDate(hotelRequest.getCheckOutDate());
        hotelDB.setPrice(hotelRequest.getPrice());
        // Save the updated hotel to the repository
        return hotelRepository.save(hotelDB);
    }

    @Override
    public String deleteById(Long hotelId) {
        Hotel hotelDB = getHotelById(hotelId);
        hotelRepository.delete(hotelDB);
        return "Hotel with id: " + hotelId + " has been deleted successfully";
    }

    @Override
    public List<Hotel> searchHotels(String location, LocalDate checkInDate, LocalDate checkOutDate) {
        if(location == null && checkInDate == null && checkOutDate == null)
        {
            return hotelRepository.findAll();
        }
        return hotelRepository.searchHotels(location, checkInDate, checkOutDate);
    }

    @Override
    public List<Hotel> findHotelsByLocationAndRoomType(String location, String roomType) {
        return hotelRepository.findByLocationAndRoomType(location, roomType);
    }

    private void validate(Hotel hotel) {
        if (hotel.getHotelId() != null && hotelRepository.findById(hotel.getHotelId()).isPresent()) {
            throw new IllegalArgumentException("Hotel with ID " + hotel.getHotelId() + " already exists");
        }
        if (hotel.getHotelName() == null || hotel.getHotelName().isEmpty()) {
            throw new IllegalArgumentException("Name must not be null or empty");
        }
        if (hotel.getLocation() == null || hotel.getLocation().isEmpty()) {
            throw new IllegalArgumentException("Location must not be null or empty");
        }
        if(hotel.getPrice() <= 0)
        {
            throw new IllegalArgumentException("Price must be greater or equal 0");
        }
        if (hotel.getCheckInDate() == null || hotel.getCheckOutDate() == null) {
            throw new IllegalArgumentException("Check-in and check-out dates must not be null");
        }
        if (hotel.getCheckOutDate().isBefore(hotel.getCheckInDate())) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }
    }

}
