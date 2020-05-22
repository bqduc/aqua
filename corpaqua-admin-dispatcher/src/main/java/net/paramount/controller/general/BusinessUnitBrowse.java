package net.paramount.controller.general;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import net.paramount.common.ListUtility;
import net.paramount.css.service.org.BusinessUnitService;
import net.paramount.domain.model.Filter;
import net.paramount.entity.general.BusinessUnit;
import net.paramount.framework.component.CompCore;

/**
 * @author ducbq
 */
@Named
@ViewScoped
public class BusinessUnitBrowse extends CompCore {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2318856342073765984L;

	@Inject
	private BusinessUnitService businessService;

	@Setter @Getter
	private BusinessUnit selectedObject;

	@Setter @Getter
	private List<BusinessUnit> selectedObjects;

	@Setter @Getter
	private List<BusinessUnit> businessObjects;
	@Setter @Getter
	private List<BusinessUnit> filteredObjects;

	@Setter @Getter
	private String instantSearch;

	@Setter @Getter
	private Long id;

	@Setter @Getter
	private Filter<BusinessUnit> filter = new Filter<>(new BusinessUnit());

	@Setter @Getter
	private List<BusinessUnit> filteredValue;// data table filteredValue attribute (column filters)

	@PostConstruct
	public void initDataModel() {
		try {
			this.businessObjects = businessService.getObjects();
		} catch (Exception e) {
			log.error(e);
		}
	}

	public void clear() {
		filter = new Filter<BusinessUnit>(new BusinessUnit());
	}

	public List<String> completeModel(String query) {
		List<String> result = ListUtility.createDataList();
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
		/*if (CommonUtility.isNotEmpty(this.selectedObjects)) {
			for (BusinessUnit removalItem : this.selectedObjects) {
				System.out.println("#" + removalItem.getDisplayName());
				this.businessObjects.remove(removalItem);
			}
			this.selectedObjects.clear();
		}*/
	}

	public void recordsRowSelected(AjaxBehaviorEvent e) {
		System.out.println("recordsRowSelected");
	}
}
