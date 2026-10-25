package org.sakila.entities.peliculas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.sakila.entities.ventas.Inventory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the film database table.
 * 
 */
@Entity
@Table(name = "Film")
@NamedQuery(name="Film.findAll", query="SELECT f FROM Film f")
@Getter
@Setter
@AllArgsConstructor
public class Film implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="film_id")
	private Short filmId;

	@Lob
	@Column(name="description")
	private String description;

	@SuppressWarnings("deprecation")
	@Column(name="last_update")
	@Temporal(TemporalType.TIMESTAMP)
	private Timestamp lastUpdate;

	@Column(name="length")
	private BigInteger length;

	@Column(name="rating")
	private String rating;

	@SuppressWarnings("deprecation")
	@Temporal(TemporalType.DATE)
	@Column(name="release_year")
	private Date releaseYear;

	@Column(name="rental_duration")
	private BigInteger rentalDuration;

	@Column(name="rental_rate")
	private BigDecimal rentalRate;

	@Column(name="replacement_cost")
	private BigDecimal replacementCost;

	@Column(name="special_features")
	private String specialFeatures;

	@Column(name="title")
	private String title;

	//bi-directional many-to-one association to Language
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="language_id")
	private Language language1;

	//bi-directional many-to-one association to Language
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="original_language_id")
	private Language language2;

	//bi-directional many-to-one association to FilmActor
	
	@OneToMany(mappedBy="film")
	private List<FilmActor> filmActors;

	//bi-directional many-to-one association to FilmCategory
	@OneToMany(mappedBy="film")
	private List<FilmCategory> filmCategories;

	//bi-directional many-to-one association to Inventory
	
	@OneToMany(mappedBy="film")
	private List<Inventory> inventories;

	public Film() {
	}

	public FilmActor addFilmActor(FilmActor filmActor) {
		getFilmActors().add(filmActor);
		filmActor.setFilm(this);

		return filmActor;
	}

	public FilmActor removeFilmActor(FilmActor filmActor) {
		getFilmActors().remove(filmActor);
		filmActor.setFilm(null);

		return filmActor;
	}

	public FilmCategory addFilmCategory(FilmCategory filmCategory) {
		getFilmCategories().add(filmCategory);
		filmCategory.setFilm(this);

		return filmCategory;
	}

	public FilmCategory removeFilmCategory(FilmCategory filmCategory) {
		getFilmCategories().remove(filmCategory);
		filmCategory.setFilm(null);

		return filmCategory;
	}

	public Inventory addInventory(Inventory inventory) {
		getInventories().add(inventory);
		inventory.setFilm(this);

		return inventory;
	}

	public Inventory removeInventory(Inventory inventory) {
		getInventories().remove(inventory);
		inventory.setFilm(null);

		return inventory;
	}
}