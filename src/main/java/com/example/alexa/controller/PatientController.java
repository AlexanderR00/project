package com.example.alexa.controller;

import com.example.alexa.entity.Patient;
import com.example.alexa.entity.dtos.PatientResponse;
import com.example.alexa.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<List<PatientResponse>> getAllPatient(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order) {
        List<PatientResponse> patient = patientService.getAllPatients(page, size, sortBy, order);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable("id") Long id) {
        PatientResponse patient = patientService.getPatientById(id);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        Patient createdPatient = patientService.createPatient(patient);
        return new ResponseEntity<>(createdPatient, HttpStatus.CREATED);
    }

    @PutMapping("/{patientId}/treat")
    public ResponseEntity<String> treatPatient(@PathVariable("patientId") Long patientId) {
            patientService.treatPatient(patientId);
            return new ResponseEntity<>("Patient treated successfully", HttpStatus.OK);
    }

}

