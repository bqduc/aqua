package net.paramount.repository.trade;

import org.springframework.stereotype.Repository;

import net.paramount.entity.trade.Bank;
import net.paramount.framework.repository.CodeNameRepository;

@Repository
public interface BankRepository extends CodeNameRepository<Bank, Long>{
}
