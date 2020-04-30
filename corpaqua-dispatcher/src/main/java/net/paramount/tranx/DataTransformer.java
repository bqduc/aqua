/**
 * 
 */
package net.paramount.tranx;

import net.paramount.exceptions.AppException;
import net.paramount.framework.entity.RepoEntity;
import net.peaga.domain.base.Repository;

/**
 * @author ducbq
 *
 */
public interface DataTransformer {
  /**
  * This is the marshaling method which transforms all data from proxy object to an entity of a business object.
  * @param targetBusinessObject The target business object that will be transformed to. 
  * @param proxyObject The source proxy object which contained data for transforming from. 
  * @return Transformed business object.
  * @exception AppException On input error.
  * @see AppException
  */
	RepoEntity marshall(final Repository proxyObject, RepoEntity targetBusinessObject) throws AppException;

  /**
  * This is the un-marshaling method which transforms all data from an entity of a business object to a proxy object.
  * @param targetBusinessObject The target business object that will be transformed to. 
  * @param proxyObject The source proxy object which contained data for transforming from. 
  * @return Transformed business object.
  * @exception AppException On input error.
  * @see AppException
  */
	Repository unmarshall(final RepoEntity businessObject, Repository targetProxyObject) throws AppException;
}
