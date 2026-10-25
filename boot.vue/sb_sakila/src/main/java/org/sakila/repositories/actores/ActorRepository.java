package org.sakila.repositories.actores;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.sakila.entities.actores.Actor;

public interface ActorRepository extends JpaRepository<Actor, Short> {

    @Query("SELECT a FROM Actor a WHERE a.firstName = ?1 AND a.lastName = ?2")
    Actor findByFirstNameAndLastName(String firstName, String lastName);
}