package net.paramount.repository.trade;

import org.springframework.stereotype.Repository;

import net.paramount.entity.general.TaxGroup;
import net.paramount.framework.repository.NameRepository;

@Repository
public interface TaxGroupRepository extends NameRepository<TaxGroup, Long>{
}
