package org.sakila.controllers.vistas;

import java.util.List;

import org.sakila.dto.vistas.CustomerListDTO;
import org.sakila.entities.vistas.CustomerList;
import org.sakila.repositories.vistas.CustomerListRepository;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@RestController
@RequestMapping("/api/views/customer-list")
@Transactional
public class CustomerListController {
    private final CustomerListRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public CustomerListController(CustomerListRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<CustomerListDTO> listar() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{id}")
    public CustomerListDTO obtener(@PathVariable Short id) {
        CustomerList entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "CustomerList no encontrado"));
        return toDto(entity);
    }

    private CustomerListDTO toDto(CustomerList entity) {
        CustomerListDTO dto = new CustomerListDTO();
        dto.setId(entity.getId());
        dto.setAddress(entity.getAddress());
        dto.setCity(entity.getCity());
        dto.setCountry(entity.getCountry());
        dto.setName(entity.getName());
        dto.setNotes(entity.getNotes());
        dto.setPhone(entity.getPhone());
        dto.setSid(entity.getSid());
        dto.setZip_code(entity.getZip_code());
        return dto;
    }
}
