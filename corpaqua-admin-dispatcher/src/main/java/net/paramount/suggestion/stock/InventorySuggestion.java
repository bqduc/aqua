package net.paramount.suggestion.stock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import net.paramount.common.ListUtility;
import net.paramount.css.service.stock.InventoryService;
import net.paramount.entity.stock.InventoryCore;

@Named(value="inventorySuggestion")
@ViewScoped
public class InventorySuggestion implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3953585001633751748L;

	private InventoryCore item;
	private List<InventoryCore> selectedItems;

	@Inject
	private InventoryService businessService;

	public List<String> completeText(String query) {
		List<String> results = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			results.add(query + i);
		}

		return results;
	}

	public List<InventoryCore> completeItem(String query) {
		List<InventoryCore> allItems = businessService.getObjects();
		List<InventoryCore> filteredItems = ListUtility.createDataList();

		String lowerCaseQuery = query.toLowerCase();
		allItems.stream().filter(itr -> itr.getName().toLowerCase().contains(lowerCaseQuery)).forEach(filteredItems::add);
		return filteredItems;
	}

	public List<InventoryCore> completeItemContains(String query) {
		List<InventoryCore> allItems = businessService.getObjects();
		List<InventoryCore> filteredItems = ListUtility.createDataList();

		String lowerCaseQuery = query.toLowerCase();
		allItems.stream().filter(itr -> itr.getName().toLowerCase().contains(lowerCaseQuery)).forEach(filteredItems::add);
		return filteredItems;
	}

	public void onItemSelect(SelectEvent<?> event) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("ProductCore Selected", event.getObject().toString()));
	}

	public List<InventoryCore> getSelectedItems() {
		return selectedItems;
	}

	public void setSelectedItems(List<InventoryCore> selectedItems) {
		this.selectedItems = selectedItems;
	}

	public char getItemGroup(InventoryCore item) {
		return item.getName().charAt(0);
	}

	public InventoryCore getItem() {
		return item;
	}

	public void setItem(InventoryCore item) {
		this.item = item;
	}
}
