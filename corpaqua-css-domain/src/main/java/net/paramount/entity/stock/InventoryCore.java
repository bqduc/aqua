package net.paramount.entity.stock;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.paramount.common.ListUtility;
import net.paramount.domain.entity.Attachment;
import net.paramount.entity.general.MeasureUnit;
import net.paramount.framework.entity.RepoAuditable;
import net.paramount.global.GlobalConstants;
import net.paramount.model.InventoryType;

/**
 * Entity class Product
 * 
 * @author ducbq
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "inventory_core")
public class InventoryCore extends RepoAuditable {
	private static final long serialVersionUID = -2929178651788000055L;

	@Builder.Default
	@Column(name = "product_type")
	@Enumerated(EnumType.ORDINAL)
	private ProductType productType = ProductType.Product;

	@Column(name = "code", length = GlobalConstants.SIZE_CODE)
	private String code;

	@Column(name = "barcode", length = GlobalConstants.SIZE_BARCODE)
	private String barcode;

	@Column(name = "NAME", length = GlobalConstants.SIZE_NAME)
	private String name;

	/**
	 * It keeps the writing information that will be printed on the label.
	 */
	@Column(name = "LABEL_NAME")
	private String labelName;

	@Size(max = GlobalConstants.SIZE_NAME)
	@Column(name = "translated_name")
	private String translatedName;
	
	@Builder.Default
	@Column(name = "open_date")
	@Temporal(TemporalType.DATE)
	private Date openDate = new Date();

	@Column(name = "info", columnDefinition = "TEXT")
	private String info;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private InventoryCore parent;

	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "issue_date")
	private Date issueDate;

	@Builder.Default
	@Column(name = "reset_date")
	private ZonedDateTime resetDate = null;

	@Builder.Default
	@Column(name = "weight")
	private Double weight = 0d;

	@Builder.Default
	@Column(name = "volume")
	private Double volume = 0d;

	@Builder.Default
	@Column(name = "lenght")
	private Double length = 0d;

	@Column(name = "sale_ok")
	private Boolean saleOk;

	@Column(name = "purchase_ok")
	private Boolean purchaseOk;

	@ManyToOne
	@JoinColumn(name = "measure_unit_id")
	private MeasureUnit measureUnit;

	@Column(name = "inventory_type")
	@Enumerated(EnumType.ORDINAL)
	private InventoryType inventoryType;

	@Column(name="stock_amount")
	private Double stockAmount;

	@Column(name="disable_when_stock_amount_is_zero")
	private Boolean disableWhenStockAmountIsZero;

	@Column(name="sort_order")
	private Integer sortOrder;

	@Column(name="fractional_unit")
	private Boolean fractionalUnit;

	@Column(name="default_sell_portion")
	private Integer defaultSellPortion;

	/*@Basic(fetch = FetchType.LAZY)
	@Lob
	@Column(name = "image_1")
	@Type(type = "org.hibernate.type.ImageType")
	private byte[] imageBuffer1;

	@Basic(fetch = FetchType.LAZY)
	@Lob
	@Column(name = "image_2")
	@Type(type = "org.hibernate.type.ImageType")
	private byte[] imageBuffer2;

	@Basic(fetch = FetchType.LAZY)
	@Lob
	@Column(name = "image_3")
	@Type(type = "org.hibernate.type.ImageType")
	private byte[] imageBuffer3;*/
/*	@Builder.Default
	@OneToMany(
  		mappedBy="owner"
      , cascade = CascadeType.ALL
      , orphanRemoval = true
      , fetch = FetchType.EAGER
      , targetEntity = InventoryImage.class
	)
	private Set<InventoryImage> images = ListUtility.createHashSet();
*/

	@Transient
	public String getCaption() {
		return "[" + getCode() + "] " + getName();
	}

	@Override
	public String toString() {
		return getName();
	}

	/*public ProductCore addProfile(ProductProfile productProfile) {
		productProfile.setOwner(this);
		productProfileList.add(productProfile);
		return this;
	}*/

	/*public InventoryCore addImage(InventoryImage inventoryImage) {
		this.images.add(inventoryImage);
		return this;
	}

	public InventoryCore addImage(Attachment attachment) {
		this.images.add(
  		InventoryImage.builder()
  		.owner(this)
  		.imageBuffer(attachment.getData())
  		.build()
		);
		return this;
	}*/
}
