package com.TravelBookingSystem_BE.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JWTAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/v1/auth/**").permitAll() // Authentication endpoints

                        // Swagger UI and API Docs
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/swagger-resources/**"
                        ).permitAll()

                        // BookingController permissions
                        .requestMatchers("/api/bookings/users/{userId}").hasRole("ADMIN")
                        .requestMatchers("/api/bookings/users/{userId}/bookings/{bookingId}").hasRole("ADMIN")
                        .requestMatchers("/api/bookings/users/{userId}/bookings").hasRole("ADMIN")

                        // CabController permissions
                        .requestMatchers("/api/cabs/create").hasAnyRole("ADMIN","AGENT")
                        .requestMatchers("/api/cabs/search").permitAll()
                        .requestMatchers("/api/cabs/{cabId}").hasAnyRole("ADMIN","AGENT")
                        .requestMatchers("/api/cabs/{cabId}/delete").hasRole("ADMIN")
                        .requestMatchers("/api/cabs/{cabId}/update").hasRole("ADMIN")
                        .requestMatchers("/api/cabs/bookings").permitAll()
                        .requestMatchers("/api/cabs/bookings/manage").hasAnyRole("ADMIN", "AGENT")
                        .requestMatchers("/api/cabs/bookings/email/{custEmail}").permitAll()
                        .requestMatchers("/api/cabs/bookings/phone/{custPhone}").permitAll()
                        .requestMatchers("/api/cabs/bookings/cabId/{cabId}").hasAnyRole("AGENT", "ADMIN")
                        .requestMatchers("/api/cabs/searchByTypeAndPrice").permitAll()
                        .requestMatchers("/api/cabs/bookings/{bookingId}/cancel").permitAll()

                        // FlightController permissions
                        .requestMatchers("/api/flights").permitAll()
                        .requestMatchers("/api/flights/create").hasAnyRole("ADMIN", "AGENT")
                        .requestMatchers("/api/flights/{id}").permitAll()
                        .requestMatchers("/api/flights/{id}/delete").hasRole("ADMIN")
                        .requestMatchers("/api/flights/{id}/update").hasAnyRole("ADMIN")
                        .requestMatchers("/api/flights/seats/confirmed").permitAll()
                        .requestMatchers("/api/flights/search").permitAll()
                        .requestMatchers("/api/flights/bookings").permitAll()
                        .requestMatchers("/api/flights/manage").hasAnyRole("AGENT", "ADMIN")
                        .requestMatchers("/api/flights/bookings/phone/{custPhone}").permitAll()
                        .requestMatchers("/api/flights/bookings/email/{custEmail}").permitAll()
                        .requestMatchers("/api/flights/bookings/{bookingId}/cancel").permitAll()
                        .requestMatchers("/api/flights/bookings/flightNumber/{flightNumber}").hasAnyRole("AGENT", "ADMIN")
                        .requestMatchers("/api/flights/searchByAirlineAndPrice").permitAll()

                        // HotelController permissions
                        .requestMatchers("/api/hotels/create").hasAnyRole("AGENT", "ADMIN")
                        .requestMatchers("/api/hotels").permitAll()
                        .requestMatchers("/api/hotels/{hotelId}").permitAll()
                        .requestMatchers("/api/hotels/{hotelId}/update").hasRole("ADMIN")
                        .requestMatchers("/api/hotels/{hotelId}/delete").hasRole("ADMIN")
                        .requestMatchers("/api/hotels/search").permitAll()
                        .requestMatchers("/api/hotels/searchByLocationAndRoomType").permitAll()
                        .requestMatchers("/api/hotels/bookings").permitAll()
                        .requestMatchers("/api/hotels/manage").hasAnyRole("AGENT", "ADMIN")
                        .requestMatchers("/api/hotels/email").permitAll()
                        .requestMatchers("/api/hotels/phone").permitAll()
                        .requestMatchers("/api/hotels/bookings/hotel/{hotelId}").hasAnyRole("AGENT", "ADMIN")
                        .requestMatchers("/api/hotels/bookings/{bookingId}/cancel").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
