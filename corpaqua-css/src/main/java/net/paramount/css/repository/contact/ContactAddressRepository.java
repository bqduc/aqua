package net.paramount.css.repository.contact;

import java.util.List;

import org.springframework.stereotype.Repository;

import net.paramount.entity.contact.CTAContact;
import net.paramount.entity.contact.ContactAddress;
import net.paramount.framework.repository.BaseRepository;

@Repository
public interface ContactAddressRepository extends BaseRepository<ContactAddress, Long> {
	List<ContactAddress> findByOwner(CTAContact contact);
}
