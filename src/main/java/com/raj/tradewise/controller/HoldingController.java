package com.raj.tradewise.controller;

import com.raj.tradewise.dto.stock.BuyStockRequest;
import com.raj.tradewise.dto.stock.HoldingResponse;
import com.raj.tradewise.service.HoldingService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/holdings")
public class HoldingController {

    private final HoldingService holdingService;
    public HoldingController(HoldingService holdingService){
        this.holdingService=holdingService;
    }

    @PostMapping
    public HoldingResponse buyStock(@Valid @RequestBody BuyStockRequest request){
        return holdingService.buyStock(request);
    }

}
