package com.example.alexa.service;

import com.example.alexa.entity.Dentist;
import com.example.alexa.repository.DentistRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DentistService {
    private final DentistRepository dentistRepository;

    public DentistService(DentistRepository dentistRepository) {
        this.dentistRepository = dentistRepository;
    }

    public Page<Dentist> getAllDentists(int page, int size, String sortBy, String order) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sortBy));
        return dentistRepository.findAll(pageable);
    }


    public Dentist getDentistById(Long id) {
        return dentistRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Dentist not found with id: " + id));
    }

    public Dentist createDentist(Dentist dentist) {
        return dentistRepository.save(dentist);
    }

    public Dentist updateDentist(Long id, Dentist updatedDentist) {
        Dentist existingDentist = getDentistById(id);
        // Обновление полей сущности по вашему усмотрению
        existingDentist.setName(updatedDentist.getName());
        existingDentist.setLastName(updatedDentist.getLastName());
        existingDentist.setLevel(updatedDentist.getLevel());
        existingDentist.setClassification(updatedDentist.getClassification());
        // Другие обновления...

        return dentistRepository.save(existingDentist);
    }

    public void deleteDentist(Long id) {
        Dentist dentist = getDentistById(id);
        dentistRepository.delete(dentist);
    }
}

