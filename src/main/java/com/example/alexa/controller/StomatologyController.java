package com.example.alexa.controller;

import com.example.alexa.entity.Stomatology;
import com.example.alexa.entity.dtos.StomatologyResponse;
import com.example.alexa.entity.dtos.StomatologyTranslatedDTO;
import com.example.alexa.service.StomatologyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/stomatologies")
public class StomatologyController {
    private final StomatologyService stomatologyService;

    public StomatologyController(StomatologyService stomatologyService) {
        this.stomatologyService = stomatologyService;
    }

    @GetMapping
    public ResponseEntity<List<StomatologyResponse>> getAllStomatologies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order,
            @RequestHeader("language") String language) {
        List<StomatologyResponse> stomatologies = stomatologyService.getAllStomatologies(page, size, sortBy, order, language);
        return new ResponseEntity<>(stomatologies, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StomatologyResponse> getStomatologyById(@PathVariable("id") Long id, @RequestHeader("language") String language) {
        StomatologyResponse stomatology = stomatologyService.getStomatologyById(id,language);
        return new ResponseEntity<>(stomatology, HttpStatus.OK);
    }

    @PostMapping("/create/{directorId}")
    public ResponseEntity<StomatologyResponse> createStomatology(@RequestBody StomatologyTranslatedDTO stomatology,@PathVariable("directorId") Long id) {
        StomatologyResponse createdStomatology = stomatologyService.createStomatology(stomatology,id);
        return new ResponseEntity<>(createdStomatology, HttpStatus.CREATED);
    }

}

