package net.paramount.css.service.config;

import java.util.Optional;

import net.paramount.domain.entity.general.Language;
import net.paramount.framework.service.GenericService;

public interface LanguageService extends GenericService<Language, Long>{
	Optional<Language> getByCode(String code);
	Language getByName(String name);
}
