package org.sakila.repositories.vistas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sakila.entities.vistas.StaffList;

public interface StaffListRepository extends JpaRepository<StaffList, Byte> {
}
