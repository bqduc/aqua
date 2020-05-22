package net.paramount.conversion.trade;

import javax.faces.application.FacesMessage;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;

import net.paramount.entity.trade.Tax;
import net.paramount.framework.faces.ConverterCore;
import net.paramount.global.GlobalConstants;
import net.paramount.service.trade.TaxService;

@Named
@Service
public class TaxConverter extends ConverterCore<Tax> {
	@Inject
	private TaxService service;

	@Override
	protected Tax objectFromString(String value) {
		Tax asObject = null;
		if (value != null && value.trim().length() > 0) {
			try {
				if (GlobalConstants.NONE_OBJECT_ID==Long.parseLong(value))
					return null;

				asObject = service.getObject(Long.valueOf(value));
			} catch (NumberFormatException e) {
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Tax Conversion Error", "Not a valid generic item."));
			}
		} 
		return asObject;
	}

	@Override
	protected String objectToString(Tax object) {
		String bizObjectSpec = null;
		if (object != null) {
			bizObjectSpec = new StringBuilder()
					.append(object.getId())
					.toString();
		} 
		return bizObjectSpec;
	}
}
