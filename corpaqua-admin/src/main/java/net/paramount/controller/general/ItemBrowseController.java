package net.paramount.controller.general;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.paramount.common.CommonUtility;
import net.paramount.common.ListUtility;
import net.paramount.css.service.config.ItemService;
import net.paramount.domain.model.Filter;
import net.paramount.entity.general.GeneralCatalogue;
import net.paramount.utility.FacesUtilities;

/**
 * Created by rmpestano on 12/02/17.
 */
@Named(value = "itemBrowser")
@ViewScoped
public class ItemBrowseController implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6412581743995405359L;

	@Inject
  private ItemService businessService;
	private List<GeneralCatalogue> selectedObjects; 
	private List<GeneralCatalogue> businessObjects; 
	private Filter<GeneralCatalogue> bizFilter = new Filter<>(new GeneralCatalogue());
	private List<GeneralCatalogue> filteredObjects;// datatable filteredValue attribute (column filters)

    @Inject
    private FacesUtilities utils;

    private String instantSearch;
    Long id;

    Filter<GeneralCatalogue> filter = new Filter<>(new GeneralCatalogue());

    List<GeneralCatalogue> filteredValue;// datatable filteredValue attribute (column filters)

    @PostConstruct
    public void initDataModel() {
    	try {
    		this.businessObjects = businessService.getObjects();
			} catch (Exception e) {
				e.printStackTrace();
			}
    }

    public void clear() {
        filter = new Filter<GeneralCatalogue>(new GeneralCatalogue());
    }

    public List<String> completeModel(String query) {
        List<String> result = ListUtility.createDataList();//carService.getModels(query);
        return result;
    }

    public void search(String parameter) {
    	System.out.println("Searching parameter: " + parameter);
    	/*if (id == null) {
            throw new BusinessException("Provide Car ID to load");
        }
        selectedCars.add(carService.findById(id));*/
    }

    public void delete() {
    	if (CommonUtility.isNotEmpty(this.selectedObjects)) {
      	for (GeneralCatalogue removalItem :this.selectedObjects) {
      		System.out.println("#" + removalItem.getCode());
      		this.businessObjects.remove(removalItem);
      	}
        utils.addDetailMessage("Objects deleted successfully!");
        this.selectedObjects.clear();
    	}
    }

    public List<GeneralCatalogue> getFilteredValue() {
        return filteredValue;
    }

    public void setFilteredValue(List<GeneralCatalogue> filteredValue) {
        this.filteredValue = filteredValue;
    }

    public Filter<GeneralCatalogue> getFilter() {
        return filter;
    }

    public void setFilter(Filter<GeneralCatalogue> filter) {
        this.filter = filter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

  	public List<GeneralCatalogue> getBusinessObjects() {
  		return businessObjects;
  	}

  	public void setBusinessObjects(List<GeneralCatalogue> businessObjects) {
  		this.businessObjects = businessObjects;
  	}

		public List<GeneralCatalogue> getSelectedObjects() {
			return selectedObjects;
		}

		public void setSelectedObjects(List<GeneralCatalogue> selectedObjects) {
			this.selectedObjects = selectedObjects;
		}

		public Filter<GeneralCatalogue> getBizFilter() {
			return bizFilter;
		}

		public void setBizFilter(Filter<GeneralCatalogue> bizFilter) {
			this.bizFilter = bizFilter;
		}

		public List<GeneralCatalogue> getFilteredObjects() {
			return filteredObjects;
		}

		public void setFilteredObjects(List<GeneralCatalogue> filteredObjects) {
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
