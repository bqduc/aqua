package net.paramount.repository.trade;

import org.springframework.stereotype.Repository;

import net.paramount.entity.trade.TaxRate;
import net.paramount.framework.repository.BaseRepository;

@Repository
public interface TaxRateRepository extends BaseRepository<TaxRate, Long>{
}
