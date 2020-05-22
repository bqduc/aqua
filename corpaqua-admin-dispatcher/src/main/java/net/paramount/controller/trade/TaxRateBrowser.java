package net.paramount.controller.trade;

import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import net.paramount.entity.trade.TaxRate;
import net.paramount.framework.controller.BrowserHome;
import net.paramount.framework.model.CodeNameFilterBase;
import net.paramount.service.trade.TaxRateService;

/**
 * @author ducbq
 */
@Named
@ViewScoped
public class TaxRateBrowser extends BrowserHome<TaxRate, CodeNameFilterBase> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7598506054351621308L;

	@Inject
	private TaxRateService businessService;

	@Setter
	@Getter
	private TaxRate selectedObject;

	@Override
	protected List<TaxRate> doGetBusinessObjects() {
		return businessService.getObjects();
	}
}
