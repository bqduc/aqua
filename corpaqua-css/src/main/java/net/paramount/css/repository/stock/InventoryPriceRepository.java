package net.paramount.css.repository.stock;

import java.util.List;

import org.springframework.stereotype.Repository;

import net.paramount.entity.stock.InventoryCore;
import net.paramount.entity.stock.InventoryPrice;
import net.paramount.framework.repository.BaseRepository;

@Repository
public interface InventoryPriceRepository extends BaseRepository<InventoryPrice, Long> {
	Long countByOwner(InventoryCore inventoryCore);
	List<InventoryPrice> findByOwner(InventoryCore inventoryCore);
}
