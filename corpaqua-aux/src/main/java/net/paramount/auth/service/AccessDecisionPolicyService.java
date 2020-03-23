package net.paramount.auth.service;

import java.util.List;

import net.paramount.auth.entity.AccessDecisionPolicy;
import net.paramount.auth.entity.Authority;
import net.paramount.framework.service.GenericService;

public interface AccessDecisionPolicyService extends GenericService<AccessDecisionPolicy, Long> {
	List<AccessDecisionPolicy> getAccessDecisionPolicies(String accessPattern);
	List<AccessDecisionPolicy> getAccessDecisionPoliciesByAuthority(Authority authority);
}
