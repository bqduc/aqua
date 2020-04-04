package net.paramount.repository.trade;

import org.springframework.stereotype.Repository;

import net.paramount.entity.emx.CustomerInvoice;
import net.paramount.framework.repository.BaseRepository;

@Repository
public interface CustomerInvoiceRepository extends BaseRepository<CustomerInvoice, Long>{
}
