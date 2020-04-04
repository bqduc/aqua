package net.paramount.repository.trade;

import org.springframework.stereotype.Repository;

import net.paramount.entity.trade.PromissoryNote;
import net.paramount.framework.repository.BaseRepository;

@Repository
public interface PromissoryNoteRepository extends BaseRepository<PromissoryNote, Long>{
}
