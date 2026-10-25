package org.sakila.entities.vistas;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the actor_info database table.
 * 
 */
@Entity
@Table(name="actor_info")
@NamedQuery(name="ActorInfo.findAll", query="SELECT a FROM ActorInfo a")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActorInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="actor_id")
	private Short actorId;

	@Lob
	@Column(name="film_info")
	private String filmInfo;

	@Column(name="first_name")
	private String firstName;

	@Column(name="last_name")
	private String lastName;

}