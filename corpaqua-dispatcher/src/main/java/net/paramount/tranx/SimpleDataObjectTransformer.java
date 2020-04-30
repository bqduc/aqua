/**
 * 
 */
package net.paramount.tranx;

import java.lang.reflect.InvocationTargetException;

import javax.inject.Named;

import org.springframework.stereotype.Component;

import net.paramount.common.CommonBeanUtils;
import net.paramount.exceptions.AppException;
import net.paramount.framework.entity.RepoEntity;
import net.peaga.domain.base.Repository;

/**
 * @author ducbq
 *
 */
@Named
@Component
public class SimpleDataObjectTransformer implements DataTransformer {
	@Override
	public RepoEntity marshall(final Repository proxyObject, RepoEntity targetBusinessObject) throws AppException {
		try {
			CommonBeanUtils.copyBean(proxyObject, targetBusinessObject);
		} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
			throw new AppException(e);
		}
		return targetBusinessObject;
	}

	@Override
	public Repository unmarshall(final RepoEntity businessObject, Repository targetProxyObject) throws AppException {
		try {
			CommonBeanUtils.copyBean(businessObject, targetProxyObject);
		} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
			throw new AppException(e);
		}
		return targetProxyObject;
	}
}
