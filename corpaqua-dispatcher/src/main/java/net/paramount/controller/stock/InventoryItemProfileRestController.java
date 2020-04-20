package net.paramount.controller.stock;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.nep.facade.ProductProfile;
import net.paramount.css.service.stock.InventoryService;
import net.paramount.domain.facade.InventoryItemProfile;
import net.paramount.framework.controller.RestCoreController;
import net.paramount.framework.service.IService;

/**
 * @author ducbq
 */
@RestController
@Controller
@RequestMapping(value = net.paramount.common.CommonConstants.REST_API + "inventory")
public class InventoryItemProfileRestController extends RestCoreController<InventoryItemProfile, Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8372603137306173654L;

	@Inject 
	private InventoryService businessService;

	@Override
	protected InventoryItemProfile doFetchBusinessObject(Long id) {
		ProductProfile productProfile = this.businessService.getProfile(id);
		InventoryItemProfile fetchedBizObject = new InventoryItemProfile(productProfile);
		return fetchedBizObject;
	}

	@Override
	protected IService<?, Long> getBusinessService() {
		return this.businessService;
	}
}
