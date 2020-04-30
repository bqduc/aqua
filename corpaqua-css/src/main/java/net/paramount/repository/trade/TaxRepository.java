package net.paramount.repository.trade;

import org.springframework.stereotype.Repository;

import net.paramount.entity.trade.Tax;
import net.paramount.framework.repository.CodeNameRepository;

@Repository
public interface TaxRepository extends CodeNameRepository<Tax, Long>{
}
