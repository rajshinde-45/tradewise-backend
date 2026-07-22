package com.raj.tradewise.controller;


import com.raj.tradewise.dto.portfolio.PortfolioRequest;
import com.raj.tradewise.dto.portfolio.PortfolioResponse;
import com.raj.tradewise.service.PortfolioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService){
        this.portfolioService=portfolioService;
    }


    @PostMapping("")
    public ResponseEntity<PortfolioResponse> createPortfolio(@Valid @RequestBody PortfolioRequest request){
         PortfolioResponse response =  portfolioService.createPortfolio(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
