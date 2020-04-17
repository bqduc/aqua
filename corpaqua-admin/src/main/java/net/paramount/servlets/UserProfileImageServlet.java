/**
 * 
 */
package net.paramount.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.paramount.auth.entity.SecurityAccountProfile;
import net.paramount.auth.service.UserAccountService;
import net.paramount.common.CommonUtility;
import net.paramount.css.service.stock.InventoryService;
import net.paramount.framework.logging.LogService;
import net.paramount.framework.servlet.ServletCore;

/**
 * @author ducbq
 *
 */
public class UserProfileImageServlet extends ServletCore {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1741403523440066873L;
	
	private UserAccountService businessService;

	@Override
	protected void onInit() throws ServletException {
		this.businessService = (UserAccountService)this.getBean(InventoryService.class);
		this.log = (LogService)this.getBean(LogService.class);
	}

	@Override
	protected void onGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String userProfileIdParam = request.getParameter("userProfileId");
			if (null == this.businessService) {
				businessService = (UserAccountService)this.getBean(UserAccountService.class);
			}

			SecurityAccountProfile businessObject = businessService.getObject(CommonUtility.parseLong(userProfileIdParam));
			if (null != businessObject && null != businessObject.getAttachment()) {
				response.getOutputStream().write(businessObject.getAttachment().getData());
			}
		} catch (Exception e) {
			log.error(e);
		}
	}
}
