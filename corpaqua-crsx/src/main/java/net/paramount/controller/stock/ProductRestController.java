/**
 * 
 */
package net.paramount.controller.stock;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.paramount.css.service.stock.ProductProfileService;
import net.paramount.entity.stock.ProductProfile;
import net.paramount.framework.controller.BaseRestController;
import net.paramount.framework.service.IService;

/**
 * @author ducbui
 *
 */
@RestController
@RequestMapping("/api/product")
public class ProductRestController extends BaseRestController<ProductProfile, Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -753628349833341962L;

	@Inject
	private ProductProfileService businessService;

	@Override
	protected void doCreateBusinessObject(ProductProfile businessObject) {
		log.info("Account Rest::CreateBusinessObject: " + businessObject.getOwner().getCode());
		businessService.saveOrUpdate(businessObject);
		log.info("Account Rest::CreateBusinessObject is done");
	}

	@RequestMapping(value = "/listAll/", method = RequestMethod.GET)
	public ResponseEntity<List<ProductProfile>> listAll() {
		List<ProductProfile> userObjects = businessService.getObjects();
		if (userObjects.isEmpty()) {
			return new ResponseEntity<List<ProductProfile>>(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<ProductProfile>>(userObjects, HttpStatus.OK);
	}

	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public List<ProductProfile> list(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<ProductProfile> objects = businessService.getObjects();
		System.out.println("COME !");
		return objects;
	}

	@Override
	protected ProductProfile doFetchBusinessObject(Long id) {
		ProductProfile fetchedObject = this.businessService.getObject(id);
		return fetchedObject;
	}

	@Override
	protected IService<ProductProfile, Long> getBusinessService() {
		return this.businessService;
	}
}
