package org.sakila.repositories.ventas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sakila.entities.ventas.Rental;

public interface RentalRepository extends JpaRepository<Rental, Integer> {
}
