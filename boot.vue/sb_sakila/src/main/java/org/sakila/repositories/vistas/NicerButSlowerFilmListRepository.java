package org.sakila.repositories.vistas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sakila.entities.vistas.NicerButSlowerFilmList;

public interface NicerButSlowerFilmListRepository extends JpaRepository<NicerButSlowerFilmList, Short> {
}
