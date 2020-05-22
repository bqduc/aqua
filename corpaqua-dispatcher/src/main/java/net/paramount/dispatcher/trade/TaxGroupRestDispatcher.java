package net.paramount.dispatcher.trade;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.paramount.common.BeanUtility;
import net.paramount.common.ListUtility;
import net.paramount.domain.model.SearchCondition;
import net.paramount.entity.general.TaxGroup;
import net.paramount.exceptions.AppException;
import net.paramount.framework.controller.RestCoreController;
import net.paramount.framework.entity.RepoEntity;
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
public class TaxGroupRestDispatcher extends RestCoreController<TaxGroupProxy, Long> {
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
			this.simpleDataObjectTransformer.transformToProxy(fetchedBizObject, proxyObject);
		} catch (AppException e) {
			log.error(e);
		}
		return proxyObject;
	}

	@Override
	protected TaxGroupProxy doCreateBusinessObject(TaxGroupProxy proxyObject) {
		TaxGroup newBizObject = new TaxGroup();
		TaxGroup parent = null;
		try {
			this.simpleDataObjectTransformer.transformToBusiness(proxyObject, newBizObject);
			if (proxyObject.getParent() != null) {
				parent = this.businessService.getObject(proxyObject.getParent().getId());
				newBizObject.setParent(parent);
			}
			this.businessService.saveOrUpdate(newBizObject);
			proxyObject.setId(newBizObject.getId());
		} catch (Exception e) {
			log.error(e);
		}
		return proxyObject;
	}

	@Override
	protected TaxGroupProxy doUpdateBusinessObject(TaxGroupProxy proxyObject) {
		TaxGroup bizObject = this.businessService.getObject(proxyObject.getId());
		BeanUtility.getInstance().copyBeanData(proxyObject, bizObject, new String[] {"id", "parent"});
		//transformParents(bizObject, proxyObject);
		if (null != proxyObject.getParent()) {
			bizObject.setParent(this.businessService.getObject(proxyObject.getParent().getId()));
		}

		this.businessService.saveOrUpdate(bizObject);
		return proxyObject;
	}

	@Override
	protected IService<?, Long> getBusinessService() {
		return this.businessService;
	}

	@Override
	protected List<TaxGroupProxy> searchBusinessObjects(SearchCondition searchCondition) {
		List<TaxGroupProxy> list = ListUtility.createList();
		TaxGroup fetchedBizObject = this.businessService.getByName(searchCondition.getName()).orElse(null);
		if (null != fetchedBizObject) {
			TaxGroupProxy taxGroupProxy = TaxGroupProxy.builder().build();
			try {
				this.simpleDataObjectTransformer.transformToProxy(fetchedBizObject, taxGroupProxy);
			} catch (AppException e) {
				e.printStackTrace();
			}
			list.add(taxGroupProxy);
		}
		return list;
	}

	protected RepoEntity transformParents(TaxGroup businessObject, TaxGroupProxy proxyObject) {
		TaxGroup parent = null;
		while (null != businessObject && null != proxyObject && null != proxyObject.getParent()) {
			parent = this.businessService.getObject(proxyObject.getParent().getId());
			businessObject.setParent(parent);

			businessObject = businessObject.getParent();
			proxyObject = proxyObject.getParent();
		}
		return businessObject;
	}
}
