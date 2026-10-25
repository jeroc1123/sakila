package org.sakila.controllers.direcciones;

import java.util.List;
import org.sakila.dto.direcciones.CityDTO;
import org.sakila.entities.direcciones.City;
import org.sakila.repositories.direcciones.CityRepository;
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
@RequestMapping("/api/cities")
@Transactional
public class CityController {
    private final CityRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public CityController(CityRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<CityDTO> listar() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{cityId}")
    public CityDTO obtener(@PathVariable Short cityId) {
        City entity = repository.findById(cityId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "City no encontrado"));
        return toDto(entity);
    }

    @PostMapping
    public ResponseEntity<CityDTO> crear(@RequestBody CityDTO dto) {
        City entity = new City();
        aplicar(dto, entity);
        entity.setCityId(dto.getCityId());
        City saved = repository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(saved));
    }

    @PutMapping("/{cityId}")
    public CityDTO actualizar(@PathVariable Short cityId, @RequestBody CityDTO dto) {
        City entity = repository.findById(cityId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "City no encontrado"));
        aplicar(dto, entity);
        return toDto(repository.save(entity));
    }

    @DeleteMapping("/{cityId}")
    public ResponseEntity<Void> eliminar(@PathVariable Short cityId) {
        City entity = repository.findById(cityId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "City no encontrado"));
        repository.delete(entity);
        return ResponseEntity.noContent().build();
    }

    private CityDTO toDto(City entity) {
        CityDTO dto = new CityDTO();
        dto.setCityId(entity.getCityId());
        dto.setCity(entity.getCity());
        dto.setCountryId(entity.getCountryId());
        dto.setLastUpdate(entity.getLastUpdate());
        return dto;
    }

    private void aplicar(CityDTO dto, City entity) {
        entity.setCity(dto.getCity());
        entity.setCountryId(dto.getCountryId());
        entity.setLastUpdate(dto.getLastUpdate());
    }
}
