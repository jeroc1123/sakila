package org.sakila.entities.direcciones;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the city database table.
 * 
 */
@Entity
@Table(name = "City")
@NamedQuery(name="City.findAll", query="SELECT c FROM City c")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor 
public class City implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="city_id")
	private Short cityId;
	
	@Column(name="city")
	private String city;

	@Column(name="country_id")
	private Short countryId;

	@SuppressWarnings("deprecation")
	@Column(name="last_update")
	@Temporal(TemporalType.TIMESTAMP)
	private Timestamp lastUpdate;

}