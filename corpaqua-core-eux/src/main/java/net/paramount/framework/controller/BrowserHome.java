/**
 * 
 */
package net.paramount.framework.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import lombok.Getter;
import lombok.Setter;
import net.paramount.common.ListUtility;
import net.paramount.framework.model.FilterBase;

/**
 * @author bqduc
 *
 */
public abstract class BrowserHome<E, F extends FilterBase> extends Home <E, F> {
	private static final long serialVersionUID = -2784847685814774630L;

	@Setter
	@Getter
	private F filterModel; 

	@Setter
	@Getter
	private String instantSearch;

	@Setter
	@Getter
	private List<E> businessObjects = ListUtility.createList();

	@Setter
	@Getter
	private List<E> selectedObjects = ListUtility.createList();

	@Setter
	@Getter
  private List<E> filteredObjects = ListUtility.createList();

	protected abstract List<E> doGetBusinessObjects();

	@PostConstruct
  void init() {
  	this.filterModel = createFilterModel();

  	//Loading mandatory business objects
  	this.businessObjects = this.doGetBusinessObjects();
  }

  public F createFilterModel() {
  	return null;
  }

}
