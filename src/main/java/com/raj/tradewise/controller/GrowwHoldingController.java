package com.raj.tradewise.controller;

import com.raj.tradewise.dto.groww.GrowwHoldingResponse;
import com.raj.tradewise.dto.groww.TradeWiseGrowwHoldingResponse;
import com.raj.tradewise.service.GrowwHoldingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/groww")
@RequiredArgsConstructor
public class GrowwHoldingController {

    private final GrowwHoldingService growwHoldingService;

    @GetMapping("/holdings")
    public List<TradeWiseGrowwHoldingResponse> getHoldings(){
        return growwHoldingService.getHoldings();
    }

}
