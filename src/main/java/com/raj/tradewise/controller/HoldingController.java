package com.raj.tradewise.controller;

import com.raj.tradewise.dto.stock.BuyStockRequest;
import com.raj.tradewise.dto.stock.HoldingResponse;
import com.raj.tradewise.dto.stock.SellStockRequest;
import com.raj.tradewise.dto.stock.SellStockResponse;
import com.raj.tradewise.service.HoldingService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/sell")
    public SellStockResponse sellStock(@Valid @RequestBody SellStockRequest request){
        return holdingService.sellStocks(request);
    }

}
