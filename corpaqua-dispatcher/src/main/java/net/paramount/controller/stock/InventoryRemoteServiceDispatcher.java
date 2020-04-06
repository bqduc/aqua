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

import net.paramount.css.service.stock.ProductCoreService;
import net.paramount.entity.stock.ProductCore;
import net.paramount.framework.controller.BaseRestController;
import net.paramount.framework.service.IService;

/**
 * @author ducbui
 *
 */
@RestController
@RequestMapping("/api/inventory")
public class InventoryRemoteServiceDispatcher extends BaseRestController<ProductCore, Long> {
	private static final long serialVersionUID = -2715466758043807257L;

	@Inject
	private ProductCoreService businessService;

	@Override
	protected void doCreateBusinessObject(ProductCore businessObject) {
		log.info("Account Rest::CreateBusinessObject: " + businessObject.getCode());
		businessService.saveOrUpdate(businessObject);
		log.info("Account Rest::CreateBusinessObject is done");
	}

	@RequestMapping(value = "/listAll/", method = RequestMethod.GET)
	public ResponseEntity<List<ProductCore>> listAll() {
		List<ProductCore> userObjects = businessService.getObjects();
		if (userObjects.isEmpty()) {
			return new ResponseEntity<List<ProductCore>>(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<ProductCore>>(userObjects, HttpStatus.OK);
	}

	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public List<ProductCore> list(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<ProductCore> objects = businessService.getObjects();
		System.out.println("COME !");
		return objects;
	}

	@Override
	protected ProductCore doFetchBusinessObject(Long id) {
		ProductCore fetchedObject = this.businessService.getObject(id);
		return fetchedObject;
	}

	@Override
	protected IService<ProductCore, Long> getBusinessService() {
		return this.businessService;
	}
}
