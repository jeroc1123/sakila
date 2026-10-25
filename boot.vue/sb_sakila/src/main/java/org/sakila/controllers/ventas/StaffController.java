package org.sakila.controllers.ventas;

import java.util.List;
import org.sakila.dto.ventas.StaffDTO;
import org.sakila.entities.ventas.Staff;
import org.sakila.repositories.ventas.StaffRepository;
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
@RequestMapping("/api/staff")
@Transactional
public class StaffController {
    private final StaffRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public StaffController(StaffRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<StaffDTO> listar() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{staffId}")
    public StaffDTO obtener(@PathVariable Byte staffId) {
        Staff entity = repository.findById(staffId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Staff no encontrado"));
        return toDto(entity);
    }

    @PostMapping
    public ResponseEntity<StaffDTO> crear(@RequestBody StaffDTO dto) {
        Staff entity = new Staff();
        aplicar(dto, entity);
        Staff saved = repository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(saved));
    }

    @PutMapping("/{staffId}")
    public StaffDTO actualizar(@PathVariable Byte staffId, @RequestBody StaffDTO dto) {
        Staff entity = repository.findById(staffId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Staff no encontrado"));
        aplicar(dto, entity);
        return toDto(repository.save(entity));
    }

    @DeleteMapping("/{staffId}")
    public ResponseEntity<Void> eliminar(@PathVariable Byte staffId) {
        Staff entity = repository.findById(staffId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Staff no encontrado"));
        repository.delete(entity);
        return ResponseEntity.noContent().build();
    }

    private StaffDTO toDto(Staff entity) {
        StaffDTO dto = new StaffDTO();
        dto.setStaffId(entity.getStaffId());
        dto.setActive(entity.getActive());
        dto.setEmail(entity.getEmail());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setLastUpdate(entity.getLastUpdate());
        dto.setPassword(entity.getPassword());
        dto.setPicture(entity.getPicture());
        dto.setUsername(entity.getUsername());
        dto.setAddressId(entity.getAddress() == null ? null : entity.getAddress().getAddressId());
        dto.setStoreId(entity.getStore() == null ? null : entity.getStore().getStoreId());
        return dto;
    }

    private void aplicar(StaffDTO dto, Staff entity) {
        entity.setActive(dto.getActive());
        entity.setEmail(dto.getEmail());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setLastUpdate(dto.getLastUpdate());
        entity.setPassword(dto.getPassword());
        entity.setPicture(dto.getPicture());
        entity.setUsername(dto.getUsername());
        if (dto.getAddressId() == null) {
            entity.setAddress(null);
        } else {
            org.sakila.entities.direcciones.Address related = entityManager.find(org.sakila.entities.direcciones.Address.class, dto.getAddressId());
            if (related == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "address relacionado no encontrado");
            }
            entity.setAddress(related);
        }
        if (dto.getStoreId() == null) {
            entity.setStore(null);
        } else {
            org.sakila.entities.ventas.Store related = entityManager.find(org.sakila.entities.ventas.Store.class, dto.getStoreId());
            if (related == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "store relacionado no encontrado");
            }
            entity.setStore(related);
        }
    }
}
