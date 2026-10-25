package org.sakila.controllers.ventas;

import java.util.List;
import org.sakila.dto.ventas.InventoryDTO;
import org.sakila.entities.ventas.Inventory;
import org.sakila.repositories.ventas.InventoryRepository;
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
@RequestMapping("/api/inventories")
@Transactional
public class InventoryController {
    private final InventoryRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public InventoryController(InventoryRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<InventoryDTO> listar() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{inventoryId}")
    public InventoryDTO obtener(@PathVariable Integer inventoryId) {
        Inventory entity = repository.findById(inventoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory no encontrado"));
        return toDto(entity);
    }

    @PostMapping
    public ResponseEntity<InventoryDTO> crear(@RequestBody InventoryDTO dto) {
        Inventory entity = new Inventory();
        aplicar(dto, entity);
        Inventory saved = repository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(saved));
    }

    @PutMapping("/{inventoryId}")
    public InventoryDTO actualizar(@PathVariable Integer inventoryId, @RequestBody InventoryDTO dto) {
        Inventory entity = repository.findById(inventoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory no encontrado"));
        aplicar(dto, entity);
        return toDto(repository.save(entity));
    }

    @DeleteMapping("/{inventoryId}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer inventoryId) {
        Inventory entity = repository.findById(inventoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory no encontrado"));
        repository.delete(entity);
        return ResponseEntity.noContent().build();
    }

    private InventoryDTO toDto(Inventory entity) {
        InventoryDTO dto = new InventoryDTO();
        dto.setInventoryId(entity.getInventoryId());
        dto.setLastUpdate(entity.getLastUpdate());
        dto.setFilmId(entity.getFilm() == null ? null : entity.getFilm().getFilmId());
        dto.setStoreId(entity.getStore() == null ? null : entity.getStore().getStoreId());
        return dto;
    }

    private void aplicar(InventoryDTO dto, Inventory entity) {
        entity.setLastUpdate(dto.getLastUpdate());
        if (dto.getFilmId() == null) {
            entity.setFilm(null);
        } else {
            org.sakila.entities.peliculas.Film related = entityManager.find(org.sakila.entities.peliculas.Film.class, dto.getFilmId());
            if (related == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "film relacionado no encontrado");
            }
            entity.setFilm(related);
        }
        if (dto.getStoreId() == null) {
            entity.setStore(null);
        } else {
            org.sakila.entities.ventas.Store related = entityManager.find(org.sakila.entities.ventas.Store.class, dto.getStoreId());
            if (related == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "store relacionado no encontrado");
            }
            entity.setStore(related);
        }
    }
}
