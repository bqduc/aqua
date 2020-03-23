/*
* Copyright 2017, Bui Quy Duc
* by the @authors tag. See the LICENCE in the distribution for a
* full listing of individual contributors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package net.paramount.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import net.paramount.auth.model.AccessDecision;
import net.paramount.framework.entity.BizObjectBase;

/**
 * Module.
 * 
 * @author bqduc
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "aux_access_decision_policy")
public class AccessDecisionPolicy extends BizObjectBase {
	private static final long serialVersionUID = 5502198617024752752L;

	@NotNull
	@Column(name = "access_pattern", length=120)
	private String accessPattern;

	@Builder.Default
	@Column(name="access_decision")
  @Enumerated(EnumType.ORDINAL)
	private AccessDecision accessDecision = AccessDecision.ACCESS_ABSTAIN;

	@ManyToOne
	@JoinColumn(name = "authority_id")
	private Authority authority;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private AccessDecisionPolicy parent;

	@Column(name = "info", columnDefinition="text")
	private String info;

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public AccessDecisionPolicy getParent() {
		return parent;
	}

	public void setParent(AccessDecisionPolicy parent) {
		this.parent = parent;
	}

	public String getAccessPattern() {
		return accessPattern;
	}

	public void setAccessPattern(String accessPattern) {
		this.accessPattern = accessPattern;
	}

	public AccessDecision getAccessDecision() {
		return accessDecision;
	}

	public void setAccessDecision(AccessDecision accessDecision) {
		this.accessDecision = accessDecision;
	}

	public Authority getAuthority() {
		return authority;
	}

	public void setAuthority(Authority authority) {
		this.authority = authority;
	}
	
}
