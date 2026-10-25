package org.sakila.controllers.vistas;

import java.util.List;

import org.sakila.dto.vistas.StaffListDTO;
import org.sakila.entities.vistas.StaffList;
import org.sakila.repositories.vistas.StaffListRepository;
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
@RequestMapping("/api/views/staff-list")
@Transactional
public class StaffListController {
    private final StaffListRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public StaffListController(StaffListRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<StaffListDTO> listar() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{id}")
    public StaffListDTO obtener(@PathVariable Byte id) {
        StaffList entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "StaffList no encontrado"));
        return toDto(entity);
    }

    private StaffListDTO toDto(StaffList entity) {
        StaffListDTO dto = new StaffListDTO();
        dto.setAddress(entity.getAddress());
        dto.setCity(entity.getCity());
        dto.setCountry(entity.getCountry());
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPhone(entity.getPhone());
        dto.setSid(entity.getSid());
        dto.setZip_code(entity.getZip_code());
        return dto;
    }
}
