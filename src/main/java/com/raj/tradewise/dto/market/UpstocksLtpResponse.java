package com.raj.tradewise.dto.market;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
public class UpstocksLtpResponse {

    private String status;
    private Map<String,LtpData> Data;

    @Getter
    @Setter
    public static class LtpData{

        @JsonProperty("last_price")
        private BigDecimal lastPrice;

        @JsonProperty("instrument_token")
        private String instrumentToken;

        private Integer ltq;
        private Long volume;

        private BigDecimal cp;
    }
}
