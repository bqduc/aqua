package net.paramount.repository.trade;

import org.springframework.stereotype.Repository;

import net.paramount.entity.trade.Pos;
import net.paramount.framework.repository.CodeNameRepository;

@Repository
public interface PosRepository extends CodeNameRepository<Pos, Long>{
}
