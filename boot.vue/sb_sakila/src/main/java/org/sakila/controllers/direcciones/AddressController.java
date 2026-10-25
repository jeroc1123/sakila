package org.sakila.controllers.direcciones;

import java.util.List;
import org.sakila.dto.direcciones.AddressDTO;
import org.sakila.entities.direcciones.Address;
import org.sakila.repositories.direcciones.AddressRepository;
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
@RequestMapping("/api/addresses")
@Transactional
public class AddressController {
    private final AddressRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public AddressController(AddressRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<AddressDTO> listar() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{addressId}")
    public AddressDTO obtener(@PathVariable Short addressId) {
        Address entity = repository.findById(addressId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address no encontrado"));
        return toDto(entity);
    }

    @PostMapping
    public ResponseEntity<AddressDTO> crear(@RequestBody AddressDTO dto) {
        Address entity = new Address();
        aplicar(dto, entity);
        Address saved = repository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(saved));
    }

    @PutMapping("/{addressId}")
    public AddressDTO actualizar(@PathVariable Short addressId, @RequestBody AddressDTO dto) {
        Address entity = repository.findById(addressId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address no encontrado"));
        aplicar(dto, entity);
        return toDto(repository.save(entity));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> eliminar(@PathVariable Short addressId) {
        Address entity = repository.findById(addressId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address no encontrado"));
        repository.delete(entity);
        return ResponseEntity.noContent().build();
    }

    private AddressDTO toDto(Address entity) {
        AddressDTO dto = new AddressDTO();
        dto.setAddressId(entity.getAddressId());
        dto.setAddress(entity.getAddress());
        dto.setAddress2(entity.getAddress2());
        dto.setCityId(entity.getCityId());
        dto.setDistrict(entity.getDistrict());
        dto.setLastUpdate(entity.getLastUpdate());
        dto.setLocation(entity.getLocation());
        dto.setPhone(entity.getPhone());
        dto.setPostalCode(entity.getPostalCode());
        return dto;
    }

    private void aplicar(AddressDTO dto, Address entity) {
        entity.setAddress(dto.getAddress());
        entity.setAddress2(dto.getAddress2());
        entity.setCityId(dto.getCityId());
        entity.setDistrict(dto.getDistrict());
        entity.setLastUpdate(dto.getLastUpdate());
        entity.setLocation(dto.getLocation());
        entity.setPhone(dto.getPhone());
        entity.setPostalCode(dto.getPostalCode());
    }
}
