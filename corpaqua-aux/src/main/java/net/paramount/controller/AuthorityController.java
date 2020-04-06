package net.paramount.controller;

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
import net.paramount.framework.controller.DetailHome;
import net.paramount.global.GlobalConstants;
import net.paramount.global.GlobalServicesRepository;
import net.paramount.service.general.CatalogueService;
import net.paramount.utility.FacesUtilities;

/**
 * @author ducbq
 */
@Named(value = "authorityController")
@ViewScoped
public class AuthorityController extends DetailHome<Authority> implements Serializable {
	private static final long serialVersionUID = 6209572587305925873L;

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

	protected String performSave() {
		try {
			bizEntity.setCategory(this.category);
			bizEntity.setParent(parent);
			StringBuilder msg = new StringBuilder("Business object ").append(bizEntity.getName());
			if (bizEntity.getId() == null) {
				msg.append(" created successfully");
			} else {
				msg.append(" updated successfully");
			}

			businessService.saveOrUpdate(bizEntity);
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

	@Override
	protected void doInit() {
		if (Faces.isAjaxRequest()) {
			return;
		}

		if (CommonAssert.has(id)) {
			bizEntity = businessService.getObject(id);
		} else {
			bizEntity = new Authority();
		}

		this.parent = bizEntity.getParent();
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

	/*@Override
	protected Authority buildNoneObject() {
		return (Authority)GlobalServicesRepository.builder().build().buildNoneObject(Authority.builder().build(), ListUtility.createMap("id", GlobalConstants.NONE_OBJECT_ID, "name", GlobalConstants.NONE_OBJECT_CODE, "displayName", GlobalConstants.NONE_OBJECT_NAME));
	}*/
}
