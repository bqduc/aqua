package net.paramount.service.impl.trade;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.paramount.entity.trade.Invoice;
import net.paramount.framework.repository.BaseRepository;
import net.paramount.framework.service.GenericServiceImpl;
import net.paramount.repository.trade.InvoiceRepository;
import net.paramount.service.trade.InvoiceService;

@Service
public class InvoiceServiceImpl extends GenericServiceImpl<Invoice, Long> implements InvoiceService{
	private static final long serialVersionUID = -3091073932588099354L;
	
	@Inject 
	private InvoiceRepository repository;
	
	protected BaseRepository<Invoice, Long> getRepository() {
		return this.repository;
	}
}
