package net.paramount.dispatcher.general;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.paramount.common.ListUtility;
import net.paramount.css.service.general.CurrencyService;
import net.paramount.domain.model.SearchCondition;
import net.paramount.entity.general.Currency;
import net.paramount.exceptions.AppException;
import net.paramount.framework.controller.RestCoreController;
import net.paramount.framework.service.IService;
import net.paramount.tranx.DataTransformer;
import net.peaga.domain.general.CurrencyProxy;

/**
 * @author ducbq
 */
@RestController
@RequestMapping(value = net.paramount.common.CommonConstants.REST_API + "currency")
public class CurrencyDispatcher extends RestCoreController<CurrencyProxy, Long> {
	private static final long serialVersionUID = -1067962556914244335L;

	@Inject 
	private CurrencyService businessService;

	@Inject 
	private DataTransformer simpleDataObjectTransformer;

	@Override
	protected CurrencyProxy doFetchBusinessObject(Long id) {
		Currency fetchedBizObject = this.businessService.getObject(id);
		CurrencyProxy proxyObject = new CurrencyProxy();
		try {
			this.simpleDataObjectTransformer.transformToProxy(fetchedBizObject, proxyObject);
		} catch (AppException e) {
			log.error(e);
		}
		return proxyObject;
	}

	@Override
	protected CurrencyProxy doCreateBusinessObject(CurrencyProxy proxyObject) {
		Currency newBizObject = new Currency();
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

	@Override
	protected List<CurrencyProxy> searchBusinessObjects(SearchCondition searchCondition) {
		List<CurrencyProxy> list = ListUtility.createList();
		Currency fetchedBizObject = this.businessService.getByCode(searchCondition.getCode()).orElse(null);
		if (null != fetchedBizObject) {
			CurrencyProxy businessObjectProxy = CurrencyProxy.builder().build();
			try {
				this.simpleDataObjectTransformer.transformToProxy(fetchedBizObject, businessObjectProxy);
			} catch (AppException e) {
				e.printStackTrace();
			}
			list.add(businessObjectProxy);
		}
		return list;
	}

}
