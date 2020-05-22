/**
 * 
 */
package net.paramount.lingual.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import net.paramount.framework.repository.BaseRepository;
import net.paramount.lingual.entity.MessageLabel;

/**
 * @author ducbq
 *
 */
@Repository
public interface MessageLabelRepository extends BaseRepository<MessageLabel, Long> {
	List<MessageLabel> findByLanguage(String language);

	MessageLabel findByKeyAndLanguage(String key, String language);

	boolean existsByKey(String key);
}
