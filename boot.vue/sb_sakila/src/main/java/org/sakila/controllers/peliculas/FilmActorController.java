package org.sakila.controllers.peliculas;

import java.util.List;
import org.sakila.dto.peliculas.FilmActorDTO;
import org.sakila.entities.peliculas.FilmActor;
import org.sakila.entities.peliculas.FilmActorPK;
import org.sakila.repositories.peliculas.FilmActorRepository;
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
@RequestMapping("/api/film-actors")
@Transactional
public class FilmActorController {
    private final FilmActorRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public FilmActorController(FilmActorRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<FilmActorDTO> listar() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{actorId}/{filmId}")
    public FilmActorDTO obtener(@PathVariable Short actorId, @PathVariable Short filmId) {
        FilmActor entity = repository.findById(new FilmActorPK(actorId, filmId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FilmActor no encontrado"));
        return toDto(entity);
    }

    @PostMapping
    public ResponseEntity<FilmActorDTO> crear(@RequestBody FilmActorDTO dto) {
        FilmActor entity = new FilmActor();
        aplicar(dto, entity);
        if (dto.getActorId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "actorId es obligatorio");
        }
        if (dto.getFilmId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "filmId es obligatorio");
        }
        entity.setId(new FilmActorPK(dto.getActorId(), dto.getFilmId()));
        FilmActor saved = repository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(saved));
    }

    @PutMapping("/{actorId}/{filmId}")
    public FilmActorDTO actualizar(@PathVariable Short actorId, @PathVariable Short filmId, @RequestBody FilmActorDTO dto) {
        FilmActor entity = repository.findById(new FilmActorPK(actorId, filmId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FilmActor no encontrado"));
        if (!actorId.equals(dto.getActorId()) || !filmId.equals(dto.getFilmId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La clave de FilmActor no se puede modificar");
        }
        aplicar(dto, entity);
        return toDto(repository.save(entity));
    }

    @DeleteMapping("/{actorId}/{filmId}")
    public ResponseEntity<Void> eliminar(@PathVariable Short actorId, @PathVariable Short filmId) {
        FilmActor entity = repository.findById(new FilmActorPK(actorId, filmId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FilmActor no encontrado"));
        repository.delete(entity);
        return ResponseEntity.noContent().build();
    }

    private FilmActorDTO toDto(FilmActor entity) {
        FilmActorDTO dto = new FilmActorDTO();
        dto.setLastUpdate(entity.getLastUpdate());
        dto.setActorId(entity.getActor() == null ? null : entity.getActor().getActorId());
        dto.setFilmId(entity.getFilm() == null ? null : entity.getFilm().getFilmId());
        dto.setActorId(entity.getId() == null ? null : entity.getId().getActorId());
        dto.setFilmId(entity.getId() == null ? null : entity.getId().getFilmId());
        return dto;
    }

    private void aplicar(FilmActorDTO dto, FilmActor entity) {
        entity.setLastUpdate(dto.getLastUpdate());
        if (dto.getActorId() == null) {
            entity.setActor(null);
        } else {
            org.sakila.entities.actores.Actor related = entityManager.find(org.sakila.entities.actores.Actor.class, dto.getActorId());
            if (related == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "actor relacionado no encontrado");
            }
            entity.setActor(related);
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
