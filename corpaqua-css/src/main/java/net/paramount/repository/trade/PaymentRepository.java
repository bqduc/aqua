package net.paramount.repository.trade;

import org.springframework.stereotype.Repository;

import net.paramount.entity.trade.Payment;
import net.paramount.framework.repository.BaseRepository;

@Repository
public interface PaymentRepository extends BaseRepository<Payment, Long>{
}
