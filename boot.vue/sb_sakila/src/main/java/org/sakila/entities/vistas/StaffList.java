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
 * The persistent class for the staff_list database table.
 * 
 */
@Entity
@Table(name="staff_list")
@NamedQuery(name="StaffList.findAll", query="SELECT s FROM StaffList s")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StaffList implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="address")
	private String address;

	@Column(name="city")
	private String city;

	@Column(name="country")
	private String country;

	@Id
	@Column(name="id")
	private Byte id;

	@Column(name="name")
	private String name;

	@Column(name="phone")
	private String phone;

	@Column(name="sid")
	private byte sid;

	@Column(name="`zip code`")
	private String zip_code;
}