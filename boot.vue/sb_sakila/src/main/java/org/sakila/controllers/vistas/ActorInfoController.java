package org.sakila.controllers.vistas;

import java.util.List;

import org.sakila.dto.vistas.ActorInfoDTO;
import org.sakila.entities.vistas.ActorInfo;
import org.sakila.repositories.vistas.ActorInfoRepository;
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
@RequestMapping("/api/views/actor-info")
@Transactional
public class ActorInfoController {
    private final ActorInfoRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public ActorInfoController(ActorInfoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<ActorInfoDTO> listar() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{actorId}")
    public ActorInfoDTO obtener(@PathVariable Short actorId) {
        ActorInfo entity = repository.findById(actorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ActorInfo no encontrado"));
        return toDto(entity);
    }

    private ActorInfoDTO toDto(ActorInfo entity) {
        ActorInfoDTO dto = new ActorInfoDTO();
        dto.setActorId(entity.getActorId());
        dto.setFilmInfo(entity.getFilmInfo());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        return dto;
    }
}
