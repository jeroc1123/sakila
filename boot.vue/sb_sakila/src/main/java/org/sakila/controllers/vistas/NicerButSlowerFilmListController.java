package org.sakila.controllers.vistas;

import java.util.List;

import org.sakila.dto.vistas.NicerButSlowerFilmListDTO;
import org.sakila.entities.vistas.NicerButSlowerFilmList;
import org.sakila.repositories.vistas.NicerButSlowerFilmListRepository;
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
@RequestMapping("/api/views/nicer-but-slower-film-list")
@Transactional
public class NicerButSlowerFilmListController {
    private final NicerButSlowerFilmListRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public NicerButSlowerFilmListController(NicerButSlowerFilmListRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<NicerButSlowerFilmListDTO> listar() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{fid}")
    public NicerButSlowerFilmListDTO obtener(@PathVariable Short fid) {
        NicerButSlowerFilmList entity = repository.findById(fid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "NicerButSlowerFilmList no encontrado"));
        return toDto(entity);
    }

    private NicerButSlowerFilmListDTO toDto(NicerButSlowerFilmList entity) {
        NicerButSlowerFilmListDTO dto = new NicerButSlowerFilmListDTO();
        dto.setFid(entity.getFid());
        dto.setActors(entity.getActors());
        dto.setCategory(entity.getCategory());
        dto.setDescription(entity.getDescription());
        dto.setLength(entity.getLength());
        dto.setPrice(entity.getPrice());
        dto.setRating(entity.getRating());
        dto.setTitle(entity.getTitle());
        return dto;
    }
}
