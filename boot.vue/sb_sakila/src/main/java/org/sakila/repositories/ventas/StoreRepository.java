package org.sakila.repositories.ventas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sakila.entities.ventas.Store;

public interface StoreRepository extends JpaRepository<Store, Byte> {
}
