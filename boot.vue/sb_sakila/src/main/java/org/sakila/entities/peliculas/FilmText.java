package org.sakila.entities.peliculas;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the film_text database table.
 * 
 */
@Entity
@Table(name = "film_text")
//@NamedQuery(name = "FilmText.findAll", query = "SELECT f FROM film_text f")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor 
public class FilmText implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="film_id")
	private Short filmId;

	@Column(name="description")
	private String description;

	@Column(name="title")
	private String title;
}