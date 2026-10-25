package org.sakila.entities.peliculas;

import java.io.Serializable;
import java.sql.Timestamp;

import org.sakila.entities.actores.Actor;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the film_actor database table.
 * 
 */
@Entity
@Table(name="film_actor")
@NamedQuery(name="FilmActor.findAll", query="SELECT f FROM FilmActor f")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FilmActor implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FilmActorPK id;

	@SuppressWarnings("deprecation")
	@Column(name="last_update")
	@Temporal(TemporalType.TIMESTAMP)
	private Timestamp lastUpdate;

	//bi-directional many-to-one association to Actor
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="actor_id")
	private Actor actor;

	//bi-directional many-to-one association to Film
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="film_id")
	private Film film;

}