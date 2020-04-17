package net.paramount.css.repository.stock;

import java.util.List;

import org.springframework.stereotype.Repository;

import net.paramount.entity.stock.InventoryCore;
import net.paramount.entity.stock.InventoryDetail;
import net.paramount.framework.repository.BaseRepository;

@Repository
public interface InventoryDetailRepository extends BaseRepository<InventoryDetail, Long> {
	Long countByOwner(InventoryCore inventoryCore);
	List<InventoryDetail> findByOwner(InventoryCore inventoryCore);
}
