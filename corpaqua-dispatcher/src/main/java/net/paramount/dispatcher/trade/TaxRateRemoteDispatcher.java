package net.paramount.dispatcher.trade;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.paramount.entity.trade.TaxRate;
import net.paramount.exceptions.AppException;
import net.paramount.framework.controller.RestCoreController;
import net.paramount.framework.service.IService;
import net.paramount.service.trade.TaxRateService;
import net.paramount.tranx.DataTransformer;
import net.peaga.domain.trade.TaxRateProxy;

/**
 * @author ducbq
 */
@RestController
@Controller
@RequestMapping(value = net.paramount.common.CommonConstants.REST_API + "taxRate")
public class TaxRateRemoteDispatcher extends RestCoreController<TaxRateProxy, Long> {
	private static final long serialVersionUID = 4157144967294064838L;

	@Inject 
	private TaxRateService businessService;

	@Inject 
	private DataTransformer simpleDataObjectTransformer;

	@Override
	protected TaxRateProxy doFetchBusinessObject(Long id) {
		TaxRate fetchedBizObject = this.businessService.getObject(id);
		TaxRateProxy proxyObject = new TaxRateProxy();
		try {
			this.simpleDataObjectTransformer.transformToProxy(fetchedBizObject, proxyObject);
		} catch (AppException e) {
			log.error(e);
		}
		return proxyObject;
	}

	@Override
	protected TaxRateProxy doCreateBusinessObject(TaxRateProxy proxyObject) {
		TaxRate newBizObject = new TaxRate();
		try {
			this.simpleDataObjectTransformer.transformToBusiness(proxyObject, newBizObject);
			this.businessService.saveOrUpdate(newBizObject);
			proxyObject.setId(newBizObject.getId());
		} catch (AppException e) {
			log.error(e);
		}
		return proxyObject;
	}

	@Override
	protected IService<?, Long> getBusinessService() {
		return this.businessService;
	}
}
