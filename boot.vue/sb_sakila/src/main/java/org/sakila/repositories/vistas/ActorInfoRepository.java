package org.sakila.repositories.vistas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sakila.entities.vistas.ActorInfo;

public interface ActorInfoRepository extends JpaRepository<ActorInfo, Short> {
}
