package org.sakila.entities.vistas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the film_list database table.
 * 
 */
@Entity
@Table(name="film_list")
@NamedQuery(name="FilmList.findAll", query="SELECT f FROM FilmList f")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FilmList implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="fid")
	private Short fid;

	@Column(name="actors")
	private String actors;

	@Column(name="category")
	private String category;

	@Column(name="description")
	private String description;

	@Column(name="length")
	private BigInteger length;

	@Column(name="price")
	private BigDecimal price;

	@Column(name="rating")
	private String rating;

	@Column(name="title")
	private String title;

}