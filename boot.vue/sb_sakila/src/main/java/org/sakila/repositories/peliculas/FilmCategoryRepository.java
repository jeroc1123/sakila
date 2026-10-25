package org.sakila.repositories.peliculas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sakila.entities.peliculas.FilmCategory;
import org.sakila.entities.peliculas.FilmCategoryPK;

public interface FilmCategoryRepository extends JpaRepository<FilmCategory, FilmCategoryPK> {
}
