package net.paramount.controller.stock;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.access.AccessDeniedException;

import lombok.Getter;
import lombok.Setter;
import net.nep.facade.ProductProfile;
import net.paramount.common.CommonAssert;
import net.paramount.common.ListUtility;
import net.paramount.css.service.stock.InventoryService;
import net.paramount.css.service.system.SequenceManager;
import net.paramount.domain.entity.Attachment;
import net.paramount.domain.entity.general.Catalogue;
import net.paramount.entity.stock.InventoryCore;
import net.paramount.entity.stock.InventoryImage;
import net.paramount.framework.controller.DetailHome;
import net.paramount.global.GlobalConstants;
import net.paramount.global.GlobalServicesRepository;
import net.paramount.service.general.CatalogueService;

/**
 * @author ducbq
 */
@Named
@ViewScoped
//@RequestScope
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class InventoryController extends DetailHome<InventoryCore> implements Serializable {
	private static final long serialVersionUID = -4252723578792859073L;

	@Inject
	private InventoryService businessService;

	@Inject
	private CatalogueService catalogueService;

	@Inject
	private SequenceManager sequenceManager;
	
	@Setter
	@Getter
	private Long id;

	@Setter
	@Getter
	private InventoryCore businessObject;

	@Setter
	@Getter
	private InventoryCore parent;

	@Setter
	@Getter
	private Catalogue category;

	@Setter
	@Getter
	private List<Catalogue> catalogues = ListUtility.createList();

	@Setter
	@Getter
	private UploadedFile uploadedFile;

	@Setter
	@Getter
	private ProductProfile bizObject;

	@Setter
	@Getter
	private UploadedFiles imageFiles;
	
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
		businessObject = new InventoryCore();
		id = null;
	}

	public boolean isNew() {
		return businessObject == null || businessObject.getId() == null;
	}

	@Override
	protected void onInit() {
		/*if (Faces.isAjaxRequest()) {
			return;
		}*/

		if (CommonAssert.has(id)) {
			//businessObject = businessService.getObject(id);
			this.bizObject = this.businessService.getProfile(id);
		} else if (null==this.bizObject){
			//businessObject = new ProductCore();
			String serial = this.sequenceManager.generateNewSerialNumber("INV");
			InventoryCore productCore = InventoryCore.builder()
					.code(serial)
					.build();
			this.bizObject = ProductProfile.builder()
					.core(productCore)
					.serial(serial)
					.build();
		}

		this.parent = this.bizObject.getCore().getParent();
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
			this.consolidateInventoryData();
			StringBuilder msg = new StringBuilder("Business object ").append(this.bizObject.getCore().getName());
			if (this.bizObject.getCore().getId() == null) {
				msg.append(" created successfully");
			} else {
				msg.append(" updated successfully");
			}

			businessService.saveProfile(this.bizObject);

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

	public void handleUpload(FileUploadEvent event) {
		InputStream uploadInputStream = null;
		byte[] buffer = null;
		try {
			uploadInputStream = event.getFile().getInputStream();
			buffer = new byte[uploadInputStream.available()];
			uploadInputStream.read(buffer);
		} catch (IOException e) {
			log.error(e);
		}

		this.bizObject.addAttachment(
  		Attachment.builder()
  		.mimetype(event.getFile().getContentType())
  		.name(event.getFile().getFileName())
  		.data(event.getFile().getContent())
  		.build()
		);
		/*this.bizObject.getCore().addImage(
				InventoryImage.builder()
				.owner(this.bizObject.getCore())
				.imageBuffer(buffer)
				.build()
				);*/
	}
 
	public String getImageContentsAsBase64() {
    return Base64.getEncoder().encodeToString(this.uploadedFile.getContent());
	}

	private void consolidateInventoryData() {
		this.bizObject.getCore().setParent(this.parent);
		//Consolidate data of images
		/*Iterator<Attachment> itr = this.bizObject.getImages().iterator();
		this.bizObject.getCore().setImageBuffer1(itr.next().getData());
		
		for (Attachment attachment :this.bizObject.getImages()) {
			//this.bizObject.getCore().addImage(attachment);
		}*/
	}

	/*public StreamedContent getInventoryImage(int imageId) throws IOException {
		//FacesContext context = FacesContext.getCurrentInstance();

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		} else {
			// Reading image from database assuming that product image (bytes)
			// of product id I1 which is already stored in the database.
			InventoryImage inventoryImage = null;
			try {
				inventoryImage = this.bizObject.getInventoryImages().get(imageId);
			} catch (Exception e) {
				// TODO: handle exception
			}
			if (null != inventoryImage) {
				byte[] image = inventoryImage.getImageBuffer();
				return DefaultStreamedContent.builder().contentType(inventoryImage.getContentType())
						.stream(() -> new ByteArrayInputStream(image))
						.build();
			} else {
				return new DefaultStreamedContent();
			}
			//return new DefaultStreamedContent(new ByteArrayInputStream(image), "image/png");

		}
	}*/
}
