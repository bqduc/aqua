package net.paramount.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.paramount.css.repository.stock.ProductProfileRepository;
import net.paramount.css.service.stock.ProductProfileService;
import net.paramount.entity.stock.ProductProfile;
import net.paramount.framework.repository.BaseRepository;
import net.paramount.framework.service.GenericServiceImpl;


@Service
public class ProductProfileServiceImpl extends GenericServiceImpl<ProductProfile, Long> implements ProductProfileService{
	private static final long serialVersionUID = 7785962811434327239L;

	@Inject 
	private ProductProfileRepository repository;
	
	protected BaseRepository<ProductProfile, Long> getRepository() {
		return this.repository;
	}
}
