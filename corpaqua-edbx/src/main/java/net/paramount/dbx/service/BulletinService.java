package net.paramount.dbx.service;

import java.util.Optional;

import net.paramount.dbx.entity.Bulletin;
import net.paramount.exceptions.ObjectNotFoundException;
import net.paramount.framework.service.GenericService;

public interface BulletinService extends GenericService<Bulletin, Long>{

  /**
   * Get one Bulletin with the provided serial.
   * 
   * @param serial The Bulletin serial
   * @return The item
   * @throws ObjectNotFoundException If no such Bulletin exists.
   */
	Optional<Bulletin> getBySerial(String serial) throws ObjectNotFoundException;
}
