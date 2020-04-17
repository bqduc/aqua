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

import net.paramount.css.service.stock.InventoryService;
import net.paramount.entity.stock.InventoryCore;
import net.paramount.framework.controller.BaseRestController;
import net.paramount.framework.service.IService;

/**
 * @author ducbui
 *
 */
@RestController
@RequestMapping("/api/product")
public class ProductRestController extends BaseRestController<InventoryCore, Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -753628349833341962L;

	@Inject
	private InventoryService businessService;

	@Override
	protected void doCreateBusinessObject(InventoryCore businessObject) {
		log.info("Account Rest::CreateBusinessObject: " + businessObject.getCode());
		businessService.saveOrUpdate(businessObject);
		log.info("Account Rest::CreateBusinessObject is done");
	}

	@RequestMapping(value = "/listAll/", method = RequestMethod.GET)
	public ResponseEntity<List<InventoryCore>> listAll() {
		List<InventoryCore> userObjects = businessService.getObjects();
		if (userObjects.isEmpty()) {
			return new ResponseEntity<List<InventoryCore>>(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<InventoryCore>>(userObjects, HttpStatus.OK);
	}

	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public List<InventoryCore> list(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<InventoryCore> objects = businessService.getObjects();
		System.out.println("COME !");
		return objects;
	}

	@Override
	protected InventoryCore doFetchBusinessObject(Long id) {
		InventoryCore fetchedObject = this.businessService.getObject(id);
		return fetchedObject;
	}

	@Override
	protected IService<InventoryCore, Long> getBusinessService() {
		return this.businessService;
	}
}
