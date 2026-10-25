package org.sakila.controllers.ventas;

import java.util.List;
import org.sakila.dto.ventas.CustomerDTO;
import org.sakila.entities.ventas.Customer;
import org.sakila.repositories.ventas.CustomerRepository;
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
@RequestMapping("/api/customers")
@Transactional
public class CustomerController {
    private final CustomerRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<CustomerDTO> listar() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{customerId}")
    public CustomerDTO obtener(@PathVariable Short customerId) {
        Customer entity = repository.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer no encontrado"));
        return toDto(entity);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> crear(@RequestBody CustomerDTO dto) {
        Customer entity = new Customer();
        aplicar(dto, entity);
        Customer saved = repository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(saved));
    }

    @PutMapping("/{customerId}")
    public CustomerDTO actualizar(@PathVariable Short customerId, @RequestBody CustomerDTO dto) {
        Customer entity = repository.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer no encontrado"));
        aplicar(dto, entity);
        return toDto(repository.save(entity));
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> eliminar(@PathVariable Short customerId) {
        Customer entity = repository.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer no encontrado"));
        repository.delete(entity);
        return ResponseEntity.noContent().build();
    }

    private CustomerDTO toDto(Customer entity) {
        CustomerDTO dto = new CustomerDTO();
        dto.setCustomerId(entity.getCustomerId());
        dto.setActive(entity.getActive());
        dto.setCreateDate(entity.getCreateDate());
        dto.setEmail(entity.getEmail());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setLastUpdate(entity.getLastUpdate());
        dto.setAddressId(entity.getAddress() == null ? null : entity.getAddress().getAddressId());
        dto.setStoreId(entity.getStore() == null ? null : entity.getStore().getStoreId());
        return dto;
    }

    private void aplicar(CustomerDTO dto, Customer entity) {
        entity.setActive(dto.getActive());
        entity.setCreateDate(dto.getCreateDate());
        entity.setEmail(dto.getEmail());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
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
