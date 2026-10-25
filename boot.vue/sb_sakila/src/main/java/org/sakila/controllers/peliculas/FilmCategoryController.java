package org.sakila.controllers.peliculas;

import java.util.List;
import org.sakila.dto.peliculas.FilmCategoryDTO;
import org.sakila.entities.peliculas.FilmCategory;
import org.sakila.entities.peliculas.FilmCategoryPK;
import org.sakila.repositories.peliculas.FilmCategoryRepository;
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
@RequestMapping("/api/film-categories")
@Transactional
public class FilmCategoryController {
    private final FilmCategoryRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public FilmCategoryController(FilmCategoryRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<FilmCategoryDTO> listar() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{filmId}/{categoryId}")
    public FilmCategoryDTO obtener(@PathVariable Short filmId, @PathVariable Byte categoryId) {
        FilmCategory entity = repository.findById(new FilmCategoryPK(filmId, categoryId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FilmCategory no encontrado"));
        return toDto(entity);
    }

    @PostMapping
    public ResponseEntity<FilmCategoryDTO> crear(@RequestBody FilmCategoryDTO dto) {
        FilmCategory entity = new FilmCategory();
        aplicar(dto, entity);
        if (dto.getFilmId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "filmId es obligatorio");
        }
        if (dto.getCategoryId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "categoryId es obligatorio");
        }
        entity.setId(new FilmCategoryPK(dto.getFilmId(), dto.getCategoryId().byteValue()));
        FilmCategory saved = repository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(saved));
    }

    @PutMapping("/{filmId}/{categoryId}")
    public FilmCategoryDTO actualizar(@PathVariable Short filmId, @PathVariable Byte categoryId, @RequestBody FilmCategoryDTO dto) {
        FilmCategory entity = repository.findById(new FilmCategoryPK(filmId, categoryId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FilmCategory no encontrado"));
        if (!filmId.equals(dto.getFilmId()) || !categoryId.equals(dto.getCategoryId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La clave de FilmCategory no se puede modificar");
        }
        aplicar(dto, entity);
        return toDto(repository.save(entity));
    }

    @DeleteMapping("/{filmId}/{categoryId}")
    public ResponseEntity<Void> eliminar(@PathVariable Short filmId, @PathVariable Byte categoryId) {
        FilmCategory entity = repository.findById(new FilmCategoryPK(filmId, categoryId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FilmCategory no encontrado"));
        repository.delete(entity);
        return ResponseEntity.noContent().build();
    }

    private FilmCategoryDTO toDto(FilmCategory entity) {
        FilmCategoryDTO dto = new FilmCategoryDTO();
        dto.setLastUpdate(entity.getLastUpdate());
        dto.setCategoryId(entity.getCategory() == null ? null : entity.getCategory().getCategoryId());
        dto.setFilmId(entity.getFilm() == null ? null : entity.getFilm().getFilmId());
        dto.setFilmId(entity.getId() == null ? null : entity.getId().getFilmId());
        dto.setCategoryId(entity.getId() == null ? null : entity.getId().getCategoryId());
        return dto;
    }

    private void aplicar(FilmCategoryDTO dto, FilmCategory entity) {
        entity.setLastUpdate(dto.getLastUpdate());
        if (dto.getCategoryId() == null) {
            entity.setCategory(null);
        } else {
            org.sakila.entities.peliculas.Category related = entityManager.find(org.sakila.entities.peliculas.Category.class, dto.getCategoryId());
            if (related == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "category relacionado no encontrado");
            }
            entity.setCategory(related);
        }
        if (dto.getFilmId() == null) {
            entity.setFilm(null);
        } else {
            org.sakila.entities.peliculas.Film related = entityManager.find(org.sakila.entities.peliculas.Film.class, dto.getFilmId());
            if (related == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "film relacionado no encontrado");
            }
            entity.setFilm(related);
        }
    }
}
