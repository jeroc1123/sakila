package org.sakila.controllers.actores;

import java.util.List;
import org.sakila.dto.actores.ActorDTO;
import org.sakila.entities.actores.Actor;
import org.sakila.repositories.actores.ActorRepository;
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
@RequestMapping("/api/actors")
@Transactional
public class ActorController {
    private final ActorRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public ActorController(ActorRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<ActorDTO> listar() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{actorId}")
    public ActorDTO obtener(@PathVariable Short actorId) {
        Actor entity = repository.findById(actorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Actor no encontrado"));
        return toDto(entity);
    }

    @PostMapping
    public ResponseEntity<ActorDTO> crear(@RequestBody ActorDTO dto) {
        Actor entity = new Actor();
        aplicar(dto, entity);
        Actor saved = repository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(saved));
    }

    @PutMapping("/{actorId}")
    public ActorDTO actualizar(@PathVariable Short actorId, @RequestBody ActorDTO dto) {
        Actor entity = repository.findById(actorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Actor no encontrado"));
        aplicar(dto, entity);
        return toDto(repository.save(entity));
    }

    @DeleteMapping("/{actorId}")
    public ResponseEntity<Void> eliminar(@PathVariable Short actorId) {
        Actor entity = repository.findById(actorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Actor no encontrado"));
        repository.delete(entity);
        return ResponseEntity.noContent().build();
    }

    private ActorDTO toDto(Actor entity) {
        ActorDTO dto = new ActorDTO();
        dto.setActorId(entity.getActorId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setLastUpdate(entity.getLastUpdate());
        return dto;
    }

    private void aplicar(ActorDTO dto, Actor entity) {
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setLastUpdate(dto.getLastUpdate());
    }
}
