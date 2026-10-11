package com.raj.tradewise.dto.groww;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class GrowwHoldingResponse {

    private String isin;

    @JsonProperty("trading_symbol")
    private String tradingSymbol;

    private BigDecimal quantity;

    @JsonProperty("average_price")
    private BigDecimal averagePrice;
}
