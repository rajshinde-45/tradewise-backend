package com.raj.tradewise.service;


import com.raj.tradewise.dto.market.UpstocksLtpResponse;
import com.raj.tradewise.exception.MarketPriceException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.Map;

@Service
//@RequiredArgsConstructor
public class MarketPriceServiceImpl implements MarketPriceService {

    private final RestClient upstocksRestClient;

    public MarketPriceServiceImpl(@Qualifier("upstocksRestClient") RestClient upstocksRestClient){
        this.upstocksRestClient = upstocksRestClient;
    }


    @Override
    public BigDecimal getCurrentPrice(String instrumentKey) {
//        try {
        UpstocksLtpResponse response = upstocksRestClient.get().
                uri(uriBuilder -> uriBuilder
                        .path("/v3/market-quote/ltp")
                        .queryParam("instrument_key", instrumentKey)
                        .build())
                .retrieve()
                .body(UpstocksLtpResponse.class);



            if (response == null || response.getData() == null ||
                     response.getData().isEmpty()){
                throw new MarketPriceException("Unable to fetch market price for symbol " + instrumentKey);
            }

        Map<String,UpstocksLtpResponse.LtpData> data= response.getData();

        return data.values()
                .stream().map(UpstocksLtpResponse.LtpData::getLastPrice)
                .filter(price -> price !=null)
                .findFirst()
                .orElseThrow(()-> new MarketPriceException("Market price is missing for " + instrumentKey));

    }
}
