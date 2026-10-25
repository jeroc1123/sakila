package org.sakila.repositories.vistas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sakila.entities.vistas.SalesByFilmCategory;

public interface SalesByFilmCategoryRepository extends JpaRepository<SalesByFilmCategory, String> {
}
