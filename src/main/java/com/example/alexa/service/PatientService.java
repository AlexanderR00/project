package com.example.alexa.service;

import com.example.alexa.entity.Patient;
import com.example.alexa.entity.Stomatology;
import com.example.alexa.entity.dtos.PatientResponse;
import com.example.alexa.enums.StomatologyType;
import com.example.alexa.repository.DentistRepository;
import com.example.alexa.repository.PatientRepository;
import com.example.alexa.repository.StomatologyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final StomatologyRepository stomatologyRepository;
    private final DentistRepository dentistRepository;


    public List<PatientResponse> getAllPatients(int page, int size, String sortBy, String order) {
        Sort sort = order.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Patient> patients = patientRepository.findAll(pageable);
        return patients.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private PatientResponse convertToDTO(Patient product) {
        PatientResponse patientResponse = new PatientResponse();
        patientResponse.setAge(product.getAge());
        patientResponse.setWallet(product.getWallet());
        patientResponse.setLastName(product.getLastName());
        patientResponse.setName(product.getName());
        return patientResponse;
    }

    public PatientResponse getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Patient not found with id: " + id));
        return convertToDTO(patient);
    }

    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public void treatPatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new NoSuchElementException("Patient not found with id: " + patientId));

        Stomatology stomatology = patient.getStomatology();
        double treatmentCost = calculateTreatmentCost(patient, stomatology);

        if (treatmentCost > 0) {
            if (patient.getWallet() >= treatmentCost) {
                performTreatment(patient, stomatology, treatmentCost);
            }
        }
    }
    private double calculateTreatmentCost(Patient patient, Stomatology stomatology) {
        if (patient.getAge() < 18 && stomatology.getType() == StomatologyType.PUBLIC) {
            return 0; // Лечение бесплатно
        } else {
            return 10000; // Лечение платное
        }
    }
    private void performTreatment(Patient patient, Stomatology stomatology, double treatmentCost) {
        patient.setWallet(patient.getWallet() - treatmentCost);
        stomatology.setWallet(stomatology.getWallet() + treatmentCost);

        patientRepository.save(patient);
        stomatologyRepository.save(stomatology);
    }
}

