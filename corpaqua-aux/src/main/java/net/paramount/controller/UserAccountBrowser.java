package net.paramount.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.paramount.auth.entity.SecurityAccountProfile;
import net.paramount.auth.service.UserAccountService;
import net.paramount.common.CommonUtility;
import net.paramount.common.ListUtility;
import net.paramount.domain.model.Filter;

/**
 * @author ducbq
 */
@Named(value = "userAccountBrowser")
@ViewScoped
public class UserAccountBrowser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3867358136696124359L;
	@Inject
	private UserAccountService businessService;
	private List<SecurityAccountProfile> selectedObjects;
	private List<SecurityAccountProfile> businessObjects;
	private Filter<SecurityAccountProfile> bizFilter = new Filter<>(new SecurityAccountProfile());
	private List<SecurityAccountProfile> filteredObjects;// datatable filteredValue attribute (column filters)

	private String instantSearch;
	Long id;

	Filter<SecurityAccountProfile> filter = new Filter<>(new SecurityAccountProfile());

	List<SecurityAccountProfile> filteredValue;// datatable filteredValue attribute (column filters)

	@PostConstruct
	public void initDataModel() {
		try {
			System.out.println("==>Come to authority browser!");
			this.businessObjects = businessService.getObjects();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clear() {
		filter = new Filter<SecurityAccountProfile>(new SecurityAccountProfile());
	}

	public List<String> completeModel(String query) {
		List<String> result = ListUtility.createDataList();// carService.getModels(query);
		return result;
	}

	public void search(String parameter) {
		System.out.println("Searching parameter: " + parameter);
		/*
		 * if (id == null) { throw new BusinessException("Provide Car ID to load"); }
		 * selectedCars.add(carService.findById(id));
		 */
	}

	public void delete() {
		if (CommonUtility.isNotEmpty(this.selectedObjects)) {
			for (SecurityAccountProfile removalItem : this.selectedObjects) {
				System.out.println("#" + removalItem.getDisplayName());
				this.businessObjects.remove(removalItem);
			}
			this.selectedObjects.clear();
		}
	}

	public List<SecurityAccountProfile> getFilteredValue() {
		return filteredValue;
	}

	public void setFilteredValue(List<SecurityAccountProfile> filteredValue) {
		this.filteredValue = filteredValue;
	}

	public Filter<SecurityAccountProfile> getFilter() {
		return filter;
	}

	public void setFilter(Filter<SecurityAccountProfile> filter) {
		this.filter = filter;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<SecurityAccountProfile> getBusinessObjects() {
		return businessObjects;
	}

	public void setBusinessObjects(List<SecurityAccountProfile> businessObjects) {
		this.businessObjects = businessObjects;
	}

	public List<SecurityAccountProfile> getSelectedObjects() {
		return selectedObjects;
	}

	public void setSelectedObjects(List<SecurityAccountProfile> selectedObjects) {
		this.selectedObjects = selectedObjects;
	}

	public Filter<SecurityAccountProfile> getBizFilter() {
		return bizFilter;
	}

	public void setBizFilter(Filter<SecurityAccountProfile> bizFilter) {
		this.bizFilter = bizFilter;
	}

	public List<SecurityAccountProfile> getFilteredObjects() {
		return filteredObjects;
	}

	public void setFilteredObjects(List<SecurityAccountProfile> filteredObjects) {
		this.filteredObjects = filteredObjects;
	}

	public String getInstantSearch() {
		return instantSearch;
	}

	public void setInstantSearch(String instantSearch) {
		this.instantSearch = instantSearch;
	}

	public void recordsRowSelected(AjaxBehaviorEvent e) {
		System.out.println("recordsRowSelected");
	}
}
