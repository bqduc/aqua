package net.paramount.css.service.stock;

import java.util.List;

import net.nep.facade.ProductProfile;
import net.paramount.entity.stock.InventoryCore;
import net.paramount.entity.stock.InventoryDetail;
import net.paramount.entity.stock.InventoryImage;
import net.paramount.framework.service.GenericService;

public interface InventoryService extends GenericService<InventoryCore, Long> {
	ProductProfile getProfile(Long objectId);

	InventoryImage saveInventoryImage(InventoryImage inventoryImage);
	List<InventoryImage> getInventoryImages(Long inventoryId);

	InventoryImage getInventoryImage(Long inventoryImageId);
	
	InventoryDetail saveInventoryImage(InventoryDetail inventoryDetail);

	ProductProfile saveProfile(ProductProfile productProfile);
}
