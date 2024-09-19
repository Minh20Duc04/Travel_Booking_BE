package com.TravelBookingSystem_BE.Service;

import com.TravelBookingSystem_BE.Model.User;

public interface UserService {
    public User getUserById(Long userId);

    public User updateById(Long userId, User userDetails);

    public String deleteById(Long userId);
}
