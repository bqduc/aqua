package net.paramount.repository.trade;

import org.springframework.stereotype.Repository;

import net.paramount.entity.trade.Invoice;
import net.paramount.framework.repository.CodeSerialRepository;

@Repository
public interface InvoiceRepository extends CodeSerialRepository<Invoice, Long>{
}
