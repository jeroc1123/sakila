package org.sakila.repositories.vistas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sakila.entities.vistas.CustomerList;

public interface CustomerListRepository extends JpaRepository<CustomerList, Short> {
}
