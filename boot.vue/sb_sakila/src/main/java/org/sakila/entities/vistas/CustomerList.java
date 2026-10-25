package org.sakila.entities.vistas;

import java.io.Serializable;

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
 * The persistent class for the customer_list database table.
 * 
 */
@Entity
@Table(name="customer_list")
@NamedQuery(name="CustomerList.findAll", query="SELECT c FROM CustomerList c")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerList implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	private Short id;
	
	@Column(name="address")
	private String address;
	
	@Column(name="city")
	private String city;
	
	@Column(name="country")
	private String country;

	@Column(name="name")
	private String name;

	@Column(name="notes")
	private String notes;

	@Column(name="phone")
	private String phone;

	@Column(name="sid")
	private byte sid;

	@Column(name="`zip code`")
	private String zip_code;
}