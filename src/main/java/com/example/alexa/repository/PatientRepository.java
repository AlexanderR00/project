package com.example.alexa.repository;

import com.example.alexa.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    // Здесь можно добавить кастомные методы для работы с сущностью Patient, если нужно
}

