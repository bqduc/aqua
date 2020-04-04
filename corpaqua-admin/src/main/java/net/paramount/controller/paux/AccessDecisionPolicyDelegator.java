package net.paramount.controller.paux;

import static com.github.adminfaces.template.util.Assert.has;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;

import com.github.adminfaces.template.exception.AccessDeniedException;

import lombok.Getter;
import lombok.Setter;
import net.paramount.auth.entity.AccessDecisionAuthority;
import net.paramount.auth.entity.AccessDecisionPolicy;
import net.paramount.auth.entity.Authority;
import net.paramount.auth.service.AccessDecisionPolicyService;
import net.paramount.auth.service.AuthorityService;
import net.paramount.common.ListUtility;
import net.paramount.utility.FacesUtilities;

/**
 * @author ducbq
 */
@Named
@ViewScoped
public class AccessDecisionPolicyDelegator implements Serializable {
	private static final long serialVersionUID = 8999272230254126909L;

	@Inject
	private AuthorityService authorityService;

	@Inject
	private AccessDecisionPolicyService businessService;

	@Inject
	private FacesUtilities utils;

	@Setter
	@Getter
	private Long id;

	@Setter
	@Getter
	private AccessDecisionPolicy businessObject;

	@Setter
	@Getter
	private List<Authority> grantedAuthorities = ListUtility.createList();

	public void init() {
		if (Faces.isAjaxRequest()) {
			return;
		}

		if (has(id)) {
			this.businessObject = businessService.getObject(Long.valueOf(id));
		} else {
			this.businessObject = AccessDecisionPolicy.builder().build();
		}

		for (AccessDecisionAuthority ada :this.businessObject.getAccessDecisionAuthorities()) {
			grantedAuthorities.add(ada.getAuthority());
		}
	}

	public void remove() throws IOException {
		if (!utils.isUserInRole("ROLE_ADMIN")) {
			throw new AccessDeniedException("User not authorized! Only role <b>admin</b> can remove cars.");
		}
	
		if (has(businessObject) && has(businessObject.getId())) {
			// carService.remove(car);
			utils.addDetailMessage("Car " + businessObject.getAccessPattern() + " removed successfully");
			Faces.getFlash().setKeepMessages(true);
			Faces.redirect("user/car-list.jsf");
		}
	}

	public void save() {
		businessService.saveOrUpdate(this.businessObject);
		StringBuilder msg = new StringBuilder("Access decision policy ")
				.append(this.businessObject.getAccessPattern())
				.append(" created successfully")
				;
		utils.addDetailMessage(msg.toString());
	}

	public void clear() {
		this.businessObject = AccessDecisionPolicy.builder().build();
		id = null;
	}

	public boolean isNew() {
		return this.businessObject == null || this.businessObject.getId() == null;
	}

	public List<Authority> getAuthorities(){
		return authorityService.getObjects();
	}
}
