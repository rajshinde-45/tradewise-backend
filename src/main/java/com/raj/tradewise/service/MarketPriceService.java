package com.raj.tradewise.service;

import java.math.BigDecimal;

public interface MarketPriceService {

    BigDecimal getCurrentPrice(String symbol);
}
