package org.sakila.repositories.direcciones;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sakila.entities.direcciones.City;

public interface CityRepository extends JpaRepository<City, Short> {
}
