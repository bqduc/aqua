package net.paramount.lingual.service;

import java.util.List;

import net.paramount.domain.entity.general.Language;
import net.paramount.exceptions.ObjectNotFoundException;
import net.paramount.framework.service.GenericService;
import net.paramount.lingual.entity.Label;
import net.paramount.lingual.entity.ResxMessage;

public interface MessageService extends GenericService<ResxMessage, Long> {
	List<ResxMessage> getByLanguage(Language language) throws ObjectNotFoundException;
	ResxMessage getByLabelAndLanguage(Label label, Language language) throws ObjectNotFoundException;
}
