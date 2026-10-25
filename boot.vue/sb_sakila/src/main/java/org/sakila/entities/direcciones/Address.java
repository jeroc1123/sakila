package org.sakila.entities.direcciones;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.sakila.entities.ventas.Customer;
import org.sakila.entities.ventas.Staff;
import org.sakila.entities.ventas.Store;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the address database table.
 * 
 */
@Entity
@Table(name = "Address")
@NamedQuery(name="Address.findAll", query="SELECT a FROM Address a")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor 
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="address_id")
	private Short addressId;

	@Column(name="address")
	private String address;

	@Column(name="address2")
	private String address2;

	@Column(name="city_id")
	private Short cityId;

	@Column(name="district")
	private String district;

	@Column(name="last_update")
	private Timestamp lastUpdate;

	@Column(name="location", columnDefinition = "GEOMETRY")
	private byte[] location;

	@Column(name="phone")
	private String phone;

	@Column(name="postal_code")
	private String postalCode;

	//bi-directional many-to-one association to Customer
	
	@OneToMany(mappedBy="address")
	private List<Customer> customers;

	//bi-directional many-to-one association to Staff
	@OneToMany(mappedBy="address")
	private List<Staff> staffs;

	//bi-directional many-to-one association to Store
	@OneToMany(mappedBy="address")
	private List<Store> stores;

	public Customer addCustomer(Customer customer) {
		getCustomers().add(customer);
		customer.setAddress(this);

		return customer;
	}

	public Customer removeCustomer(Customer customer) {
		getCustomers().remove(customer);
		customer.setAddress(null);

		return customer;
	}

	public Staff addStaff(Staff staff) {
		getStaffs().add(staff);
		staff.setAddress(this);

		return staff;
	}

	public Staff removeStaff(Staff staff) {
		getStaffs().remove(staff);
		staff.setAddress(null);

		return staff;
	}

	public Store addStore(Store store) {
		getStores().add(store);
		store.setAddress(this);

		return store;
	}

	public Store removeStore(Store store) {
		getStores().remove(store);
		store.setAddress(null);

		return store;
	}
}