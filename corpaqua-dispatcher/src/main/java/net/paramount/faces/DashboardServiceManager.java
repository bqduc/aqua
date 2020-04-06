/**
 * 
 */
package net.paramount.faces;

import java.util.Calendar;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import net.paramount.auth.service.AccessDecisionPolicyService;
import net.paramount.auth.service.AuthorityService;
import net.paramount.css.service.contact.ContactService;
import net.paramount.css.service.org.BusinessUnitService;
import net.paramount.css.service.stock.ProductCoreService;
import net.paramount.framework.component.CompCore;
import net.paramount.service.general.CatalogueService;
import net.paramount.service.general.CatalogueSubtypeService;
import net.paramount.service.trade.BankBranchService;
import net.paramount.service.trade.BankService;
import net.paramount.service.trade.OrderNoteService;
import net.paramount.service.trade.PosService;

/**
 * @author ducbq
 *
 */
@Named
@Component
@SessionScope
public class DashboardServiceManager extends CompCore {
	private static final long serialVersionUID = 9104358853063262882L;

	@Inject 
	private AccessDecisionPolicyService accessDecisionPolicyService;
	
	@Inject 
	private CatalogueService catalogueService;

	@Inject 
	private ContactService contactService;

	@Inject 
	private CatalogueSubtypeService catalogueSubtypeService;

	@Inject 
	private AuthorityService authorityService;
	
	@Inject 
	private ProductCoreService productService;

	@Inject 
	private BusinessUnitService businessUnitService;

	@Inject 
	private BankService bankService;

	@Inject 
	private BankBranchService bankBranchService;

	@Inject 
	private PosService posService;

	@Inject 
	private OrderNoteService orderNoteService;

	//private Map<String, Object> dashboardDataMap = ListUtility.createMap();

	//private static boolean isRunning = false;

	public void syncDashboardData() {
		/*if (isRunning)
			return;

		isRunning = true;*/
		System.out.println("DashboardServiceManager fired at: " + Calendar.getInstance().getTime());
		/*this.dashboardDataMap.put(DashboardElement.accessDecisionPolicyCount.name(), Long.valueOf(accessDecisionPolicyService.count()));
		this.dashboardDataMap.put(DashboardElement.catalogueCount.name(), Long.valueOf(this.catalogueService.count()));
		this.dashboardDataMap.put(DashboardElement.authorityCount.name(), Long.valueOf(this.authorityService.count()));*/

		///isRunning = false;
	}

	public Long requestDashboardData(String dashboardKey) {
		return doRequestDashboardData(dashboardKey);
	}

	protected Long doRequestDashboardData(String dashboardKey) {
		if (DashboardElement.orderNoteCount.name().equals(dashboardKey))
			return this.orderNoteService.count();

		if (DashboardElement.bankCount.name().equals(dashboardKey))
			return this.bankService.count();

		if (DashboardElement.bankBranchCount.name().equals(dashboardKey))
			return this.bankBranchService.count();

		if (DashboardElement.pointOfSaleCount.name().equals(dashboardKey))
			return this.posService.count();

		if (DashboardElement.contactCount.name().equals(dashboardKey))
			return this.contactService.count();

		if (DashboardElement.catalogueCount.name().equals(dashboardKey))
			return this.catalogueService.count();

		if (DashboardElement.authorityCount.name().equals(dashboardKey))
			return this.authorityService.count();

		if (DashboardElement.accessDecisionPolicyCount.name().equals(dashboardKey))
			return this.accessDecisionPolicyService.count();

		if (DashboardElement.catalogueSubtypeCount.name().equals(dashboardKey))
			return this.catalogueSubtypeService.count();

		if (DashboardElement.inventoryCount.name().equals(dashboardKey))
			return this.productService.count();

		return Long.valueOf(-1l);
	}
}
