package net.paramount.repository.trade;

import org.springframework.stereotype.Repository;

import net.paramount.entity.trade.BankBranch;
import net.paramount.framework.repository.CodeNameRepository;

@Repository
public interface BankBranchRepository extends CodeNameRepository<BankBranch, Long>{
}
