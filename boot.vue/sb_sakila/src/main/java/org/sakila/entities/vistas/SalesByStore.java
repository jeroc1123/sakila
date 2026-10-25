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
 * The persistent class for the sales_by_store database table.
 * 
 */
@Entity
@Table(name="sales_by_store")
@NamedQuery(name="SalesByStore.findAll", query="SELECT s FROM SalesByStore s")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalesByStore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="manager")
	private String manager;

	@Column(name="store")
	private String store;

	@Column(name="total_sales")
	private BigDecimal totalSales;

}