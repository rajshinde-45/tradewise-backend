package com.raj.tradewise.service;

import com.raj.tradewise.dto.groww.GrowwResponse;
import com.raj.tradewise.dto.groww.TradeWiseGrowwHoldingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GrowwHoldingService {

    private final RestClient growwRestclient;

    public List<TradeWiseGrowwHoldingResponse> getHoldings(){

        GrowwResponse growwResponse = growwRestclient.get()
                .uri("/v1/holdings/user")
                .retrieve()
                .body(GrowwResponse.class);

        return growwResponse.getPayload().getHoldings().stream()
                .map(holding -> new TradeWiseGrowwHoldingResponse(
                        holding.getTradingSymbol(),
                        holding.getQuantity(),
                        holding.getAveragePrice()
                )).toList();
    }


}
