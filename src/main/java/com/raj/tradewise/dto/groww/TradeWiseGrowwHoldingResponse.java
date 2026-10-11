package com.raj.tradewise.dto.groww;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class TradeWiseGrowwHoldingResponse {

    private String symbol;
    private BigDecimal quantity;
    private BigDecimal averagePrice;


}
