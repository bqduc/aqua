package net.paramount.css.repository.config;

import org.springframework.stereotype.Repository;

import net.paramount.domain.entity.general.Language;
import net.paramount.framework.repository.CodeNameRepository;

@Repository("languageRepository")
public interface LanguageRepository extends CodeNameRepository<Language, Long>{
}
