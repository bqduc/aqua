package net.paramount.service.trade;

import java.util.Optional;

import net.paramount.entity.trade.Bank;
import net.paramount.exceptions.ObjectNotFoundException;
import net.paramount.framework.service.GenericService;

public interface BankService extends GenericService<Bank, Long> {

	/**
	 * Get one Bank with the provided name.
	 * 
	 * @param name
	 *            The Bank name
	 * @return The Bank
	 * @throws ObjectNotFoundException
	 *             If no such Bank exists.
	 */
	Optional<Bank> getByName(String name) throws ObjectNotFoundException;

	/**
	 * Get one Bank with the provided code.
	 * 
	 * @param code
	 *            The Bank code
	 * @return The Bank
	 * @throws ObjectNotFoundException
	 *             If no such Bank exists.
	 */
	Optional<Bank> getByCode(String code) throws ObjectNotFoundException;
}
