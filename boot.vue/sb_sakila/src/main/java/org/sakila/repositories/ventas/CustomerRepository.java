package org.sakila.repositories.ventas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sakila.entities.ventas.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Short> {
}
