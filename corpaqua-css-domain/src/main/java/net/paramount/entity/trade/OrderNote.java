/**
 * 
 */
package net.paramount.entity.trade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.paramount.auth.entity.SecurityAccountProfile;
import net.paramount.entity.contact.CTAContact;
import net.paramount.entity.contact.ContactAddress;
import net.paramount.entity.contact.ContactAssignment;
import net.paramount.entity.contact.ContactCommunication;
import net.paramount.entity.contact.ContactCore;
import net.paramount.entity.contact.ContactHierarchy;
import net.paramount.entity.contact.ContactProfile;
import net.paramount.entity.doc.DocumentBase;
import net.paramount.entity.doc.DocumentType;
import net.paramount.entity.stock.Warehouse;

/**
 * @author ducbq
 *
 */

@Builder
@Entity
@Table(name="ORDER_NOTE")
@NoArgsConstructor
@AllArgsConstructor
public class OrderNote extends DocumentBase {
	private static final long serialVersionUID = 5374753611465150282L;

	@Setter
	@Getter
  @Column(name="ACTION")
  @Enumerated(EnumType.ORDINAL)
  private TradeAction action;
  
	@Setter
	@Getter
  @ManyToOne
  @JoinColumn(name="WAREHOUSE_ID")
  private Warehouse warehouse;
  
	@Setter
	@Getter
  @ManyToOne
  @JoinColumn(name="CONTACT_ID")
  private CTAContact contact;

  /**
   * satışı yapan tezgahtar bilgisini tutar.
   */
	@Setter
	@Getter
  @ManyToOne
  @JoinColumn(name="CLERK_ID")
  private SecurityAccountProfile clerk;

  /*@OneToMany(mappedBy = "owner", cascade=CascadeType.ALL )
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)*/
	@Builder.Default
	@Setter
	@Getter
  @OneToMany(
  		mappedBy="owner"
      , cascade = CascadeType.ALL
      , orphanRemoval = true
      , fetch = FetchType.EAGER
  )
  private List<OrderItem> items = new ArrayList<OrderItem>();

  @Override
	public DocumentType getDocumentType() {
		return null;
	}

}
