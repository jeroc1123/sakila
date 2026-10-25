package org.sakila.repositories.direcciones;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sakila.entities.direcciones.Country;

public interface CountryRepository extends JpaRepository<Country, Short> {
}
