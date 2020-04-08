/**
 * 
 */
package net.paramount.entity.trade;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.paramount.entity.contact.ContactPerson;
import net.paramount.entity.general.Organization;
import net.paramount.framework.entity.RepoAuditable;
import net.paramount.model.AccountType;

/**
 * @author ducbq
 *
 */
@Data
@Builder
@Entity
@Table(name="ACCOUNT")
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
public class Account extends RepoAuditable {
	private static final long serialVersionUID = 4969045202995145269L;

  @Column(name="ACCOUNT_TYPE")
  @Enumerated(EnumType.ORDINAL)
  private AccountType accountType;
  
  @Column(name="CODE", length=20, nullable=false, unique=true) 
  private String code;
  
  @Column(name="NAME", length=50) 
  private String name;
  
  @Column(name="INFO") 
  private String info;

  @Column(name="SYSTEM")
  private Boolean system;

  @Embedded
  private ContactPerson contactPerson;

  @ManyToOne
  @JoinColumn(name="ORGANIZATION_ID")
  private Organization organization;
}
