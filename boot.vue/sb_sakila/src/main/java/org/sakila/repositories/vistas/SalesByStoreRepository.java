package org.sakila.repositories.vistas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sakila.entities.vistas.SalesByStore;

public interface SalesByStoreRepository extends JpaRepository<SalesByStore, String> {
}
