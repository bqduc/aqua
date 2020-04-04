/**
 * 
 */
package net.paramount.lingual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.paramount.lingual.entity.Message;

/**
 * @author ducbq
 *
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
	List<Message> findByLocale(String locale);

	Message findByKeyAndLocale(String key, String locale);

	boolean existsByKey(String key);
}
