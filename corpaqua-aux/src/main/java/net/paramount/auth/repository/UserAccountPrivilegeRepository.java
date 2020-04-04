/**
 * 
 */
package net.paramount.auth.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import net.paramount.auth.entity.SecurityAccountProfile;
import net.paramount.auth.entity.UserAccountPrivilege;
import net.paramount.framework.repository.BaseRepository;

/**
 * @author bqduc
 *
 */
@Repository
public interface UserAccountPrivilegeRepository extends BaseRepository<UserAccountPrivilege, Long> {
	List<UserAccountPrivilege> findByUserAccount(SecurityAccountProfile userAccount);
}
