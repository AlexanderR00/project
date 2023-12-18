package com.example.alexa.controller;

import com.example.alexa.entity.Director;
import com.example.alexa.entity.dtos.DirectorDTO;
import com.example.alexa.entity.dtos.DirectorResponse;
import com.example.alexa.service.DirectorService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/directors")
public class DirectorController {
    private final DirectorService directorService;

    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @GetMapping
    public ResponseEntity<List<DirectorResponse>> getAllDirector(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order) {
        List<DirectorResponse> directors = directorService.getAllDirectors(page, size, sortBy, order);
        return new ResponseEntity<>(directors, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DirectorResponse> getDirectorById(@PathVariable("id") Long id) {
        DirectorResponse director = directorService.getDirectorById(id);
        return new ResponseEntity<>(director, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DirectorResponse> createDirector(@RequestBody DirectorDTO directorDTO) {
        DirectorResponse createdDirector = directorService.createDirector(directorDTO);
        return new ResponseEntity<>(createdDirector, HttpStatus.CREATED);
    }

}

