/*
 * Copyleft 2007-2011 Ozgur Yazilim A.S.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 * http://www.gnu.org/licenses/lgpl.html
 *
 * www.tekir.com.tr
 * www.ozguryazilim.com.tr
 *
 */

package net.paramount.entity.general;

import java.math.BigDecimal;
import java.text.NumberFormat;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author haky
 */
@Embeddable
public class Quantity implements java.io.Serializable {
	private static final long serialVersionUID = -8871186569561849667L;

	@ManyToOne
	@JoinColumn(name = "unit_id", insertable = false, updatable = false)
	private MeasureUnit unit;

	@Column(name = "quantity", precision = 5, scale = 2)
	private Double value = 0d;

	public Quantity() {
		this.unit = null;
		this.value = 0d;
	}

	public Quantity(Quantity quantity) {
		this.value = new Double(quantity.getValue());
		this.unit = quantity.getUnit();
	}

	public Quantity(double value, MeasureUnit measureUnit) {
		this.value = value;
		this.unit = measureUnit;
	}

	public void moveFieldsOf(Quantity anotherQuantity) {
		if (anotherQuantity != null) {
			this.unit = anotherQuantity.getUnit();
			this.value = anotherQuantity.getValue();
		}
	}

	public Double getValue() {
		return value;
	}

	public BigDecimal asBigDecimal() {
		return BigDecimal.valueOf(value);
	}

	public void setValue(Double value) {
		this.value = value;
	}

	@Override
	public String toString() {

		NumberFormat f = NumberFormat.getInstance();
		f.setMaximumFractionDigits(2);
		f.setMinimumFractionDigits(2);

		return f.format(getValue()) + "#" + getUnit().getCode();
	}

	public String toStringInNarrowFormat() {
		NumberFormat f = NumberFormat.getInstance();
		f.setMaximumFractionDigits(2);
		f.setMinimumFractionDigits(2);

		String result = f.format(getValue()) + "#" + getUnit().getCode();
		if (result.length() > 7) {
			result = result.substring(0, 7);
		}
		return result;
	}

	public boolean isZero() {
		return this.value == 0d;
	}

	public MeasureUnit getUnit() {
		return unit;
	}

	public void setUnit(MeasureUnit unit) {
		this.unit = unit;
	}
}
