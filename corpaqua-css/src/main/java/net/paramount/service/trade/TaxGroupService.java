package net.paramount.service.trade;

import java.util.Optional;

import net.paramount.entity.general.TaxGroup;
import net.paramount.exceptions.ObjectNotFoundException;
import net.paramount.framework.service.GenericService;

public interface TaxGroupService extends GenericService<TaxGroup, Long> {

	/**
	 * Get one TaxGroup with the provided name.
	 * 
	 * @param name
	 *            The TaxGroup name
	 * @return The TaxGroup
	 * @throws ObjectNotFoundException
	 *             If no such TaxGroup exists.
	 */
	Optional<TaxGroup> getByName(String name) throws ObjectNotFoundException;
}
