package org.sakila.controllers.ventas;

import java.util.List;
import org.sakila.dto.ventas.PaymentDTO;
import org.sakila.entities.ventas.Payment;
import org.sakila.repositories.ventas.PaymentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@RestController
@RequestMapping("/api/payments")
@Transactional
public class PaymentController {
    private final PaymentRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public PaymentController(PaymentRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<PaymentDTO> listar() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{paymentId}")
    public PaymentDTO obtener(@PathVariable Long paymentId) {
        Payment entity = repository.findById(paymentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment no encontrado"));
        return toDto(entity);
    }

    @PostMapping
    public ResponseEntity<PaymentDTO> crear(@RequestBody PaymentDTO dto) {
        Payment entity = new Payment();
        aplicar(dto, entity);
        Payment saved = repository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(saved));
    }

    @PutMapping("/{paymentId}")
    public PaymentDTO actualizar(@PathVariable Long paymentId, @RequestBody PaymentDTO dto) {
        Payment entity = repository.findById(paymentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment no encontrado"));
        aplicar(dto, entity);
        return toDto(repository.save(entity));
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<Void> eliminar(@PathVariable Long paymentId) {
        Payment entity = repository.findById(paymentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment no encontrado"));
        repository.delete(entity);
        return ResponseEntity.noContent().build();
    }

    private PaymentDTO toDto(Payment entity) {
        PaymentDTO dto = new PaymentDTO();
        dto.setPaymentId(entity.getPaymentId());
        dto.setAmount(entity.getAmount());
        dto.setLastUpdate(entity.getLastUpdate());
        dto.setPaymentDate(entity.getPaymentDate());
        dto.setCustomerId(entity.getCustomer() == null ? null : entity.getCustomer().getCustomerId());
        dto.setRentalId(entity.getRental() == null ? null : entity.getRental().getRentalId());
        dto.setStaffId(entity.getStaff() == null ? null : entity.getStaff().getStaffId());
        return dto;
    }

    private void aplicar(PaymentDTO dto, Payment entity) {
        entity.setAmount(dto.getAmount());
        entity.setLastUpdate(dto.getLastUpdate());
        entity.setPaymentDate(dto.getPaymentDate());
        if (dto.getCustomerId() == null) {
            entity.setCustomer(null);
        } else {
            org.sakila.entities.ventas.Customer related = entityManager.find(org.sakila.entities.ventas.Customer.class, dto.getCustomerId());
            if (related == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "customer relacionado no encontrado");
            }
            entity.setCustomer(related);
        }
        if (dto.getRentalId() == null) {
            entity.setRental(null);
        } else {
            org.sakila.entities.ventas.Rental related = entityManager.find(org.sakila.entities.ventas.Rental.class, dto.getRentalId());
            if (related == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "rental relacionado no encontrado");
            }
            entity.setRental(related);
        }
        if (dto.getStaffId() == null) {
            entity.setStaff(null);
        } else {
            org.sakila.entities.ventas.Staff related = entityManager.find(org.sakila.entities.ventas.Staff.class, dto.getStaffId());
            if (related == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "staff relacionado no encontrado");
            }
            entity.setStaff(related);
        }
    }
}
