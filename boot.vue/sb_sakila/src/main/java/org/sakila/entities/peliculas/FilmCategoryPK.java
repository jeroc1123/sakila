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
 * The primary key class for the film_category database table.
 * 
 */
@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor 
public class FilmCategoryPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="film_id", insertable=false, updatable=false)
	private Short filmId;

	@Column(name="category_id", insertable=false, updatable=false)
	private byte categoryId;

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FilmCategoryPK)) {
			return false;
		}
		FilmCategoryPK castOther = (FilmCategoryPK)other;
		return Objects.equals(filmId, castOther.filmId)
				&& categoryId == castOther.categoryId;
	}

	public int hashCode() {
		return Objects.hash(filmId, categoryId);
	}
}