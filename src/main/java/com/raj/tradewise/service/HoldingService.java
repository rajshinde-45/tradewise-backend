package com.raj.tradewise.service;


import com.raj.tradewise.dto.stock.BuyStockRequest;
import com.raj.tradewise.dto.stock.HoldingResponse;
import com.raj.tradewise.dto.stock.SellStockRequest;
import com.raj.tradewise.dto.stock.SellStockResponse;
import com.raj.tradewise.entity.Holding;
import com.raj.tradewise.entity.Portfolio;
import com.raj.tradewise.entity.Stock;
import com.raj.tradewise.exception.HoldingNotFoundException;
import com.raj.tradewise.exception.InsufficientQuantityException;
import com.raj.tradewise.exception.MissingInstrumentKeyException;
import com.raj.tradewise.exception.StockNotFoundException;
import com.raj.tradewise.repository.HoldingRepository;
import com.raj.tradewise.repository.StockRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HoldingService {

    private final HoldingRepository holdingRepository;
    private final StockRepository stockRepository;
    private final PortfolioService portfolioService;


    @Transactional
    public HoldingResponse buyStock(BuyStockRequest request){
        Portfolio portfolio = portfolioService.findOwnedPortfolio(request.getPortfolioId());
        Stock stock = findOrCreateStock(request);
        Holding holding = findOrCreateHolding(request,portfolio,stock);
        return mapToResponse(holding);
    }


    private Stock findOrCreateStock(BuyStockRequest request){
//        Stock stock = stockRepository.findBySymbol(request.getSymbol()).orElseGet(()->
//                {
//                    Stock newStock = new Stock();
//                    newStock.setSymbol(request.getSymbol());
//                    newStock.setSector(request.getSector());
//                    newStock.setExchange(request.getExchange());
//                    newStock.setCompanyName(request.getCompanyName());
//                    return stockRepository.save(newStock);
//                });
//        return stock;

            Optional<Stock> existingStock = stockRepository.findBySymbol(request.getSymbol());

            if (existingStock.isPresent()) {

                Stock stock = existingStock.get();

                // Populate the key for an existing stock if it is missing
                if (stock.getInstrumentKey() == null
                        || stock.getInstrumentKey().isBlank()) {

                    if (request.getInstrumentKey() == null
                            || request.getInstrumentKey().isBlank()) {
                        throw new IllegalArgumentException(
                                "Instrument key is required for stock: "
                                        + stock.getSymbol()
                        );
                    }

                    stock.setInstrumentKey(request.getInstrumentKey());
                    stock = stockRepository.save(stock);
                }

                return stock;
            }

            // A new stock must have an instrument key
            if (request.getInstrumentKey() == null
                    || request.getInstrumentKey().isBlank()) {
                throw new MissingInstrumentKeyException(
                        "Instrument key is required when creating a new stock"
                );
            }

            Stock newStock = new Stock();
            newStock.setSymbol(request.getSymbol());
            newStock.setSector(request.getSector());
            newStock.setExchange(request.getExchange());
            newStock.setCompanyName(request.getCompanyName());
            newStock.setInstrumentKey(request.getInstrumentKey());

            return stockRepository.save(newStock);
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


    public List<HoldingResponse> getPortfolioHoldings(Long portfolioId){
        Portfolio portfolio = portfolioService.findOwnedPortfolio(portfolioId);
        List<Holding> holdings = holdingRepository.findByPortfolio(portfolio);

        //Instead of this
//        List<HoldingResponse> responses = new ArrayList<>();
//        for(Holding holding: holdings){
//            HoldingResponse response = mapToResponse(holding);
//            responses.add(response);
//        }
//       we can use this
        return holdings.stream().map(this::mapToResponse).toList();
    }
    
    @Transactional
    public SellStockResponse sellStocks(SellStockRequest request){
        Portfolio portfolio = portfolioService.findOwnedPortfolio(request.getPortfolioId());
        Stock stock = stockRepository.findBySymbol(request.getSymbol()).orElseThrow(()-> new StockNotFoundException("Stock not found"));

        Holding holding = holdingRepository.findByPortfolioAndStock(portfolio,stock).orElseThrow(()-> new HoldingNotFoundException("Holding not found"));

        Integer requestedQuantity = request.getQuantity();
        Integer ownedQuantity = holding.getQuantity();
        if(requestedQuantity>ownedQuantity){
            throw new InsufficientQuantityException("Insufficient quantity");
        }
        if(requestedQuantity.equals(ownedQuantity)){
            holdingRepository.delete(holding);
            return new SellStockResponse(
                    "Stock sold successfully",
                    stock.getSymbol(),
                    requestedQuantity
            );
        }

        holding.setQuantity(ownedQuantity-requestedQuantity);
        holdingRepository.save(holding);


        return new SellStockResponse("Stock sold successfully",
                stock.getSymbol(),
                requestedQuantity);
    }

}
