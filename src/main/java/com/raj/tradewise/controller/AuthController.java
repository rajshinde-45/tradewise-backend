package com.raj.tradewise.controller;

import com.raj.tradewise.dto.register.LoginRequest;
import com.raj.tradewise.dto.register.RegisterRequest;
import com.raj.tradewise.dto.response.LoginResponse;
import com.raj.tradewise.dto.response.RegisterResponse;
import com.raj.tradewise.service.AppUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api/v1/auth")
public class AuthController {

    private final AppUserService appUserService;
    public AuthController(AppUserService appUserService){
        this.appUserService=appUserService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody RegisterRequest registerRequest){
        RegisterResponse registerResponse = appUserService.registerUser(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest request){
        LoginResponse response = appUserService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
