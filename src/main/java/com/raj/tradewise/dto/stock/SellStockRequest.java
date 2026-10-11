package com.raj.tradewise.dto.stock;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SellStockRequest {

    @NotNull
    private Long portfolioId;

    @NotBlank
    private String symbol;

    @NotNull
    @Min(1)
    private Integer quantity;

}
