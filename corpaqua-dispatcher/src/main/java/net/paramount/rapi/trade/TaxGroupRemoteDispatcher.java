package net.paramount.rapi.trade;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.paramount.entity.general.TaxGroup;
import net.paramount.exceptions.AppException;
import net.paramount.framework.controller.RestCoreController;
import net.paramount.framework.service.IService;
import net.paramount.service.trade.TaxGroupService;
import net.paramount.tranx.DataTransformer;
import net.peaga.domain.trade.TaxGroupProxy;

/**
 * @author ducbq
 */
@RestController
@Controller
@RequestMapping(value = net.paramount.common.CommonConstants.REST_API + "taxGroup")
public class TaxGroupRemoteDispatcher extends RestCoreController<TaxGroupProxy, Long> {
	private static final long serialVersionUID = -8578417652190400110L;

	@Inject 
	private TaxGroupService businessService;

	@Inject 
	private DataTransformer simpleDataObjectTransformer;

	@Override
	protected TaxGroupProxy doFetchBusinessObject(Long id) {
		TaxGroup fetchedBizObject = this.businessService.getObject(id);
		TaxGroupProxy proxyObject = new TaxGroupProxy();
		try {
			this.simpleDataObjectTransformer.unmarshall(fetchedBizObject, proxyObject);
		} catch (AppException e) {
			log.error(e);
		}
		return proxyObject;
	}

	@Override
	protected void doCreateBusinessObject(TaxGroupProxy proxyObject) {
		TaxGroup newBizObject = new TaxGroup();
		try {
			this.simpleDataObjectTransformer.marshall(proxyObject, newBizObject);
			this.businessService.saveOrUpdate(newBizObject);
		} catch (AppException e) {
			log.error(e);
		}
	}

	@Override
	protected IService<?, Long> getBusinessService() {
		return this.businessService;
	}
}
