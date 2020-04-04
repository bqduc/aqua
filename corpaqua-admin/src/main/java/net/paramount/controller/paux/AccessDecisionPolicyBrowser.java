package net.paramount.controller.paux;

import java.util.List;
import java.util.Locale;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.paramount.auth.entity.AccessDecisionPolicy;
import net.paramount.auth.model.AccessDecisionPolicyFilter;
import net.paramount.auth.service.AccessDecisionPolicyService;
import net.paramount.framework.controller.BrowserHome;
import net.paramount.msp.faces.model.FacesCar;
import net.paramount.msp.faces.model.FacesTeamFacade;
import net.paramount.msp.faces.service.FacesCarService;

/**
 * @author ducbq
 */
@Named
@ViewScoped
public class AccessDecisionPolicyBrowser extends BrowserHome<AccessDecisionPolicy, AccessDecisionPolicyFilter> /*BaseController*/ {
	private static final long serialVersionUID = -1878333227078649346L;
	private List<FacesTeamFacade> teams;
	private List<FacesCar> cars;
	private FacesCar selectedCar;
	private List<String> selectedColors;

	private List<FacesCar> filteredCars;

	@Inject
	private FacesCarService carService;

	@Inject
	private AccessDecisionPolicyService businessService;

	public boolean filterByPrice(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim();
		if (filterText == null || filterText.equals("")) {
			return true;
		}

		if (value == null) {
			return false;
		}

		return ((Comparable) value).compareTo(Integer.valueOf(filterText)) > 0;
	}

	public int getRandomPrice() {
		return (int) (Math.random() * 100000);
	}

	public boolean filterByColor(Object value, Object filter, Locale locale) {

		if (filter == null || filter.toString().equals("")) {
			return true;
		}

		if (value == null) {
			return false;
		}

		if (selectedColors.isEmpty()) {
			return true;
		}

		return selectedColors.contains(value.toString());
	}

	public List<FacesTeamFacade> getTeams() {
		return teams;
	}

	public List<String> getBrands() {
		return carService.getBrands();
	}

	public List<String> getColors() {
		return carService.getColors();
	}

	public List<FacesCar> getCars() {
		return cars;
	}

	public List<FacesCar> getCarsCarousel() {
		return cars.subList(0, 8);
	}

	public List<FacesCar> getFilteredCars() {
		return filteredCars;
	}

	public void setFilteredCars(List<FacesCar> filteredCars) {
		this.filteredCars = filteredCars;
	}

	public List<String> getSelectedColors() {
		return selectedColors;
	}

	public void setSelectedColors(List<String> selectedColors) {
		this.selectedColors = selectedColors;
	}

	public FacesCar getSelectedCar() {
		return selectedCar;
	}

	public void setSelectedCar(FacesCar selectedCar) {
		this.selectedCar = selectedCar;
	}

	@Override
	protected List<AccessDecisionPolicy> doGetBusinessObjects() {
		return this.businessService.getObjects();
	}
}
