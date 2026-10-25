package org.sakila.controllers.peliculas;

import java.util.List;
import org.sakila.dto.peliculas.LanguageDTO;
import org.sakila.entities.peliculas.Language;
import org.sakila.repositories.peliculas.LanguageRepository;
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
@RequestMapping("/api/languages")
@Transactional
public class LanguageController {
    private final LanguageRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public LanguageController(LanguageRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<LanguageDTO> listar() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{languageId}")
    public LanguageDTO obtener(@PathVariable Byte languageId) {
        Language entity = repository.findById(languageId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Language no encontrado"));
        return toDto(entity);
    }

    @PostMapping
    public ResponseEntity<LanguageDTO> crear(@RequestBody LanguageDTO dto) {
        Language entity = new Language();
        aplicar(dto, entity);
        Language saved = repository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(saved));
    }

    @PutMapping("/{languageId}")
    public LanguageDTO actualizar(@PathVariable Byte languageId, @RequestBody LanguageDTO dto) {
        Language entity = repository.findById(languageId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Language no encontrado"));
        aplicar(dto, entity);
        return toDto(repository.save(entity));
    }

    @DeleteMapping("/{languageId}")
    public ResponseEntity<Void> eliminar(@PathVariable Byte languageId) {
        Language entity = repository.findById(languageId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Language no encontrado"));
        repository.delete(entity);
        return ResponseEntity.noContent().build();
    }

    private LanguageDTO toDto(Language entity) {
        LanguageDTO dto = new LanguageDTO();
        dto.setLanguageId(entity.getLanguageId());
        dto.setLastUpdate(entity.getLastUpdate());
        dto.setName(entity.getName());
        return dto;
    }

    private void aplicar(LanguageDTO dto, Language entity) {
        entity.setLastUpdate(dto.getLastUpdate());
        entity.setName(dto.getName());
    }
}
