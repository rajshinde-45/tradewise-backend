package com.raj.tradewise.dto.stock;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SellStockResponse {

    private String message;
    private String symbol;
    private Integer soldQuantity;
}
