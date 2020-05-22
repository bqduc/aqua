package net.paramount.dispatcher.trade;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.paramount.common.BeanUtility;
import net.paramount.common.CommonUtility;
import net.paramount.common.ListUtility;
import net.paramount.domain.model.SearchCondition;
import net.paramount.entity.trade.Tax;
import net.paramount.exceptions.AppException;
import net.paramount.framework.controller.RestCoreController;
import net.paramount.framework.service.IService;
import net.paramount.service.trade.TaxService;
import net.paramount.tranx.DataTransformer;
import net.peaga.domain.trade.TaxProxy;

/**
 * @author ducbq
 */
@RestController
@Controller
@RequestMapping(value = net.paramount.common.CommonConstants.REST_API + "tax")
public class TaxRemoteDispatcher extends RestCoreController<TaxProxy, Long> {
	private static final long serialVersionUID = 8204722123359040866L;

	@Inject 
	private TaxService businessService;

	@Inject 
	private DataTransformer taxDataObjectTransformer;

	@Override
	protected TaxProxy doFetchBusinessObject(Long id) {
		Tax fetchedBizObject = this.businessService.getObject(id);
		TaxProxy proxyObject = new TaxProxy();
		try {
			this.taxDataObjectTransformer.transformToProxy(fetchedBizObject, proxyObject);
		} catch (AppException e) {
			log.error(e);
		}
		return proxyObject;
	}

	@Override
	protected TaxProxy doCreateBusinessObject(TaxProxy proxyObject) {
		Tax newBizObject = new Tax();
		try {
			this.taxDataObjectTransformer.transformToBusiness(proxyObject, newBizObject);
			this.businessService.saveOrUpdate(newBizObject);
			proxyObject.setId(newBizObject.getId());
		} catch (AppException e) {
			log.error(e);
		}
		return proxyObject;
	}

	@Override
	protected TaxProxy doUpdateBusinessObject(TaxProxy updatedClientObject) {
		Tax bizObject = this.businessService.getObject(updatedClientObject.getId());
		BeanUtility.getInstance().copyBeanData(updatedClientObject, bizObject, new String[] {"id", "parent", "group"});
		if (null != updatedClientObject.getParent()) {
			bizObject.setParent(this.businessService.getObject(updatedClientObject.getParent().getId()));
		}

		this.businessService.saveOrUpdate(bizObject);
		return updatedClientObject;
	}

	@Override
	protected IService<?, Long> getBusinessService() {
		return this.businessService;
	}

	@Override
	protected List<TaxProxy> searchBusinessObjects(SearchCondition searchCondition) {
		List<TaxProxy> list = ListUtility.createList();
		String keyword = CommonUtility.isNotEmpty(searchCondition.getName())?searchCondition.getName():searchCondition.getCode();
		List<Tax> foundBusinessObjects = this.businessService.search(keyword);
		TaxProxy proxyObject = null;
		for (Tax bizObject :foundBusinessObjects) {
			proxyObject = new TaxProxy();
			try {
				this.taxDataObjectTransformer.transformToProxy(bizObject, proxyObject);
			} catch (AppException e) {
				log.error(e);
			}
			list.add(proxyObject);
		}
		return list;
	}
}
