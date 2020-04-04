/**
 * 
 */
package net.paramount.auth.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import net.paramount.auth.entity.AccessDecisionAuthority;
import net.paramount.auth.entity.Authority;
import net.paramount.framework.repository.BaseRepository;

/**
 * @author bqduc
 *
 */
@Repository
public interface AccessDecisionAuthorityRepository extends BaseRepository<AccessDecisionAuthority, Long> {
	List<AccessDecisionAuthority> findByAuthority(Authority authority);

	boolean existsByAuthority(Authority authority);
}
