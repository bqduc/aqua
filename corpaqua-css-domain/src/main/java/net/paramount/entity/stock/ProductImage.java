package net.paramount.entity.stock;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.paramount.framework.entity.RepoAuditable;

/**
 * Entity class Product
 * 
 * @author ducbq
 */
@Data
@Builder
@Entity
@Table(name = "product_image")
@EqualsAndHashCode(callSuper=false)
public class ProductImage extends RepoAuditable {
	private static final long serialVersionUID = 9092368070849737080L;

	@ManyToOne
	@JoinColumn(name = "product_profile_id")
	private ProductProfile owner;

	@Basic(fetch = FetchType.LAZY)
	@Lob
	@Column(name = "image_default")
	@Type(type = "org.hibernate.type.ImageType")
	private byte[] imageDefault;

}
