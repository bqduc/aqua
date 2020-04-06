package net.paramount.controller.stock;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.primefaces.event.SelectEvent;
import org.springframework.security.access.AccessDeniedException;

import lombok.Getter;
import lombok.Setter;
import net.paramount.common.CommonAssert;
import net.paramount.common.ListUtility;
import net.paramount.css.service.stock.ProductCoreService;
import net.paramount.domain.entity.general.Catalogue;
import net.paramount.entity.stock.ProductCore;
import net.paramount.entity.stock.ProductProfile;
import net.paramount.framework.controller.DetailHome;
import net.paramount.global.GlobalConstants;
import net.paramount.global.GlobalServicesRepository;
import net.paramount.service.general.CatalogueService;

/**
 * @author ducbq
 */
@Named
@ViewScoped
public class InventoryController extends DetailHome<ProductCore> implements Serializable {
	private static final long serialVersionUID = -4252723578792859073L;

	@Inject
	private ProductCoreService businessService;

	@Inject
	private CatalogueService catalogueService;

	@Setter
	@Getter
	private Long id;

	@Setter
	@Getter
	private ProductCore businessObject;

	@Setter
	@Getter
	private ProductCore parent;

	@Setter
	@Getter
	private Catalogue category;

	@Setter
	@Getter
	private List<Catalogue> catalogues = ListUtility.createList();

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
		businessObject.addProfile(
				ProductProfile.builder()
				.availableDate(Calendar.getInstance().getTime()).build()
		);
		businessService.save(businessObject);
		return "stayBack".equals(action)?"":"inventoryBrowse?faces-redirect=true";
	}
	/*
	public void save() {
		try {
			//businessObject.setCategory(this.category);
			businessObject.setParent(parent);
			StringBuilder msg = new StringBuilder("Business object ").append(businessObject.getName());
			if (businessObject.getId() == null) {
				msg.append(" created successfully");
			} else {
				msg.append(" updated successfully");
			}

			businessService.saveOrUpdate(businessObject);
			facesUtilities.addDetailMessage(msg.toString());
			if (!Boolean.TRUE.equals(this.getCreateOther())) {
				Faces.getFlash().setKeepMessages(true);
				Faces.redirect("/bszone/auxadmin/authorityBrowse.xhtml");
			} else {
				Faces.getFlash().setKeepMessages(true);
				Faces.redirect("/bszone/auxadmin/authorityDetail.xhtml");
			}
		} catch (IOException e) {
			this.log.error(e);
		}
	}
	*/

	public String handleSubmit() {
		if (Boolean.TRUE.equals(this.getCreateOther()))
			return "detail";

		return "browse";
	}

	public void clear() {
		businessObject = new ProductCore();
		id = null;
	}

	public boolean isNew() {
		return businessObject == null || businessObject.getId() == null;
	}

	public void handleParentSelect(SelectEvent<?> event) { 
		Object item = event.getObject(); 
		if (item instanceof ProductCore) {
			this.parent = (ProductCore)item;
		}
		//FacesMessage msg = new FacesMessage("Selected", "Item:" + item); 
	}

	@Override
	protected void doInit() {
		/*if (Faces.isAjaxRequest()) {
			return;
		}*/

		if (CommonAssert.has(id)) {
			businessObject = businessService.getObject(id);
		} else {
			businessObject = new ProductCore();
		}

		this.parent = businessObject.getParent();
		//Load additional data 
		this.catalogues.add(
				(Catalogue)GlobalServicesRepository.builder().build().buildNoneObject(
				Catalogue.builder().build(), 
				ListUtility.createMap(
						"id", GlobalConstants.NONE_OBJECT_ID, 
						"code", GlobalConstants.NONE_OBJECT_CODE, 
						"name", GlobalConstants.NONE_OBJECT_NAME, 
						"translatedName", GlobalConstants.NONE_OBJECT_TRANSLATED_NAME)));

		this.catalogues.addAll(catalogueService.getVisibleObjects());
	}


	@Override
	protected String performSave() {
		try {
			//businessObject.setCategory(this.category);
			businessObject.setParent(parent);
			StringBuilder msg = new StringBuilder("Business object ").append(businessObject.getName());
			if (businessObject.getId() == null) {
				msg.append(" created successfully");
			} else {
				msg.append(" updated successfully");
			}

			businessService.saveOrUpdate(businessObject);
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

	/*@Override
	protected ProductCore buildNoneObject() {
		return (ProductCore)GlobalServicesRepository.builder().build().buildNoneObject(ProductCore.builder().build(), ListUtility.createMap("id", GlobalConstants.NONE_OBJECT_ID, "name", GlobalConstants.NONE_OBJECT_CODE, "displayName", GlobalConstants.NONE_OBJECT_NAME));
	}*/
}
