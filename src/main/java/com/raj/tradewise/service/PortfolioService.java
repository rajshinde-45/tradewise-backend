package com.raj.tradewise.service;


import com.raj.tradewise.dto.portfolio.PortfolioRequest;
import com.raj.tradewise.dto.portfolio.PortfolioResponse;
import com.raj.tradewise.dto.portfolio.PortfolioSummaryResponse;
import com.raj.tradewise.entity.AppUser;
import com.raj.tradewise.entity.Holding;
import com.raj.tradewise.entity.Portfolio;
import com.raj.tradewise.exception.MarketPriceException;
import com.raj.tradewise.exception.MissingInstrumentKeyException;
import com.raj.tradewise.exception.PortfolioNotFoundException;
import com.raj.tradewise.exception.UnauthorizedAccessException;
import com.raj.tradewise.repository.HoldingRepository;
import com.raj.tradewise.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private  final PortfolioRepository portfolioRepository;
    private final HoldingRepository holdingRepository;
    private final MarketPriceService marketPriceService;


    public PortfolioResponse createPortfolio(PortfolioRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser user = (AppUser) authentication.getPrincipal();
        Portfolio portfolio = mapToEntity(request,user);
        return mapToResponse(portfolioRepository.save(portfolio));
    }

    private Portfolio mapToEntity(PortfolioRequest request, AppUser user){
        Portfolio portfolio =new Portfolio();
        portfolio.setUser(user);
        portfolio.setName(request.getName());
        portfolio.setCreatedAt(LocalDateTime.now());
        return  portfolio;
    }

    private PortfolioResponse mapToResponse(Portfolio portfolio){
        PortfolioResponse response = new PortfolioResponse();
        response.setId(portfolio.getId());
        response.setName(portfolio.getName());
        response.setCreatedAt(portfolio.getCreatedAt());
        return response;
    }

    public Portfolio findOwnedPortfolio(Long id){
        Portfolio portfolio = portfolioRepository.findById(id).orElseThrow(()-> new PortfolioNotFoundException("Portfolio not found"));

        //user verification
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser user = (AppUser) authentication.getPrincipal();
        if(!portfolio.getUser().getId().equals(user.getId())){
            throw new UnauthorizedAccessException("You are not authorize to access this portfolio");
        }
        return portfolio;
    }

    public PortfolioSummaryResponse getPortfolioSummary(Long portfolioId){
        Portfolio portfolio = findOwnedPortfolio(portfolioId);
        List<Holding>holdings = holdingRepository.findByPortfolio(portfolio);

//        for(Holding holding:holdings){
//            BigDecimal investedAmount = holding.getAverageBuyPrice().multiply(BigDecimal.valueOf(holding.getQuantity()));
//            totalInvested = totalInvested.add(investedAmount);
//        }

        BigDecimal totalInvested = holdings.stream().map(holding -> holding.getAverageBuyPrice().multiply(BigDecimal.valueOf(holding.getQuantity()))).
                reduce(BigDecimal.ZERO,BigDecimal::add);

        BigDecimal currentValue = BigDecimal.ZERO;
        for(Holding holding:holdings){

            String instrumentKey = holding.getStock().getInstrumentKey();
            if(instrumentKey ==null || instrumentKey.isBlank()){
                throw new MissingInstrumentKeyException("Instrument key is missing for stock: "+ holding.getStock().getSymbol());
            }

            BigDecimal currentPrice = marketPriceService.getCurrentPrice(instrumentKey);

            BigDecimal holdingCurrentValue = currentPrice.multiply(BigDecimal.valueOf(holding.getQuantity()));

            currentValue = currentValue.add(holdingCurrentValue);

        }


    BigDecimal profitLoss = currentValue.subtract(totalInvested);
    BigDecimal profitLossPercentage = BigDecimal.ZERO;
    if(totalInvested.compareTo(BigDecimal.ZERO)>0){
        profitLossPercentage  = profitLoss
                .multiply(BigDecimal.valueOf(100))
                .divide(totalInvested, 4, RoundingMode.HALF_UP);;
    }

    return new PortfolioSummaryResponse(portfolio.getId(),portfolio.getName(), totalInvested,currentValue,profitLoss, profitLossPercentage);
    }



}
