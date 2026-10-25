package org.sakila.controllers.peliculas;

import java.util.List;
import org.sakila.dto.peliculas.CategoryDTO;
import org.sakila.entities.peliculas.Category;
import org.sakila.repositories.peliculas.CategoryRepository;
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
@RequestMapping("/api/categories")
@Transactional
public class CategoryController {
    private final CategoryRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public CategoryController(CategoryRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<CategoryDTO> listar() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{categoryId}")
    public CategoryDTO obtener(@PathVariable Byte categoryId) {
        Category entity = repository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category no encontrado"));
        return toDto(entity);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> crear(@RequestBody CategoryDTO dto) {
        Category entity = new Category();
        aplicar(dto, entity);
        Category saved = repository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(saved));
    }

    @PutMapping("/{categoryId}")
    public CategoryDTO actualizar(@PathVariable Byte categoryId, @RequestBody CategoryDTO dto) {
        Category entity = repository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category no encontrado"));
        aplicar(dto, entity);
        return toDto(repository.save(entity));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> eliminar(@PathVariable Byte categoryId) {
        Category entity = repository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category no encontrado"));
        repository.delete(entity);
        return ResponseEntity.noContent().build();
    }

    private CategoryDTO toDto(Category entity) {
        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryId(entity.getCategoryId());
        dto.setLastUpdate(entity.getLastUpdate());
        dto.setName(entity.getName());
        return dto;
    }

    private void aplicar(CategoryDTO dto, Category entity) {
        entity.setLastUpdate(dto.getLastUpdate());
        entity.setName(dto.getName());
    }
}
