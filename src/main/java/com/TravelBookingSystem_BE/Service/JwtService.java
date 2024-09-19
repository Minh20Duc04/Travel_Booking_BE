package com.TravelBookingSystem_BE.Service;

import com.TravelBookingSystem_BE.Model.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {
    String generateToken(User user);
    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);
    <T> T extractClaim(String token, Function<Claims, T> claimResolver);
    String extractUsername(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
}
