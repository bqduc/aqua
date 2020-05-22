package net.paramount.controller.stock;

import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import net.paramount.css.service.stock.InventoryService;
import net.paramount.domain.model.ProductCoreFilter;
import net.paramount.entity.stock.InventoryCore;
import net.paramount.framework.controller.BrowserHome;

/**
 * @author ducbq
 */
@Named
@ViewScoped
public class InventoryBrowser extends BrowserHome<InventoryCore, ProductCoreFilter> {
	private static final long serialVersionUID = 2662262440970210081L;

	@Inject
	private InventoryService businessService;

	@Setter
	@Getter
	private InventoryCore selectedObject;

	@Override
	protected List<InventoryCore> doGetBusinessObjects() {
		return businessService.getObjects();
	}
}
