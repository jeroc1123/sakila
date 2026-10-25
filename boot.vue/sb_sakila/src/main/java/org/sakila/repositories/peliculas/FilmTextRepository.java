package org.sakila.repositories.peliculas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sakila.entities.peliculas.FilmText;

public interface FilmTextRepository extends JpaRepository<FilmText, Short> {
}
