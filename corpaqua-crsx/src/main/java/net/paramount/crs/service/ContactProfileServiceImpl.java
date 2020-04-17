package net.paramount.crs.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.paramount.crs.repository.cta.ContactProfileRepository;
import net.paramount.entity.contact.ContactProfile;
import net.paramount.framework.repository.BaseRepository;
import net.paramount.framework.service.GenericServiceImpl;


@Service
public class ContactProfileServiceImpl extends GenericServiceImpl<ContactProfile, Long> implements ContactProfileService{


	/**
	 * 
	 */
	private static final long serialVersionUID = -6437769894391556823L;
	@Inject 
	private ContactProfileRepository repository;
	
	protected BaseRepository<ContactProfile, Long> getRepository() {
		return this.repository;
	}
}
