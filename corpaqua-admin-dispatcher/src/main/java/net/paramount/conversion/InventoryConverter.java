package net.paramount.conversion;

import javax.faces.application.FacesMessage;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;

import net.paramount.css.service.stock.InventoryService;
import net.paramount.entity.stock.InventoryCore;
import net.paramount.framework.faces.ConverterCore;
import net.paramount.global.GlobalConstants;

@Named
@Service
public class InventoryConverter extends ConverterCore<InventoryCore> {
	@Inject
	private InventoryService service;

	@Override
	protected InventoryCore objectFromString(String value) {
		InventoryCore asObject = null;
		if (value != null && value.trim().length() > 0) {
			try {
				if (GlobalConstants.NONE_OBJECT_ID==Long.parseLong(value))
					return null;

				asObject = service.getObject(Long.valueOf(value));
			} catch (NumberFormatException e) {
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "ProductCore Conversion Error", "Not a valid generic item."));
			}
		} 
		return asObject;
	}

	@Override
	protected String objectToString(InventoryCore object) {
		String businessObjectSpec = null;
		if (object != null) {
			businessObjectSpec = new StringBuilder()
					.append(object.getId())
					.toString();
		} 
		return businessObjectSpec;
	}
}
