package net.paramount.controller.trade;

import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import net.paramount.entity.trade.Bank;
import net.paramount.framework.controller.BrowserHome;
import net.paramount.model.BankFilter;
import net.paramount.service.trade.BankService;

/**
 * @author ducbq
 */
@Named
@ViewScoped
public class BankBrowser extends BrowserHome<Bank, BankFilter> {
	private static final long serialVersionUID = 6484822881311592715L;

	@Inject
	private BankService businessService;

	@Setter
	@Getter
	private List<Bank> selectedObjects;

	@Setter
	@Getter
	private List<Bank> businessObjects;

	@Override
	protected List<Bank> doGetBusinessObjects() {
		return this.businessService.getObjects();
	}

	@Override
	public BankFilter createFilterModel() {
		return BankFilter.builder().build();
	}
}
