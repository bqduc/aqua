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

package net.paramount.entity.trade;

import net.paramount.entity.general.Money;
import net.paramount.entity.general.MoneySet;
import net.paramount.entity.general.QuantityCore;

/**
 * Generic nterface for invoice item.
 * 
 * @author haky
 */
public interface InvoiceItem {
    Long getId();
    String getName();
    String getInfo();
    String getLineCode();
    QuantityCore getQuantity();
    Money getUnitPrice();
    MoneySet getAmount();
}