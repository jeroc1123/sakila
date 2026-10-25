package org.sakila.repositories.peliculas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sakila.entities.peliculas.Film;

public interface FilmRepository extends JpaRepository<Film, Short> {
}
