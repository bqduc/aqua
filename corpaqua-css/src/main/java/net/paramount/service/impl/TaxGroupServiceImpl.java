package net.paramount.service.impl;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.paramount.entity.general.TaxGroup;
import net.paramount.exceptions.AppException;
import net.paramount.exceptions.ObjectNotFoundException;
import net.paramount.framework.repository.BaseRepository;
import net.paramount.framework.service.GenericServiceImpl;
import net.paramount.repository.trade.TaxGroupRepository;
import net.paramount.service.trade.TaxGroupService;

@Service
public class TaxGroupServiceImpl extends GenericServiceImpl<TaxGroup, Long> implements TaxGroupService {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6265119761602842252L;
	@Inject 
	private TaxGroupRepository repository;
	
	protected BaseRepository<TaxGroup, Long> getRepository() {
		return this.repository;
	}

	@Override
	public Optional<TaxGroup> getByName(String name) throws ObjectNotFoundException {
		return repository.findByName(name);
	}

	@Override
	protected Optional<TaxGroup> fetchBusinessObject(Object key) throws AppException {
		return super.getBizObject("findByName", key);
	}
}
