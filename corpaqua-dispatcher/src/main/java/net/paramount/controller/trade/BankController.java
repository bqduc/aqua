package net.paramount.controller.trade;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.springframework.security.access.AccessDeniedException;

import lombok.Getter;
import lombok.Setter;
import net.paramount.common.CommonAssert;
import net.paramount.entity.trade.Bank;
import net.paramount.framework.controller.DetailHome;
import net.paramount.service.trade.BankService;
import net.paramount.utility.FacesUtilities;

/**
 * @author ducbq
 */
@Named
@ViewScoped
public class BankController extends DetailHome<Bank> implements Serializable {
	private static final long serialVersionUID = 3833722013154668983L;

	@Inject
	private BankService businessService;

	@Inject
	private FacesUtilities facesUtilities;

	@Setter
	@Getter
	private Long id;

	@Setter
	@Getter
	private Bank businessObject;

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

	public void save() {
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
	}
}
