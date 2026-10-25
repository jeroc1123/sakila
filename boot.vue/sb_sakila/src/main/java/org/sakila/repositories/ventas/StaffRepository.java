package org.sakila.repositories.ventas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sakila.entities.ventas.Staff;

public interface StaffRepository extends JpaRepository<Staff, Byte> {
}
