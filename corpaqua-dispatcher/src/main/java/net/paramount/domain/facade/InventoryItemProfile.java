package net.paramount.domain.facade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.nep.facade.ProductProfile;
import net.paramount.common.ListUtility;
import net.paramount.entity.stock.InventoryImage;
import net.paramount.exceptions.EcosphereException;

/**
 * A Book.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper=false)
public class InventoryItemProfile implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4902783191190293446L;

	private String productType;
	private String code;
	private String barcode;
	private String name;
	private String labelName;
	private String translatedName;
	private Date openDate;
	private String info;
	private Long parentId;
	private Date issueDate;
	private Date resetDate;
	private Double weight;
	private Double volume;
	private Double length;
	private Boolean saleOk;
	private Boolean purchaseOk;
	private Long measureUnitId;
	private String inventoryType;
	private Double stockAmount;
	private Boolean disableWhenStockAmountIsZero;
	private Integer sortOrder;
	private Boolean fractionalUnit;
	private Integer defaultSellPortion;
	private BigDecimal purchasePrice;
	private BigDecimal salesPrice;
	private BigDecimal priceWithoutTax;
	private String categoryCode;
	private String taxCode;
	private String printerGroupCode;
	private List<String> servicingOrderTypeCodes;
	private List<String> modifierGroupCodes;
	private Map<String, Object> priceByParticularOrder;
	private String notes;


	private List<byte[]> images;

	public InventoryItemProfile(ProductProfile productProfile) {
		try {
			this.code = productProfile.getCore().getCode();
			this.barcode = productProfile.getCore().getBarcode();
			this.name = productProfile.getCore().getName();
			this.labelName = productProfile.getCore().getLabelName();

			this.images = ListUtility.createList();
			for (InventoryImage inventoryImage :productProfile.getInventoryImages()) {
				this.images.add(inventoryImage.getImageBuffer());
			}
		} catch (Exception e) {
			throw new EcosphereException(e);
		}
	}
}
