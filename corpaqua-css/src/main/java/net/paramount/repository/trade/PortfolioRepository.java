package net.paramount.repository.trade;

import org.springframework.stereotype.Repository;

import net.paramount.entity.trade.Portfolio;
import net.paramount.framework.repository.CodeNameRepository;

@Repository
public interface PortfolioRepository extends CodeNameRepository<Portfolio, Long>{
}
