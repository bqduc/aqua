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
import net.paramount.entity.trade.Tax;
import net.paramount.service.trade.TaxService;

@Named
@ViewScoped
public class TaxSuggestion implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8599869494508104330L;

	@Getter @Setter
	private Tax item;

	@Getter @Setter
	private List<Tax> selectedItems;

	@Inject
	private TaxService businessService;

	public List<Tax> completeItem(String keyword) {
		List<Tax> fetchedItems = businessService.search(keyword);
		return fetchedItems;
	}

	public List<Tax> completeItemContains(String keyword) {
		List<Tax> fetchedItems = businessService.search(keyword);
		return fetchedItems;
	}

	public void onItemSelect(SelectEvent<?> event) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tax Selected", event.getObject().toString()));
	}

	public char getItemGroup(Tax item) {
		return item.getName().charAt(0);
	}
}
