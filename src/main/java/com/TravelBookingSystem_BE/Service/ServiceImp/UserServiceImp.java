package com.TravelBookingSystem_BE.Service.ServiceImp;

import com.TravelBookingSystem_BE.Model.User;
import com.TravelBookingSystem_BE.Repository.UserRepository;
import com.TravelBookingSystem_BE.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public User getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User not found with id: "+userId));
        return user;
    }

    @Override
    public User updateById(Long userId, User userDetails) {
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found with id: "+userId));
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setAddress(userDetails.getAddress());
        user.setEmail(userDetails.getEmail());
        user.setRole(userDetails.getRole());
        user.setUsername(userDetails.getUsername());
        user.setPhone(userDetails.getPhone());
        user.setPassword(userDetails.getPassword());
        return userRepository.save(user);
    }

    @Override
    public String deleteById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User not found with id: "+userId));
        userRepository.deleteById(userId);
        return "User account has been delete successfully";
    }

}
