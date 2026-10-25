package org.sakila.controllers.direcciones;

import java.util.List;
import org.sakila.dto.direcciones.CountryDTO;
import org.sakila.entities.direcciones.Country;
import org.sakila.repositories.direcciones.CountryRepository;
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
@RequestMapping("/api/countries")
@Transactional
public class CountryController {
    private final CountryRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public CountryController(CountryRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<CountryDTO> listar() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{countryId}")
    public CountryDTO obtener(@PathVariable Short countryId) {
        Country entity = repository.findById(countryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Country no encontrado"));
        return toDto(entity);
    }

    @PostMapping
    public ResponseEntity<CountryDTO> crear(@RequestBody CountryDTO dto) {
        Country entity = new Country();
        aplicar(dto, entity);
        entity.setCountryId(dto.getCountryId());
        Country saved = repository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(saved));
    }

    @PutMapping("/{countryId}")
    public CountryDTO actualizar(@PathVariable Short countryId, @RequestBody CountryDTO dto) {
        Country entity = repository.findById(countryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Country no encontrado"));
        aplicar(dto, entity);
        return toDto(repository.save(entity));
    }

    @DeleteMapping("/{countryId}")
    public ResponseEntity<Void> eliminar(@PathVariable Short countryId) {
        Country entity = repository.findById(countryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Country no encontrado"));
        repository.delete(entity);
        return ResponseEntity.noContent().build();
    }

    private CountryDTO toDto(Country entity) {
        CountryDTO dto = new CountryDTO();
        dto.setCountryId(entity.getCountryId());
        dto.setCountry(entity.getCountry());
        dto.setLastUpdate(entity.getLastUpdate());
        return dto;
    }

    private void aplicar(CountryDTO dto, Country entity) {
        entity.setCountry(dto.getCountry());
        entity.setLastUpdate(dto.getLastUpdate());
    }
}
