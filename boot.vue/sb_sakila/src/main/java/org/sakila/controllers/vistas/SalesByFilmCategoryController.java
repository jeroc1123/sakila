package org.sakila.controllers.vistas;

import java.util.List;

import org.sakila.dto.vistas.SalesByFilmCategoryDTO;
import org.sakila.entities.vistas.SalesByFilmCategory;
import org.sakila.repositories.vistas.SalesByFilmCategoryRepository;
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
@RequestMapping("/api/views/sales-by-film-category")
@Transactional
public class SalesByFilmCategoryController {
    private final SalesByFilmCategoryRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public SalesByFilmCategoryController(SalesByFilmCategoryRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<SalesByFilmCategoryDTO> listar() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{category}")
    public SalesByFilmCategoryDTO obtener(@PathVariable String category) {
        SalesByFilmCategory entity = repository.findById(category)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "SalesByFilmCategory no encontrado"));
        return toDto(entity);
    }

    private SalesByFilmCategoryDTO toDto(SalesByFilmCategory entity) {
        SalesByFilmCategoryDTO dto = new SalesByFilmCategoryDTO();
        dto.setCategory(entity.getCategory());
        dto.setTotalSales(entity.getTotalSales());
        return dto;
    }
}
