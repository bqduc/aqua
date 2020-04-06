package net.paramount.controller.stock;

import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import net.paramount.css.service.stock.ProductCoreService;
import net.paramount.domain.model.ProductCoreFilter;
import net.paramount.entity.stock.ProductCore;
import net.paramount.framework.controller.BrowserHome;

/**
 * @author ducbq
 */
@Named
@ViewScoped
public class InventoryBrowser extends BrowserHome<ProductCore, ProductCoreFilter> {
	private static final long serialVersionUID = 2662262440970210081L;

	@Inject
	private ProductCoreService businessService;

	@Setter
	@Getter
	private ProductCore selectedObject;

	@Override
	protected List<ProductCore> doGetBusinessObjects() {
		return businessService.getObjects();
	}
}
