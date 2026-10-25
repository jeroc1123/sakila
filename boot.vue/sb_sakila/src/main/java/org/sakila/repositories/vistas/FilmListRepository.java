package org.sakila.repositories.vistas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sakila.entities.vistas.FilmList;

public interface FilmListRepository extends JpaRepository<FilmList, Short> {
}
