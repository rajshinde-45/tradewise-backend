package com.raj.tradewise.repository;

import com.raj.tradewise.entity.Holding;
import com.raj.tradewise.entity.Portfolio;
import com.raj.tradewise.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HoldingRepository extends JpaRepository<Holding,Long> {

    Optional<Holding> findByPortfolioAndStock(Portfolio portfolio, Stock stock);

}
