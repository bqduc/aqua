package net.paramount.auth.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.paramount.auth.entity.AccessDecisionPolicy;
import net.paramount.auth.entity.Authority;
import net.paramount.auth.repository.AccessDecisionPolicyRepository;
import net.paramount.auth.service.AccessDecisionPolicyService;
import net.paramount.framework.repository.BaseRepository;
import net.paramount.framework.service.GenericServiceImpl;


@Service
public class AccessDecisionPolicyServiceImpl extends GenericServiceImpl<AccessDecisionPolicy, Long> implements AccessDecisionPolicyService {
	private static final long serialVersionUID = 7987317340813933975L;

	@Inject 
	private AccessDecisionPolicyRepository repository;
	
	protected BaseRepository<AccessDecisionPolicy, Long> getRepository() {
		return this.repository;
	}

	@Override
	public List<AccessDecisionPolicy> getAccessDecisionPolicies(String accessPattern) {
		return this.repository.findByAccessPattern(accessPattern);
	}

	@Override
	public List<AccessDecisionPolicy> getAccessDecisionPoliciesByAuthority(Authority authority) {
		return this.repository.findByAuthority(authority);
	}
}
