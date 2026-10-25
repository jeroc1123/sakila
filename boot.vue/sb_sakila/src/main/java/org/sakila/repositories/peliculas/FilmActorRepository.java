package org.sakila.repositories.peliculas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sakila.entities.peliculas.FilmActor;
import org.sakila.entities.peliculas.FilmActorPK;

public interface FilmActorRepository extends JpaRepository<FilmActor, FilmActorPK> {
}
