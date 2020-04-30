package net.paramount.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.paramount.entity.trade.TaxRate;
import net.paramount.framework.repository.BaseRepository;
import net.paramount.framework.service.GenericServiceImpl;
import net.paramount.repository.trade.TaxRateRepository;
import net.paramount.service.trade.TaxRateService;

@Service
public class TaxRateServiceImpl extends GenericServiceImpl<TaxRate, Long> implements TaxRateService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5003151117371926438L;

	@Inject 
	private TaxRateRepository repository;
	
	protected BaseRepository<TaxRate, Long> getRepository() {
		return this.repository;
	}
}
