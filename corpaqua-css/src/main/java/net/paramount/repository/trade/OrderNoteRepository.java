package net.paramount.repository.trade;

import org.springframework.stereotype.Repository;

import net.paramount.entity.trade.OrderNote;
import net.paramount.framework.repository.CodeNameRepository;

@Repository
public interface OrderNoteRepository extends CodeNameRepository<OrderNote, Long>{
}
