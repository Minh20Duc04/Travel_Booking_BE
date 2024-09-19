package com.TravelBookingSystem_BE.Auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin("http://8081-example")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/users")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        String response = authenticationService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request)
    {
        AuthenticationResponse logInRequest = authenticationService.authenticate(request);
        return ResponseEntity.ok(logInRequest);
    }





}
