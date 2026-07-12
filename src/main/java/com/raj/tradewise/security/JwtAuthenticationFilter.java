package com.raj.tradewise.security;

import com.raj.tradewise.entity.AppUser;
import com.raj.tradewise.exception.InvalidCredentialsException;
import com.raj.tradewise.repository.AppUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtService jwtService;
    private final AppUserRepository appUserRepository;

    public JwtAuthenticationFilter(JwtService jwtService, AppUserRepository appUserRepository){
        this.jwtService = jwtService;
        this.appUserRepository = appUserRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        String jwt  =  authHeader.substring(7);
        String email = jwtService.extractEmail(jwt);

        AppUser user = appUserRepository.findByEmail(email).orElseThrow(()-> new InvalidCredentialsException("Invalid credentials"));
        if(jwtService.validateToken(jwt,user)){
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(user,null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);



    }


}
