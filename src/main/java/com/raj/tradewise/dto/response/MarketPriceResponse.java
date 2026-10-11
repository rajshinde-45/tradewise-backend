package com.raj.tradewise.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MarketPriceResponse {

    private CurrentPrice currentPrice;

    @Getter
    @Setter
    public static class CurrentPrice{
        @JsonProperty("BSE")
        private BigDecimal bse;

        @JsonProperty("NSE")
        private BigDecimal nse;
    }


}
