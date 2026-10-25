package org.sakila.repositories.ventas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sakila.entities.ventas.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
