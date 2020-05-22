package net.paramount.controller.trade;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.primefaces.event.SelectEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.access.AccessDeniedException;

import lombok.Getter;
import lombok.Setter;
import net.paramount.common.CommonAssert;
import net.paramount.domain.entity.general.Catalogue;
import net.paramount.entity.stock.InventoryCore;
import net.paramount.entity.trade.TaxRate;
import net.paramount.framework.controller.DetailHome;
import net.paramount.service.general.CatalogueService;
import net.paramount.service.trade.TaxRateService;

/**
 * @author ducbq
 */
@Named
@ViewScoped
//@RequestScope
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TaxRateController extends DetailHome<TaxRate> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3952360600471568838L;

	@Inject
	private TaxRateService businessService;

	@Inject
	private CatalogueService catalogueService;

	@Setter
	@Getter
	private TaxRate businessObject;

	@Setter
	@Getter
	private InventoryCore parent;

	@Setter
	@Getter
	private Catalogue category;

	public void remove() throws IOException {
		if (!facesUtilities.isUserInRole("ROLE_ADMIN")) {
			throw new AccessDeniedException("User not authorized! Only role <b>admin</b> can remove cars.");
		}

		if (CommonAssert.has(businessObject) && CommonAssert.has(businessObject.getId())) {
			businessService.remove(businessObject);
			facesUtilities.addDetailMessage("Business object " + businessObject.getTax().getName() + " removed successfully");
			Faces.getFlash().setKeepMessages(true);
			Faces.redirect("user/car-list.jsf");
		}
	}

	public String processSubmit(String action) {
		System.out.println(action);
		businessService.save(businessObject);
		return "stayBack".equals(action)?"":"inventoryBrowse?faces-redirect=true";
	}

	public String handleSubmit() {
		if (Boolean.TRUE.equals(this.getCreateOther()))
			return "detail";

		return "browse";
	}

	public void clear() {
		businessObject = new TaxRate();
		setId(null);
	}

	public boolean isNew() {
		return businessObject == null || businessObject.getId() == null;
	}

	@Override
	protected void onInit() {
		/*if (Faces.isAjaxRequest()) {
			return;
		}*/

		if (CommonAssert.has(getId())) {
			//businessObject = businessService.getObject(id);
			this.businessObject = this.businessService.getObject(getId());
		} else if (null==this.businessObject){
			this.businessObject = new TaxRate();
		}
	}


	@Override
	protected String performSave() {
		try {
			//businessObject.setCategory(this.category);
			this.consolidateInventoryData();
			StringBuilder msg = new StringBuilder("Business object ").append(this.businessObject.getTax().getName());
			if (this.businessObject.getId() == null) {
				msg.append(" created successfully");
			} else {
				msg.append(" updated successfully");
			}

			businessService.saveOrUpdate(this.businessObject);

			//Save additional properties
			//saveInventoryImages();

			facesUtilities.addDetailMessage(msg.toString());
			/*if (!Boolean.TRUE.equals(this.getCreateOther())) {
				Faces.getFlash().setKeepMessages(true);
				Faces.redirect("/bszone/auxadmin/authorityBrowse.xhtml");
			} else {
				Faces.getFlash().setKeepMessages(true);
				Faces.redirect("/bszone/auxadmin/authorityDetail.xhtml");
			}*/
		} catch (Exception e) {
			this.log.error(e);
		}
		return "success";
	}

	@Override
	protected void performParentSelection(SelectEvent<?> event) {
		Object item = event.getObject(); 
		if (item instanceof InventoryCore) {
			this.parent = (InventoryCore)item;
		}
		//FacesMessage msg = new FacesMessage("Selected", "Item:" + item); 
	}

	private void consolidateInventoryData() {
	}

}
