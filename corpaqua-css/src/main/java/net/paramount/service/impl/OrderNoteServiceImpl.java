package net.paramount.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.paramount.entity.trade.OrderNote;
import net.paramount.framework.repository.BaseRepository;
import net.paramount.framework.service.GenericServiceImpl;
import net.paramount.repository.trade.OrderNoteRepository;
import net.paramount.service.trade.OrderNoteService;

@Service
public class OrderNoteServiceImpl extends GenericServiceImpl<OrderNote, Long> implements OrderNoteService{
	private static final long serialVersionUID = -644103846803081016L;
	
	@Inject 
	private OrderNoteRepository repository;
	
	protected BaseRepository<OrderNote, Long> getRepository() {
		return this.repository;
	}
}
