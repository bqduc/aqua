package net.paramount.crs.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import net.paramount.entity.contact.ContactProfile;
import net.paramount.exceptions.ObjectNotFoundException;
import net.paramount.framework.model.SearchParameter;
import net.paramount.framework.service.GenericService;

public interface ContactProfileService extends GenericService<ContactProfile, Long> {

	/**
	 * Get one Contact with the provided code.
	 * 
	 * @param code
	 *            The Contact code
	 * @return The Contact
	 * @throws ObjectNotFoundException
	 *             If no such Contact exists.
	 */
	Optional<ContactProfile> getByCode(String code) throws ObjectNotFoundException;

	/**
	 * Get one Contacts with the provided search parameters.
	 * 
	 * @param searchParameter
	 *            The search parameter
	 * @return The pageable Contacts
	 */
	Page<ContactProfile> getObjects(SearchParameter searchParameter);
}
