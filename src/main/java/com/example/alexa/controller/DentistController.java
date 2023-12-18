package com.example.alexa.controller;

import com.example.alexa.entity.Dentist;
import com.example.alexa.service.DentistService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/dentists")
public class DentistController {
    private final DentistService dentistService;

    public DentistController(DentistService dentistService) {
        this.dentistService = dentistService;
    }

    @GetMapping
    public ResponseEntity<Page<Dentist>> getAllDentists(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order) {
        Page<Dentist> dentists = dentistService.getAllDentists(page, size, sortBy, order);
        return new ResponseEntity<>(dentists, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Dentist> getDentistById(@PathVariable("id") Long id) {
        Dentist dentist = dentistService.getDentistById(id);
        return new ResponseEntity<>(dentist, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Dentist> createDentist(@RequestBody Dentist dentist) {
        Dentist createdDentist = dentistService.createDentist(dentist);
        return new ResponseEntity<>(createdDentist, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dentist> updateDentist(
            @PathVariable("id") Long id,
            @RequestBody Dentist updatedDentist
    ) {
        Dentist updated = dentistService.updateDentist(id, updatedDentist);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDentist(@PathVariable("id") Long id) {
        dentistService.deleteDentist(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

