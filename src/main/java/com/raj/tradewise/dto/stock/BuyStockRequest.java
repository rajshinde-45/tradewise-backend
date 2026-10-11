package com.raj.tradewise.dto.stock;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BuyStockRequest {

//    private Long portfolioId;
//    private String symbol;
//    private String companyName;
//    private String exchange;
//    private String  sector;
//    private Integer quantity;
//    private BigDecimal buyPrice;
@NotNull
private Long portfolioId;

    @NotBlank
    private String symbol;

    @NotBlank
    private String companyName;

    @NotBlank
    private String exchange;

    @NotBlank
    private String sector;

    @NotNull
    @Min(1)
    private Integer quantity;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal buyPrice;

    private String instrumentKey;

}
