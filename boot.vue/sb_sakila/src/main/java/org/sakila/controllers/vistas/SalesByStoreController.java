package org.sakila.controllers.vistas;

import java.util.List;

import org.sakila.dto.vistas.SalesByStoreDTO;
import org.sakila.entities.vistas.SalesByStore;
import org.sakila.repositories.vistas.SalesByStoreRepository;
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
@RequestMapping("/api/views/sales-by-store")
@Transactional
public class SalesByStoreController {
    private final SalesByStoreRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public SalesByStoreController(SalesByStoreRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<SalesByStoreDTO> listar() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{manager}")
    public SalesByStoreDTO obtener(@PathVariable String manager) {
        SalesByStore entity = repository.findById(manager)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "SalesByStore no encontrado"));
        return toDto(entity);
    }

    private SalesByStoreDTO toDto(SalesByStore entity) {
        SalesByStoreDTO dto = new SalesByStoreDTO();
        dto.setManager(entity.getManager());
        dto.setStore(entity.getStore());
        dto.setTotalSales(entity.getTotalSales());
        return dto;
    }
}
