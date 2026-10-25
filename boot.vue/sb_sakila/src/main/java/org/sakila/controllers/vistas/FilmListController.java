package org.sakila.controllers.vistas;

import java.util.List;

import org.sakila.dto.vistas.FilmListDTO;
import org.sakila.entities.vistas.FilmList;
import org.sakila.repositories.vistas.FilmListRepository;
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
@RequestMapping("/api/views/film-list")
@Transactional
public class FilmListController {
    private final FilmListRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public FilmListController(FilmListRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<FilmListDTO> listar() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{fid}")
    public FilmListDTO obtener(@PathVariable Short fid) {
        FilmList entity = repository.findById(fid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FilmList no encontrado"));
        return toDto(entity);
    }

    private FilmListDTO toDto(FilmList entity) {
        FilmListDTO dto = new FilmListDTO();
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
