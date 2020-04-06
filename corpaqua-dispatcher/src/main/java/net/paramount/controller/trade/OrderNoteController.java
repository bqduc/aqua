package net.paramount.controller.trade;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.springframework.security.access.AccessDeniedException;

import lombok.Getter;
import lombok.Setter;
import net.paramount.auth.entity.Authority;
import net.paramount.common.CommonAssert;
import net.paramount.common.ListUtility;
import net.paramount.domain.entity.general.Catalogue;
import net.paramount.entity.trade.Bank;
import net.paramount.framework.controller.DetailHome;
import net.paramount.global.GlobalConstants;
import net.paramount.global.GlobalServicesRepository;
import net.paramount.service.general.CatalogueService;
import net.paramount.service.trade.BankService;
import net.paramount.utility.FacesUtilities;

/**
 * @author ducbq
 */
@Named
@ViewScoped
public class OrderNoteController extends DetailHome<Authority> implements Serializable {
	private static final long serialVersionUID = -313571612523759120L;

	@Inject
	private BankService businessService;

	@Inject
	private CatalogueService catalogueService;

	@Inject
	private FacesUtilities facesUtilities;

	@Setter
	@Getter
	private Long id;

	@Setter
	@Getter
	private Bank businessObject;

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

	protected String performSave() {
		try {
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
		return "success";
	}

	public String handleSubmit() {
		if (Boolean.TRUE.equals(this.getCreateOther()))
			return "detail";

		return "browse";
	}

	public void clear() {
		businessObject = new Bank();
		id = null;
	}

	public boolean isNew() {
		return businessObject == null || businessObject.getId() == null;
	}

	@Override
	protected void doInit() {
		if (Faces.isAjaxRequest()) {
			return;
		}

		if (CommonAssert.has(id)) {
			businessObject = businessService.getObject(id);
		} else {
			businessObject = new Bank();
		}

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
}
