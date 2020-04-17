package net.paramount.service.impl;

import javax.inject.Inject;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import net.paramount.css.repository.stock.InventoryItemRepository;
import net.paramount.css.service.stock.InventoryItemService;
import net.paramount.css.specification.InventoryItemSpecification;
import net.paramount.entity.stock.InventoryItem;
import net.paramount.exceptions.ObjectNotFoundException;
import net.paramount.framework.model.SearchParameter;
import net.paramount.framework.repository.BaseRepository;
import net.paramount.framework.service.GenericServiceImpl;


@Service
public class InventoryItemServiceImpl extends GenericServiceImpl<InventoryItem, Long> implements InventoryItemService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7761477574156308888L;

	@Inject 
	private InventoryItemRepository repository;
	
	protected BaseRepository<InventoryItem, Long> getRepository() {
		return this.repository;
	}

	@Override
	public InventoryItem getByBarcode(String barcode) throws ObjectNotFoundException {
		return (InventoryItem)super.getOptionalObject(repository.findByBarcode(barcode));
	}

	@Override
	protected Specification<InventoryItem> getRepoSpecification(SearchParameter searchParameter) {
		return InventoryItemSpecification.buildSpecification(searchParameter);
	}
}
