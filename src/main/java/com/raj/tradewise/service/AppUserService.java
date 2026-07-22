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


@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AppUserService(AppUserRepository appUserRepository,PasswordEncoder passwordEncoder, JwtService jwtService){
    this.appUserRepository=appUserRepository;
    this.passwordEncoder=passwordEncoder;
    this.jwtService=jwtService;
    }

    public RegisterResponse registerUser(RegisterRequest registerRequest){

        if(appUserRepository.existsByEmail(registerRequest.getEmail())){
            throw new EmailAlreadyExistsException("Email already exist");
        }
        AppUser appUser = mapToEntity(registerRequest);
        return mapToResponse(appUserRepository.save(appUser));
    }

    private AppUser mapToEntity(RegisterRequest registerRequest){
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
//        JwtService jwtService = new JwtService();
        // creating token to store user login
        String token = jwtService.generateToken(user);


        return mapToLoginResponse(token);
    }

    private LoginResponse mapToLoginResponse(String token){
        LoginResponse response = new LoginResponse();
        response.setMessage("Login Successful");
        response.setToken(token);
        return response;
    }

}
