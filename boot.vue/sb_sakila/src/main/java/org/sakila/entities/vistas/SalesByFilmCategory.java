package org.sakila.entities.vistas;

import java.io.Serializable;
import java.math.BigDecimal;

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
 * The persistent class for the sales_by_film_category database table.
 * 
 */
@Entity
@Table(name="sales_by_film_category")
@NamedQuery(name="SalesByFilmCategory.findAll", query="SELECT s FROM SalesByFilmCategory s")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalesByFilmCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="category")
	private String category;

	@Column(name="total_sales")
	private BigDecimal totalSales;

}