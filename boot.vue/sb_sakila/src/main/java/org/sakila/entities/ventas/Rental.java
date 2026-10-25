package org.sakila.entities.ventas;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the rental database table.
 * 
 */
@Entity
@Table(name = "Rental")
@NamedQuery(name="Rental.findAll", query="SELECT r FROM Rental r")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor 
public class Rental implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="rental_id")
	private Integer rentalId;

	@SuppressWarnings("deprecation")
	@Column(name="last_update")
	@Temporal(TemporalType.TIMESTAMP)
	private Timestamp lastUpdate;

	@SuppressWarnings("deprecation")
	@Column(name="rental_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date rentalDate;

	@SuppressWarnings("deprecation")
	@Column(name="return_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date returnDate;

	//bi-directional many-to-one association to Payment
	@OneToMany(mappedBy="rental")
	private List<Payment> payments;

	//bi-directional many-to-one association to Customer
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="customer_id")
	private Customer customer;

	//bi-directional many-to-one association to Inventory
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="inventory_id")
	private Inventory inventory;

	//bi-directional many-to-one association to Staff
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="staff_id")
	private Staff staff;

	public Payment addPayment(Payment payment) {
		getPayments().add(payment);
		payment.setRental(this);

		return payment;
	}

	public Payment removePayment(Payment payment) {
		getPayments().remove(payment);
		payment.setRental(null);

		return payment;
	}
}