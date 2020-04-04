package net.paramount.entity.stock;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.paramount.entity.general.Money;
import net.paramount.entity.general.MoneySet;
import net.paramount.entity.general.Quantity;
import net.paramount.entity.trade.DiscountOrExpense;
import net.paramount.entity.trade.UnitPriceMoneySet;
import net.paramount.framework.entity.RepoAuditable;
import net.paramount.framework.validation.StrictlyPositiveNumber;
import net.paramount.model.UnitPriceScale;

/**
 * Entity class Product
 * 
 * @author ducbq
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "product_profile_discount")
@EqualsAndHashCode(callSuper=false)
public class ProductProfileDiscount extends RepoAuditable {
	private static final long serialVersionUID = 6970590315789214584L;

	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "issued_date")
	private Date issuedDate;

	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "expired_date")
	private Date expiredDate;

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private ProductProfile owner;

	/**
	 * If the product is marked as a cost or a discount, it holds the discount / cost information.
	 */
	@Builder.Default
	@Embedded
	@Valid
	@AttributeOverrides({ 
		@AttributeOverride(name = "percentage", column = @Column(name = "DISCOUNT_EXPENSE_PERCENTAGE")),
		@AttributeOverride(name = "rate", column = @Column(name = "DISCOUNT_EXPENSE_RATE")),
		@AttributeOverride(name = "currency", column = @Column(name = "DISCOUNT_EXPENSE_CCY")),
		@AttributeOverride(name = "value", column = @Column(name = "DISCOUNT_EXPENSE_VALUE")),
		@AttributeOverride(name = "localAmount", column = @Column(name = "DISCOUNT_EXPENSE_LCYVAL")) })
	private DiscountOrExpense discountOrExpense = new DiscountOrExpense();

	/**
	 * The last purchase is price information.
	 */
	@Builder.Default
	@Embedded
	@Valid
	@AttributeOverrides({ 
		@AttributeOverride(name = "currency", column = @Column(name = "LAST_PURCHASE_PRICE_CCY")),
		@AttributeOverride(name = "value", column = @Column(name = "LAST_PURCHASE_PRICE_VALUE")),
		@AttributeOverride(name = "localAmount", column = @Column(name = "LAST_PURCHASE_PRICE_LCYVAL")) })
	private MoneySet lastPurchasePrice = new UnitPriceMoneySet();

	/**
	 * Last sale is the price information.
	 */
	@Builder.Default
	@Embedded
	@Valid
	@AttributeOverrides({ 
		@AttributeOverride(name = "currency", column = @Column(name = "LAST_SALE_PRICE_CCY")),
		@AttributeOverride(name = "value", column = @Column(name = "LAST_SALE_PRICE_VALUE")),
		@AttributeOverride(name = "localAmount", column = @Column(name = "LAST_SALE_PRICE_LCYVAL")) })
	private MoneySet lastSalePrice = new UnitPriceMoneySet();

	/**
	 * It keeps the scale (after the comma) information of the unit price of the product or service.
	 */
	@Builder.Default
	@Column(name = "UNIT_PRICE_SCALE")
	@Enumerated(EnumType.ORDINAL)
	private UnitPriceScale unitPriceScale = UnitPriceScale.Low;

	@Builder.Default
	@Column(name = "sale_price")
	@StrictlyPositiveNumber(message = "{PositiveSalePrice}")
	private Double salePrice = 0d;

	@Builder.Default
	@Column(name = "purchase_price")
	@StrictlyPositiveNumber(message = "{PositiveCost}")
	private Double purchasePrice = 0d;

	@Builder.Default
	@Embedded
  @Valid
  @AttributeOverrides( {
      @AttributeOverride(name="currency", column=@Column(name="unit_price_ccy")),
      @AttributeOverride(name="value", column=@Column(name="unit_price_value"))
  })
  private Money unitPrice = new Money(); 

	@Builder.Default
  @Embedded
  @Valid
  @AttributeOverrides( {
      @AttributeOverride(name="currency", column=@Column(name="unit_price_market_ccy")),
      @AttributeOverride(name="value", column=@Column(name="unit_price_market_value"))
  })
  private Money unitPriceMarket = new Money(); 

	@Builder.Default
  @Embedded
  @Valid
  @AttributeOverrides( {
      @AttributeOverride(name="currency", column=@Column(name="cost_price_ccy")),
      @AttributeOverride(name="value", column=@Column(name="cost_price_value"))
  })
  private Money costPrice = new Money(); 

	@Builder.Default
  @Embedded
  @Valid
  @AttributeOverrides( {
      @AttributeOverride(name="currency", column=@Column(name="selling_price_ccy")),
      @AttributeOverride(name="value", column=@Column(name="selling_price_value"))
  })
  private Money sellingPrice = new Money(); 

	@Builder.Default
  @Embedded
  @Valid
  @AttributeOverrides( {
      @AttributeOverride(name="currency", column=@Column(name="prog_selling_price_ccy")),
      @AttributeOverride(name="value", column=@Column(name="prog_selling_price_value"))
  })
  private Money progSellingPrice = new Money(); 

	@Builder.Default
  @Embedded
  @Valid
  @AttributeOverrides( {
    @AttributeOverride(name="unit", column=@Column(name="prog_selling_unit_id")),
    @AttributeOverride(name="value", column=@Column(name="prog_selling_value"))
  })
  private Quantity progSellingQuantity = new Quantity();

	@Column(name="discount_rate")
	private Double discountRate;

	public boolean isHasLowerPriceThanCent() {
		if (unitPriceScale.equals(UnitPriceScale.High))
			return true;
		return false;
	}

	public void setHasLowerPriceThanCent(boolean aValue) {
		if (aValue) {
			unitPriceScale = UnitPriceScale.High;
		} else {
			unitPriceScale = UnitPriceScale.Low;
		}
	}

	public BigDecimal getLastPurchasePriceValue() {
		return lastPurchasePrice.getValue();
	}

	public void setLastPurchasePriceValue(BigDecimal lastPurchasePriceValue) {
		this.lastPurchasePrice.setValue(lastPurchasePriceValue);
	}

	public BigDecimal getLastSalePriceValue() {
		return lastSalePrice.getValue();
	}

	public void setLastSalePriceValue(BigDecimal lastSalePriceValue) {
		this.lastSalePrice.setValue(lastSalePriceValue);
	}
}