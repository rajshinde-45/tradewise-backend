package com.raj.tradewise.controller;

import com.raj.tradewise.service.MarketPriceService;
import com.raj.tradewise.service.MarketPriceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/market")
@RequiredArgsConstructor
public class MarketPriceController {

    private final MarketPriceService marketPriceSevice;


    @GetMapping("/ltp")
    public BigDecimal getCurrentPrice(@RequestParam String instrumentKey){
        return marketPriceSevice.getCurrentPrice(instrumentKey);
    }
}
