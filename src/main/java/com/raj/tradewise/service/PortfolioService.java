package com.raj.tradewise.service;


import com.raj.tradewise.dto.portfolio.PortfolioRequest;
import com.raj.tradewise.dto.portfolio.PortfolioResponse;
import com.raj.tradewise.entity.AppUser;
import com.raj.tradewise.entity.Portfolio;
import com.raj.tradewise.repository.PortfolioRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PortfolioService {

    private  final PortfolioRepository portfolioRepository;

    public PortfolioService(PortfolioRepository portfolioRepository){
        this.portfolioRepository=portfolioRepository;
    }

    public PortfolioResponse createPortfolio(PortfolioRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser user = (AppUser) authentication.getPrincipal();
        Portfolio portfolio = mapToEntity(request,user);
        return mapToResponse(portfolioRepository.save(portfolio));
    }

    public Portfolio mapToEntity(PortfolioRequest request, AppUser user){
        Portfolio portfolio =new Portfolio();
        portfolio.setUser(user);
        portfolio.setName(request.getName());
        portfolio.setCreatedAt(LocalDateTime.now());
        return  portfolio;
    }

    public PortfolioResponse mapToResponse(Portfolio portfolio){
        PortfolioResponse response = new PortfolioResponse();
        response.setId(portfolio.getId());
        response.setName(portfolio.getName());
        response.setCreatedAt(portfolio.getCreatedAt());
        return response;
    }



}
