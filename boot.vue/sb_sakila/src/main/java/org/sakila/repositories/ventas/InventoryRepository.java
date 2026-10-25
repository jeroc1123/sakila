package org.sakila.repositories.ventas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sakila.entities.ventas.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
}
