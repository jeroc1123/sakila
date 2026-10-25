package org.sakila.controllers.peliculas;

import java.util.List;
import org.sakila.dto.peliculas.FilmTextDTO;
import org.sakila.entities.peliculas.FilmText;
import org.sakila.repositories.peliculas.FilmTextRepository;
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
@RequestMapping("/api/film-texts")
@Transactional
public class FilmTextController {
    private final FilmTextRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public FilmTextController(FilmTextRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<FilmTextDTO> listar() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{filmId}")
    public FilmTextDTO obtener(@PathVariable Short filmId) {
        FilmText entity = repository.findById(filmId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FilmText no encontrado"));
        return toDto(entity);
    }

    @PostMapping
    public ResponseEntity<FilmTextDTO> crear(@RequestBody FilmTextDTO dto) {
        FilmText entity = new FilmText();
        aplicar(dto, entity);
        entity.setFilmId(dto.getFilmId());
        FilmText saved = repository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(saved));
    }

    @PutMapping("/{filmId}")
    public FilmTextDTO actualizar(@PathVariable Short filmId, @RequestBody FilmTextDTO dto) {
        FilmText entity = repository.findById(filmId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FilmText no encontrado"));
        aplicar(dto, entity);
        return toDto(repository.save(entity));
    }

    @DeleteMapping("/{filmId}")
    public ResponseEntity<Void> eliminar(@PathVariable Short filmId) {
        FilmText entity = repository.findById(filmId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FilmText no encontrado"));
        repository.delete(entity);
        return ResponseEntity.noContent().build();
    }

    private FilmTextDTO toDto(FilmText entity) {
        FilmTextDTO dto = new FilmTextDTO();
        dto.setFilmId(entity.getFilmId());
        dto.setDescription(entity.getDescription());
        dto.setTitle(entity.getTitle());
        return dto;
    }

    private void aplicar(FilmTextDTO dto, FilmText entity) {
        entity.setDescription(dto.getDescription());
        entity.setTitle(dto.getTitle());
    }
}
