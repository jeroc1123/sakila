package org.sakila.repositories.peliculas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sakila.entities.peliculas.Category;

public interface CategoryRepository extends JpaRepository<Category, Byte> {
}
