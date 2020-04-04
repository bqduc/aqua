package net.paramount.repository.trade;

import org.springframework.stereotype.Repository;

import net.paramount.entity.trade.ShipmentNote;
import net.paramount.framework.repository.CodeSerialRepository;

@Repository
public interface ShipmentNoteRepository extends CodeSerialRepository<ShipmentNote, Long>{
}
