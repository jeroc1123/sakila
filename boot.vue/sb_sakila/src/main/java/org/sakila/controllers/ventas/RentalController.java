package org.sakila.controllers.ventas;

import java.util.List;
import org.sakila.dto.ventas.RentalDTO;
import org.sakila.entities.ventas.Rental;
import org.sakila.repositories.ventas.RentalRepository;
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
@RequestMapping("/api/rentals")
@Transactional
public class RentalController {
    private final RentalRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public RentalController(RentalRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<RentalDTO> listar() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{rentalId}")
    public RentalDTO obtener(@PathVariable Integer rentalId) {
        Rental entity = repository.findById(rentalId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rental no encontrado"));
        return toDto(entity);
    }

    @PostMapping
    public ResponseEntity<RentalDTO> crear(@RequestBody RentalDTO dto) {
        Rental entity = new Rental();
        aplicar(dto, entity);
        Rental saved = repository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(saved));
    }

    @PutMapping("/{rentalId}")
    public RentalDTO actualizar(@PathVariable Integer rentalId, @RequestBody RentalDTO dto) {
        Rental entity = repository.findById(rentalId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rental no encontrado"));
        aplicar(dto, entity);
        return toDto(repository.save(entity));
    }

    @DeleteMapping("/{rentalId}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer rentalId) {
        Rental entity = repository.findById(rentalId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rental no encontrado"));
        repository.delete(entity);
        return ResponseEntity.noContent().build();
    }

    private RentalDTO toDto(Rental entity) {
        RentalDTO dto = new RentalDTO();
        dto.setRentalId(entity.getRentalId());
        dto.setLastUpdate(entity.getLastUpdate());
        dto.setRentalDate(entity.getRentalDate());
        dto.setReturnDate(entity.getReturnDate());
        dto.setCustomerId(entity.getCustomer() == null ? null : entity.getCustomer().getCustomerId());
        dto.setInventoryId(entity.getInventory() == null ? null : entity.getInventory().getInventoryId());
        dto.setStaffId(entity.getStaff() == null ? null : entity.getStaff().getStaffId());
        return dto;
    }

    private void aplicar(RentalDTO dto, Rental entity) {
        entity.setLastUpdate(dto.getLastUpdate());
        entity.setRentalDate(dto.getRentalDate());
        entity.setReturnDate(dto.getReturnDate());
        if (dto.getCustomerId() == null) {
            entity.setCustomer(null);
        } else {
            org.sakila.entities.ventas.Customer related = entityManager.find(org.sakila.entities.ventas.Customer.class, dto.getCustomerId());
            if (related == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "customer relacionado no encontrado");
            }
            entity.setCustomer(related);
        }
        if (dto.getInventoryId() == null) {
            entity.setInventory(null);
        } else {
            org.sakila.entities.ventas.Inventory related = entityManager.find(org.sakila.entities.ventas.Inventory.class, dto.getInventoryId());
            if (related == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "inventory relacionado no encontrado");
            }
            entity.setInventory(related);
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
