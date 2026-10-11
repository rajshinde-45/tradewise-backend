package com.raj.tradewise.controller;


import com.raj.tradewise.dto.portfolio.PortfolioRequest;
import com.raj.tradewise.dto.portfolio.PortfolioResponse;
import com.raj.tradewise.dto.portfolio.PortfolioSummaryResponse;
import com.raj.tradewise.dto.stock.HoldingResponse;
import com.raj.tradewise.service.HoldingService;
import com.raj.tradewise.service.PortfolioService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/portfolios")
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final HoldingService holdingService;

    public PortfolioController(PortfolioService portfolioService,HoldingService  holdingService){
        this.portfolioService=portfolioService;
        this.holdingService = holdingService;
    }


    @PostMapping("")
    public ResponseEntity<PortfolioResponse> createPortfolio(@Valid @RequestBody PortfolioRequest request){
         PortfolioResponse response =  portfolioService.createPortfolio(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{portfolioId}/holdings")
    public List<HoldingResponse> getPortfolioHoldings(@PathVariable Long portfolioId){
        return holdingService.getPortfolioHoldings(portfolioId);
    }

    @GetMapping("/{portfolioId}/summary")
    public PortfolioSummaryResponse getPortfolioSummary(@PathVariable Long portfolioId){
        return portfolioService.getPortfolioSummary(portfolioId);
    }

}
