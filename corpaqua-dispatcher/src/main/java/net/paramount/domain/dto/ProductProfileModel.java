/**
 * 
 */
package net.paramount.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.paramount.domain.model.dto.ModelCore;
import net.paramount.entity.stock.ProductCore;
import net.paramount.entity.stock.ProductProfile;
import net.paramount.entity.stock.ProductProfileDetail;

/**
 * @author ducbq
 *
 */
@Data
@Builder
@EqualsAndHashCode(callSuper=false)
public class ProductProfileModel extends ModelCore {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4218932729423034160L;
	
	private ProductCore productCore;
	private ProductProfile productProfile;
	private ProductProfileDetail productProfileDetail;
}
