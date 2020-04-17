package net.paramount.css.repository.stock;

import java.util.List;

import org.springframework.stereotype.Repository;

import net.paramount.entity.stock.InventoryCore;
import net.paramount.entity.stock.InventoryImage;
import net.paramount.framework.repository.BaseRepository;

@Repository
public interface InventoryImageRepository extends BaseRepository<InventoryImage, Long> {
	Long countByOwner(InventoryCore inventoryCore);
	List<InventoryImage> findByOwner(InventoryCore inventoryCore);
}
