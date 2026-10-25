package org.sakila.entities.peliculas;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the language database table.
 * 
 */
@Entity
@Table(name = "Language")
@NamedQuery(name="Language.findAll", query="SELECT l FROM Language l")
@Getter
@Setter
@AllArgsConstructor
public class Language implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="language_id")
	private Byte languageId;

	@SuppressWarnings("deprecation")
	@Column(name="last_update")
	@Temporal(TemporalType.TIMESTAMP)
	private Timestamp lastUpdate;

	@Column(name="name")
	private String name;

	//bi-directional many-to-one association to Film
	@OneToMany(mappedBy="language1")
	private List<Film> films1;

	//bi-directional many-to-one association to Film
	@OneToMany(mappedBy="language2")
	private List<Film> films2;

	public Language() {
	}

	public Film addFilms1(Film films1) {
		getFilms1().add(films1);
		films1.setLanguage1(this);

		return films1;
	}

	public Film removeFilms1(Film films1) {
		getFilms1().remove(films1);
		films1.setLanguage1(null);

		return films1;
	}

	public Film addFilms2(Film films2) {
		getFilms2().add(films2);
		films2.setLanguage2(this);

		return films2;
	}

	public Film removeFilms2(Film films2) {
		getFilms2().remove(films2);
		films2.setLanguage2(null);

		return films2;
	}
}