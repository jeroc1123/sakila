package org.sakila.controllers.peliculas;

import java.util.List;
import org.sakila.dto.peliculas.FilmDTO;
import org.sakila.entities.peliculas.Film;
import org.sakila.repositories.peliculas.FilmRepository;
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
@RequestMapping("/api/films")
@Transactional
public class FilmController {
    private final FilmRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public FilmController(FilmRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<FilmDTO> listar() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{filmId}")
    public FilmDTO obtener(@PathVariable Short filmId) {
        Film entity = repository.findById(filmId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Film no encontrado"));
        return toDto(entity);
    }

    @PostMapping
    public ResponseEntity<FilmDTO> crear(@RequestBody FilmDTO dto) {
        Film entity = new Film();
        aplicar(dto, entity);
        Film saved = repository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(saved));
    }

    @PutMapping("/{filmId}")
    public FilmDTO actualizar(@PathVariable Short filmId, @RequestBody FilmDTO dto) {
        Film entity = repository.findById(filmId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Film no encontrado"));
        aplicar(dto, entity);
        return toDto(repository.save(entity));
    }

    @DeleteMapping("/{filmId}")
    public ResponseEntity<Void> eliminar(@PathVariable Short filmId) {
        Film entity = repository.findById(filmId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Film no encontrado"));
        repository.delete(entity);
        return ResponseEntity.noContent().build();
    }

    private FilmDTO toDto(Film entity) {
        FilmDTO dto = new FilmDTO();
        dto.setFilmId(entity.getFilmId());
        dto.setDescription(entity.getDescription());
        dto.setLastUpdate(entity.getLastUpdate());
        dto.setLength(entity.getLength());
        dto.setRating(entity.getRating());
        dto.setReleaseYear(entity.getReleaseYear());
        dto.setRentalDuration(entity.getRentalDuration());
        dto.setRentalRate(entity.getRentalRate());
        dto.setReplacementCost(entity.getReplacementCost());
        dto.setSpecialFeatures(entity.getSpecialFeatures());
        dto.setTitle(entity.getTitle());
        dto.setLanguageId(entity.getLanguage1() == null ? null : entity.getLanguage1().getLanguageId());
        dto.setOriginalLanguageId(entity.getLanguage2() == null ? null : entity.getLanguage2().getLanguageId());
        return dto;
    }

    private void aplicar(FilmDTO dto, Film entity) {
        entity.setDescription(dto.getDescription());
        entity.setLastUpdate(dto.getLastUpdate());
        entity.setLength(dto.getLength());
        entity.setRating(dto.getRating());
        entity.setReleaseYear(dto.getReleaseYear());
        entity.setRentalDuration(dto.getRentalDuration());
        entity.setRentalRate(dto.getRentalRate());
        entity.setReplacementCost(dto.getReplacementCost());
        entity.setSpecialFeatures(dto.getSpecialFeatures());
        entity.setTitle(dto.getTitle());
        if (dto.getLanguageId() == null) {
            entity.setLanguage1(null);
        } else {
            org.sakila.entities.peliculas.Language related = entityManager.find(org.sakila.entities.peliculas.Language.class, dto.getLanguageId());
            if (related == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "language1 relacionado no encontrado");
            }
            entity.setLanguage1(related);
        }
        if (dto.getOriginalLanguageId() == null) {
            entity.setLanguage2(null);
        } else {
            org.sakila.entities.peliculas.Language related = entityManager.find(org.sakila.entities.peliculas.Language.class, dto.getOriginalLanguageId());
            if (related == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "language2 relacionado no encontrado");
            }
            entity.setLanguage2(related);
        }
    }
}
