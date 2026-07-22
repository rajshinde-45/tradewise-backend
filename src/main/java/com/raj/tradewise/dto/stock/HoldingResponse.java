package com.raj.tradewise.dto.stock;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HoldingResponse {


    private Long id;
    private String symbol;
    private String companyName;
    private Integer quantity;
    private BigDecimal averageBuyPrice;

}
