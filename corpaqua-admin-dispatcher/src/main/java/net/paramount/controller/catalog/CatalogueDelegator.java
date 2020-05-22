package net.paramount.controller.catalog;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.primefaces.event.SelectEvent;
import org.springframework.security.access.AccessDeniedException;

import lombok.Getter;
import lombok.Setter;
import net.paramount.auth.entity.Authority;
import net.paramount.auth.service.AuthorityService;
import net.paramount.common.CommonAssert;
import net.paramount.common.ListUtility;
import net.paramount.domain.entity.general.Catalogue;
import net.paramount.service.general.CatalogueService;
import net.paramount.utility.FacesUtilities;

/**
 * @author ducbq
 */
@Named
@ViewScoped
public class CatalogueDelegator implements Serializable {
	private static final long serialVersionUID = 3456150074030583927L;

	@Inject
	private AuthorityService businessService;

	@Inject
	private CatalogueService catalogueService;

	@Inject
	private FacesUtilities facesUtilities;

	@Setter
	@Getter
	private Long id;

	@Setter
	@Getter
	private Authority bizEntity;

	@Setter
	@Getter
	private Authority parent;

	@Setter
	@Getter
	private Catalogue category;

	@Setter
	@Getter
	private List<Catalogue> catalogues = ListUtility.createList();

	public void init() {
		if (Faces.isAjaxRequest()) {
			return;
		}

		if (CommonAssert.has(id)) {
			bizEntity = businessService.getObject(id);
		} else {
			bizEntity = new Authority();
		}

		//Load additional data 
		this.catalogues = catalogueService.getVisibleObjects();
		System.out.println(this.catalogues);
	}

	public void remove() throws IOException {
		if (!facesUtilities.isUserInRole("ROLE_ADMIN")) {
			throw new AccessDeniedException("User not authorized! Only role <b>admin</b> can remove cars.");
		}
		if (CommonAssert.has(bizEntity) && CommonAssert.has(bizEntity.getId())) {
			businessService.remove(bizEntity);
			facesUtilities.addDetailMessage("Business object " + bizEntity.getName() + " removed successfully");
			Faces.getFlash().setKeepMessages(true);
			Faces.redirect("user/car-list.jsf");
		}
	}

	public void save() {
		String msg;
		bizEntity.setCategory(this.category);
		bizEntity.setParent(parent);
		if (bizEntity.getId() == null) {
			msg = "Business object " + bizEntity.getName() + " created successfully";
		} else {
			msg = "Business object " + bizEntity.getName() + " updated successfully";
		}
		businessService.saveOrUpdate(bizEntity);
		facesUtilities.addDetailMessage(msg);
	}

	public void clear() {
		bizEntity = new Authority();
		id = null;
	}

	public boolean isNew() {
		return bizEntity == null || bizEntity.getId() == null;
	}

	public void handleParentSelect(SelectEvent<?> event) { 
		Object item = event.getObject(); 
		if (item instanceof Authority) {
			this.parent = (Authority)item;
		}
		//FacesMessage msg = new FacesMessage("Selected", "Item:" + item); 
	}
}
