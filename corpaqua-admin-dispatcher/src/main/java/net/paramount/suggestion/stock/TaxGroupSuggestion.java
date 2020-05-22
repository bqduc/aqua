package net.paramount.suggestion.stock;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import lombok.Getter;
import lombok.Setter;
import net.paramount.entity.general.TaxGroup;
import net.paramount.service.trade.TaxGroupService;

@Named
@ViewScoped
public class TaxGroupSuggestion implements Serializable {
	private static final long serialVersionUID = -3310954515372349996L;

	@Getter @Setter
	private TaxGroup item;

	@Getter @Setter
	private List<TaxGroup> selectedItems;

	@Inject
	private TaxGroupService businessService;

	public List<TaxGroup> completeItem(String keyword) {
		List<TaxGroup> fetchedItems = businessService.search(keyword);
		return fetchedItems;
	}

	public List<TaxGroup> completeItemContains(String keyword) {
		List<TaxGroup> fetchedItems = businessService.search(keyword);
		return fetchedItems;
	}

	public void onItemSelect(SelectEvent<?> event) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("TaxGroup Selected", event.getObject().toString()));
	}

	public char getItemGroup(TaxGroup item) {
		return item.getName().charAt(0);
	}
}
