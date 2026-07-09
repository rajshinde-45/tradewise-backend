package com.raj.tradewise.service;

import com.raj.tradewise.dto.register.LoginRequest;
import com.raj.tradewise.dto.register.RegisterRequest;
import com.raj.tradewise.dto.response.LoginResponse;
import com.raj.tradewise.dto.response.RegisterResponse;
import com.raj.tradewise.entity.AppUser;
import com.raj.tradewise.exception.EmailAlreadyExistsException;
import com.raj.tradewise.exception.InvalidCredentialsException;
import com.raj.tradewise.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUserService(AppUserRepository appUserRepository,PasswordEncoder passwordEncoder){
    this.appUserRepository=appUserRepository;
    this.passwordEncoder=passwordEncoder;
    }

    public RegisterResponse registerUser(RegisterRequest registerRequest){

        if(appUserRepository.existsByEmail(registerRequest.getEmail())){
            throw new EmailAlreadyExistsException("Email already exist");
        }
        AppUser appUser = mapToEntity(registerRequest);
        return mapToResponse(appUserRepository.save(appUser));
    }

    public AppUser mapToEntity(RegisterRequest registerRequest){
        AppUser appUser = new AppUser();
        appUser.setName(registerRequest.getName());
        appUser.setEmail(registerRequest.getEmail());
        appUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        return appUser;
    }

    private RegisterResponse mapToResponse(AppUser appUser){
        RegisterResponse response = new RegisterResponse();
//        response.setId(appUser.getId());
//        response.setName(appUser.getName());
        response.setEmail(appUser.getEmail());
        response.setMessage("Registration successful");
        return response;
    }

    public LoginResponse login(LoginRequest request){

        AppUser user = appUserRepository.findByEmail(request.getEmail()).orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));

        if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
            throw new InvalidCredentialsException("Invalid credentials");
        }
        return mapToLoginResponse();
    }

    private LoginResponse mapToLoginResponse(){
        LoginResponse response = new LoginResponse();
        response.setMessage("Login Successful");
        return response;
    }

}
