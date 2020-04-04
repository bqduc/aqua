package net.paramount.css.repository.stock;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.paramount.entity.stock.ProductProfile;
import net.paramount.framework.repository.BaseRepository;

@Repository
public interface ProductProfileRepository extends BaseRepository<ProductProfile, Long> {
	@Query("SELECT entity FROM #{#entityName} entity WHERE ("
			+ " LOWER(entity.owner.code) like LOWER(CONCAT('%',:keyword,'%')) or "
			+ " LOWER(entity.owner.barcode) like LOWER(CONCAT('%',:keyword,'%')) or "
			+ " LOWER(entity.owner.name) like LOWER(CONCAT('%',:keyword,'%')) or "
			+ " LOWER(entity.owner.translatedName) like LOWER(CONCAT('%',:keyword,'%')) or "
			+ " LOWER(entity.composition) like LOWER(CONCAT('%',:keyword,'%')) "
			+ ")"
	)
	Page<ProductProfile> search(@Param("keyword") String keyword, Pageable pageable);
}
