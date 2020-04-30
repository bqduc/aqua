package net.paramount.service.impl;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.paramount.entity.trade.Tax;
import net.paramount.exceptions.AppException;
import net.paramount.exceptions.ObjectNotFoundException;
import net.paramount.framework.repository.BaseRepository;
import net.paramount.framework.service.GenericServiceImpl;
import net.paramount.repository.trade.TaxRepository;
import net.paramount.service.trade.TaxService;

@Service
public class TaxServiceImpl extends GenericServiceImpl<Tax, Long> implements TaxService{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1827391253714417691L;

	@Inject 
	private TaxRepository repository;
	
	protected BaseRepository<Tax, Long> getRepository() {
		return this.repository;
	}

	@Override
	public Optional<Tax> getByName(String name) throws ObjectNotFoundException {
		return repository.findByName(name);
	}

	@Override
	public Optional<Tax> getByCode(String code) throws ObjectNotFoundException {
		return repository.findByCode(code);
	}

	@Override
	protected Page<Tax> performSearch(String keyword, Pageable pageable) {
		return repository.find(keyword, pageable);
	}

	@Override
	protected Optional<Tax> fetchBusinessObject(Object key) throws AppException {
		return super.getBizObject("findByName", key);
	}
}
