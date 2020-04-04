package net.paramount.controller.catalog;

import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import net.paramount.domain.entity.general.Catalogue;
import net.paramount.domain.model.CatalogueFilter;
import net.paramount.framework.controller.BrowserHome;
import net.paramount.service.general.CatalogueService;

/**
 * @author ducbq
 */
@Named
@ViewScoped
public class CatalogueBrowser extends BrowserHome<Catalogue, CatalogueFilter> {
	private static final long serialVersionUID = 3611002430455942097L;

	@Inject
	private CatalogueService businessService;

	@Setter
	@Getter
	private List<Catalogue> selectedObjects;

	@Setter
	@Getter
	private List<Catalogue> businessObjects;

	@Override
	protected List<Catalogue> doGetBusinessObjects() {
		return this.businessService.getObjects();
	}

	@Override
	public CatalogueFilter createFilterModel() {
		return CatalogueFilter.builder().build();
	}
}
