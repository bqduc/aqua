package net.paramount.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.paramount.css.repository.stock.ProductCoreRepository;
import net.paramount.css.service.stock.ProductCoreService;
import net.paramount.entity.stock.ProductCore;
import net.paramount.framework.repository.BaseRepository;
import net.paramount.framework.service.GenericServiceImpl;


@Service
public class ProductProfileServiceImpl extends GenericServiceImpl<ProductCore, Long> implements ProductCoreService{
	private static final long serialVersionUID = 7785962811434327239L;

	@Inject 
	private ProductCoreRepository repository;

	protected BaseRepository<ProductCore, Long> getRepository() {
		return this.repository;
	}
}
