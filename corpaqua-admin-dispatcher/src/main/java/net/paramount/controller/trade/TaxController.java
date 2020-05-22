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
import net.paramount.css.service.system.SequenceManager;
import net.paramount.entity.general.TaxGroup;
import net.paramount.entity.stock.InventoryCore;
import net.paramount.entity.trade.Tax;
import net.paramount.framework.controller.DetailHome;
import net.paramount.service.trade.TaxGroupService;
import net.paramount.service.trade.TaxService;

/**
 * @author ducbq
 */
@Named
@ViewScoped
//@RequestScope
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TaxController extends DetailHome<Tax> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3952360600471568838L;

	@Inject
	private TaxService businessService;

	@Inject
	private TaxGroupService taxGroupService;

	@Inject
	private SequenceManager sequenceManager;
	
	@Setter @Getter
	private Tax businessObject;

	@Setter @Getter
	private TaxGroup taxGroup;

	@Setter @Getter
	private Tax parent;

	public void remove() throws IOException {
		if (!facesUtilities.isUserInRole("ROLE_ADMIN")) {
			throw new AccessDeniedException("User not authorized! Only role <b>admin</b> can remove cars.");
		}

		if (CommonAssert.has(businessObject) && CommonAssert.has(businessObject.getId())) {
			businessService.remove(businessObject);
			facesUtilities.addDetailMessage("Business object " + businessObject.getName() + " removed successfully");
			Faces.getFlash().setKeepMessages(true);
			Faces.redirect("user/car-list.jsf");
		}
	}

	public String processSubmit(String action) {
		System.out.println(action);
		/*businessObject.addProfile(
				ProductProfile.builder()
				.availableDate(Calendar.getInstance().getTime()).build()
		);*/
		businessService.save(businessObject);
		return "stayBack".equals(action)?"":"inventoryBrowse?faces-redirect=true";
	}

	public String handleSubmit() {
		if (Boolean.TRUE.equals(this.getCreateOther()))
			return "detail";

		return "browse";
	}

	public void clear() {
		businessObject = Tax.builder().build();
		this.setId(null);
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
			String serial = this.sequenceManager.generateNewSerialNumber("TAX");
			this.businessObject = Tax.builder()
					.code(serial)
					.build();
		}
	}

	@Override
	protected String performSave() {
		try {
			//businessObject.setCategory(this.category);
			this.consolidateInventoryData();
			StringBuilder msg = new StringBuilder("Business object ").append(this.businessObject.getName());
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

	private void consolidateInventoryData() {
		//Consolidate data of images
		/*Iterator<Attachment> itr = this.businessObject.getImages().iterator();
		this.businessObject.getCore().setImageBuffer1(itr.next().getData());
		
		for (Attachment attachment :this.businessObject.getImages()) {
			//this.businessObject.getCore().addImage(attachment);
		}*/
	}

	@Override
	protected void performParentSelection(SelectEvent<?> event) {
		Object item = event.getObject(); 
		if (item instanceof Tax) {
			this.parent = (Tax)item;
		}
	}
}
