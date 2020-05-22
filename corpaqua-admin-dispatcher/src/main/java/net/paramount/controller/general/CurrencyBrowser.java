package net.paramount.controller.general;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import net.paramount.common.ListUtility;
import net.paramount.css.service.general.CurrencyService;
import net.paramount.domain.model.Filter;
import net.paramount.entity.general.Currency;

/**
 * @author ducbq
 */
@Named
@ViewScoped
public class CurrencyBrowser implements Serializable {
	private static final long serialVersionUID = -5083260576154602450L;

	@Inject
	private CurrencyService businessService;

	@Setter @Getter
	private Currency selectedObject;

	@Setter @Getter
	private List<Currency> selectedObjects;

	@Setter @Getter
	private List<Currency> businessObjects;
	//private Filter<Currency> bizFilter = new Filter<>(new Currency());
	@Setter @Getter
	private List<Currency> filteredObjects;

	@Setter @Getter
	private String instantSearch;

	Long id;

	@Setter @Getter
	private Filter<Currency> filter = new Filter<>(new Currency());

	@Setter @Getter
	private List<Currency> filteredValue;// data table filteredValue attribute (column filters)

	@PostConstruct
	public void initDataModel() {
		try {
			this.businessObjects = businessService.getObjects();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clear() {
		filter = new Filter<Currency>(new Currency());
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
			for (Currency removalItem : this.selectedObjects) {
				System.out.println("#" + removalItem.getDisplayName());
				this.businessObjects.remove(removalItem);
			}
			this.selectedObjects.clear();
		}*/
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void recordsRowSelected(AjaxBehaviorEvent e) {
		System.out.println("recordsRowSelected");
	}
}
