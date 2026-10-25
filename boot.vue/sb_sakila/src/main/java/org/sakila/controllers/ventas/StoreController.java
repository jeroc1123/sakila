package org.sakila.controllers.ventas;

import java.util.List;
import org.sakila.dto.ventas.StoreDTO;
import org.sakila.entities.ventas.Store;
import org.sakila.repositories.ventas.StoreRepository;
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
@RequestMapping("/api/stores")
@Transactional
public class StoreController {
    private final StoreRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public StoreController(StoreRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<StoreDTO> listar() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{storeId}")
    public StoreDTO obtener(@PathVariable Byte storeId) {
        Store entity = repository.findById(storeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Store no encontrado"));
        return toDto(entity);
    }

    @PostMapping
    public ResponseEntity<StoreDTO> crear(@RequestBody StoreDTO dto) {
        Store entity = new Store();
        aplicar(dto, entity);
        Store saved = repository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(saved));
    }

    @PutMapping("/{storeId}")
    public StoreDTO actualizar(@PathVariable Byte storeId, @RequestBody StoreDTO dto) {
        Store entity = repository.findById(storeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Store no encontrado"));
        aplicar(dto, entity);
        return toDto(repository.save(entity));
    }

    @DeleteMapping("/{storeId}")
    public ResponseEntity<Void> eliminar(@PathVariable Byte storeId) {
        Store entity = repository.findById(storeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Store no encontrado"));
        repository.delete(entity);
        return ResponseEntity.noContent().build();
    }

    private StoreDTO toDto(Store entity) {
        StoreDTO dto = new StoreDTO();
        dto.setStoreId(entity.getStoreId());
        dto.setLastUpdate(entity.getLastUpdate());
        dto.setAddressId(entity.getAddress() == null ? null : entity.getAddress().getAddressId());
        dto.setManagerStaffId(entity.getStaff() == null ? null : entity.getStaff().getStaffId());
        return dto;
    }

    private void aplicar(StoreDTO dto, Store entity) {
        entity.setLastUpdate(dto.getLastUpdate());
        if (dto.getAddressId() == null) {
            entity.setAddress(null);
        } else {
            org.sakila.entities.direcciones.Address related = entityManager.find(org.sakila.entities.direcciones.Address.class, dto.getAddressId());
            if (related == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "address relacionado no encontrado");
            }
            entity.setAddress(related);
        }
        if (dto.getManagerStaffId() == null) {
            entity.setStaff(null);
        } else {
            org.sakila.entities.ventas.Staff related = entityManager.find(org.sakila.entities.ventas.Staff.class, dto.getManagerStaffId());
            if (related == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "staff relacionado no encontrado");
            }
            entity.setStaff(related);
        }
    }
}
