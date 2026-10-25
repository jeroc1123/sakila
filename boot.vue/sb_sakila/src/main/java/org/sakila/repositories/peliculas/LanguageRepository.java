package org.sakila.repositories.peliculas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sakila.entities.peliculas.Language;

public interface LanguageRepository extends JpaRepository<Language, Byte> {
}
