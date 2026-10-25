package org.sakila.entities.peliculas;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The primary key class for the film_actor database table.
 * 
 */
@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor 
public class FilmActorPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="actor_id", insertable=false, updatable=false)
	private Short actorId;

	@Column(name="film_id", insertable=false, updatable=false)
	private Short filmId;

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FilmActorPK)) {
			return false;
		}
		FilmActorPK castOther = (FilmActorPK)other;
		return Objects.equals(actorId, castOther.actorId)
				&& Objects.equals(filmId, castOther.filmId);
	}

	public int hashCode() {
		return Objects.hash(actorId, filmId);
	}
}