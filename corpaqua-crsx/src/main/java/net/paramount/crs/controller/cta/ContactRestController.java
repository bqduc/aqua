/**
 * 
 */
package net.paramount.crs.controller.cta;

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

import net.paramount.common.CommonUtility;
import net.paramount.crs.service.ContactProfileService;
import net.paramount.css.service.contact.ContactService;
import net.paramount.entity.contact.CTAContact;
import net.paramount.entity.contact.ContactCore;
import net.paramount.entity.contact.ContactProfile;
import net.paramount.framework.controller.BaseRestController;
import net.paramount.framework.service.IService;

/**
 * @author ducbui
 *
 */
@RestController
@RequestMapping("/api/contact")
public class ContactRestController extends BaseRestController<CTAContact, Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3865623310245311419L;

	@Inject
	private ContactService businessService;

	@Inject
	private ContactProfileService businessServiceProfile;

	@Override
	protected void doCreateBusinessObject(CTAContact businessObject) {
		log.info("Account Rest::CreateBusinessObject: " + businessObject.getCode());
		businessService.saveOrUpdate(businessObject);
		log.info("Account Rest::CreateBusinessObject is done");
	}

	@RequestMapping(value = "/listAll/", method = RequestMethod.GET)
	public ResponseEntity<List<CTAContact>> listAll() {
		List<CTAContact> userObjects = businessService.getObjects();
		if (userObjects.isEmpty()) {
			return new ResponseEntity<List<CTAContact>>(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<CTAContact>>(userObjects, HttpStatus.OK);
	}

	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public List<CTAContact> list(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<CTAContact> objects = businessService.getObjects();
		if (CommonUtility.isEmpty(objects)) {
			initDummyData();
			objects = businessService.getObjects();
		}
		System.out.println("COME !");
		return objects;
	}

	private void initDummyData() {
		CTAContact account = CTAContact.builder()
				.code("CC0191019")
				.accountName("Dummy Contact Clazz")
				.title("Dummy Class")
				.info("This is a dummy entity. ")
				.build();
		doCreateBusinessObject(account);
	}

	@RequestMapping(path = "/listProfiles", method = RequestMethod.GET)
	public List<ContactProfile> listObjects(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<ContactProfile> objects = businessServiceProfile.getObjects();
		if (CommonUtility.isEmpty(objects)) {
			initDummyProfilesData();
			objects = businessServiceProfile.getObjects();
		}
		System.out.println("COME !");
		return objects;
	}

	private void initDummyProfilesData() {
		ContactCore account = ContactCore.builder()
				.code("CP0191019")
				.firstName("Duc")
				.lastName("Bui Quy")
				.title("Application Developer")
				.build();
		//this.businessServiceProfile.saveOrUpdate(account);
	}

	@Override
	protected IService<CTAContact, Long> getBusinessService() {
		return this.businessService;
	}
}
