package net.paramount.entity.stock;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.paramount.auth.entity.SecurityAccountProfile;
import net.paramount.domain.entity.general.Catalogue;
import net.paramount.entity.contact.ContactAddress;
import net.paramount.entity.contact.ContactAssignment;
import net.paramount.entity.contact.ContactCommunication;
import net.paramount.entity.contact.ContactCore;
import net.paramount.entity.contact.ContactHierarchy;
import net.paramount.entity.contact.ContactProfile;
import net.paramount.framework.entity.RepoEntity;

/**
 * Entity class ProductCategory
 * 
 * @author ducbq
 */
@Builder
@Data
@Entity
@Table(name = "product_profile_category")
@NoArgsConstructor
@AllArgsConstructor
public class ProductProfileCategory extends RepoEntity {
	private static final long serialVersionUID = 2574088175219320653L;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private ProductProfile owner;

	@ManyToOne
	@JoinColumn(name = "category_catalogue_id")
	private Catalogue category;

}
/*
 * 1. sub-category
 * 2. supplementary category
 * 3. extra category
 * 4. specialized subject area
 * 5. main subject category
 * */
