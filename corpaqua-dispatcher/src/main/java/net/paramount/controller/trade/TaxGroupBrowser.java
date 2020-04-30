package net.paramount.controller.trade;

import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import net.paramount.entity.general.TaxGroup;
import net.paramount.framework.controller.BrowserHome;
import net.paramount.framework.model.CodeNameFilterBase;
import net.paramount.service.trade.TaxGroupService;

/**
 * @author ducbq
 */
@Named
@ViewScoped
public class TaxGroupBrowser extends BrowserHome<TaxGroup, CodeNameFilterBase> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5368360361651744469L;

	@Inject
	private TaxGroupService businessService;

	@Setter
	@Getter
	private TaxGroup selectedObject;

	@Override
	protected List<TaxGroup> doGetBusinessObjects() {
		return businessService.getObjects();
	}
}
