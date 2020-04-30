package net.paramount.controller.trade;

import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import net.paramount.entity.trade.Tax;
import net.paramount.framework.controller.BrowserHome;
import net.paramount.framework.model.CodeNameFilterBase;
import net.paramount.service.trade.TaxService;

/**
 * @author ducbq
 */
@Named
@ViewScoped
public class TaxRateBrowser extends BrowserHome<Tax, CodeNameFilterBase> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2400657810665393276L;

	@Inject
	private TaxService businessService;

	@Setter
	@Getter
	private Tax selectedObject;

	@Override
	protected List<Tax> doGetBusinessObjects() {
		return businessService.getObjects();
	}
}
