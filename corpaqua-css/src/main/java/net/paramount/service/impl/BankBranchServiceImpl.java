package net.paramount.service.impl;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.paramount.entity.trade.BankBranch;
import net.paramount.exceptions.AppException;
import net.paramount.exceptions.ObjectNotFoundException;
import net.paramount.framework.repository.BaseRepository;
import net.paramount.framework.service.GenericServiceImpl;
import net.paramount.repository.trade.BankBranchRepository;
import net.paramount.service.trade.BankBranchService;

@Service
public class BankBranchServiceImpl extends GenericServiceImpl<BankBranch, Long> implements BankBranchService{
	private static final long serialVersionUID = 1946792625414253696L;

	@Inject 
	private BankBranchRepository repository;
	
	protected BaseRepository<BankBranch, Long> getRepository() {
		return this.repository;
	}

	@Override
	public Optional<BankBranch> getByName(String name) throws ObjectNotFoundException {
		return repository.findByName(name);
	}

	@Override
	public Optional<BankBranch> getByCode(String code) throws ObjectNotFoundException {
		return repository.findByCode(code);
	}

	@Override
	protected Page<BankBranch> performSearch(String keyword, Pageable pageable) {
		return repository.find(keyword, pageable);
	}

	@Override
	protected Optional<BankBranch> fetchBusinessObject(Object key) throws AppException {
		return super.getBizObject("findByName", key);
	}
}
