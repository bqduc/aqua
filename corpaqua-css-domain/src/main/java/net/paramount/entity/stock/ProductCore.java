package net.paramount.entity.stock;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.paramount.common.ListUtility;
import net.paramount.entity.general.MeasureUnit;
import net.paramount.framework.entity.RepoAuditable;
import net.paramount.global.GlobalConstants;
import net.paramount.model.InventoryType;

/**
 * Entity class Product
 * 
 * @author ducbq
 */
@Data
@Builder
@Entity
@Table(name = "product_core")
@EqualsAndHashCode(callSuper=false)
public class ProductCore extends RepoAuditable {
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
	private ProductCore parent;

	@Column(name = "minimum_options")
	private Integer minimumOptions;

	@Column(name = "maximum_options")
	private Integer maximumOptions;

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

	@Builder.Default
	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProductProfile> productProfileList = ListUtility.createList();

	@Transient
	public String getCaption() {
		return "[" + getCode() + "] " + getName();
	}

	@Override
	public String toString() {
		return getName();
	}
}
