package org.sakila.repositories.direcciones;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sakila.entities.direcciones.Address;

public interface AddressRepository extends JpaRepository<Address, Short> {
}
