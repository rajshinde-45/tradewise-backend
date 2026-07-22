package com.raj.tradewise.service;


import com.raj.tradewise.dto.stock.BuyStockRequest;
import com.raj.tradewise.dto.stock.HoldingResponse;
import com.raj.tradewise.entity.Holding;
import com.raj.tradewise.entity.Portfolio;
import com.raj.tradewise.entity.Stock;
import com.raj.tradewise.exception.PortfolioNotFoundException;
import com.raj.tradewise.repository.HoldingRepository;
import com.raj.tradewise.repository.PortfolioRepository;
import com.raj.tradewise.repository.StockRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HoldingService {

    private final PortfolioRepository portfolioRepository;
    private final HoldingRepository holdingRepository;
    private final StockRepository stockRepository;

    @Transactional
    public HoldingResponse buyStock(BuyStockRequest request){
        Portfolio portfolio = findPortfolio(request.getPortfolioId());
        Stock stock = findOrCreateStock(request);
        Holding holding = findOrCreateHolding(request,portfolio,stock);
        return mapToResponse(holding);
    }

    private Portfolio findPortfolio(Long id){
        Portfolio portfolio = portfolioRepository.findById(id).orElseThrow(()-> new PortfolioNotFoundException("Portfolio not found"));
        return portfolio;
    }
    private Stock findOrCreateStock(BuyStockRequest request){
        Stock stock = stockRepository.findBySymbol(request.getSymbol()).orElseGet(()->
                {
                    Stock newStock = new Stock();
                    newStock.setSymbol(request.getSymbol());
                    newStock.setSector(request.getSector());
                    newStock.setExchange(request.getExchange());
                    newStock.setCompanyName(request.getCompanyName());
                    return stockRepository.save(newStock);
                });
        return stock;
    }

    private Holding findOrCreateHolding(BuyStockRequest request,Portfolio portfolio, Stock stock){
        Holding holding;
        Optional<Holding> optionalHolding = holdingRepository.findByPortfolioAndStock(portfolio,stock);
        if(optionalHolding.isPresent()){
            holding = optionalHolding.get();
            BigDecimal currentQuantity = BigDecimal.valueOf(holding.getQuantity());
            BigDecimal newQuantity = BigDecimal.valueOf(request.getQuantity());
            BigDecimal totalQuantity = currentQuantity.add(newQuantity);
            holding.setQuantity(holding.getQuantity()+request.getQuantity());
            BigDecimal newBuyingAmount =  request.getBuyPrice().multiply(newQuantity);
            BigDecimal oldBuyingAmount = holding.getAverageBuyPrice().multiply(currentQuantity);
            BigDecimal newAvgBuyingPrice = (newBuyingAmount.add(oldBuyingAmount)).divide(totalQuantity,2, RoundingMode.HALF_UP);
            holding.setAverageBuyPrice(newAvgBuyingPrice);
        }
        else{
            holding = new Holding();
            holding.setPortfolio(portfolio);
            holding.setStock(stock);
            holding.setQuantity(request.getQuantity());
            holding.setAverageBuyPrice(request.getBuyPrice());

        }
        holding = holdingRepository.save(holding);
        return holding;
    }

    private HoldingResponse mapToResponse(Holding holding){
        Stock stock = holding.getStock();
        HoldingResponse response = new HoldingResponse();
        response.setId(holding.getId());
        response.setSymbol(stock.getSymbol());
        response.setCompanyName(stock.getCompanyName());
        response.setQuantity(holding.getQuantity());
        response.setAverageBuyPrice(holding.getAverageBuyPrice());
        return response;
    }


}
