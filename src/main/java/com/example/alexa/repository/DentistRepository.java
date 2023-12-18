package com.example.alexa.repository;

import com.example.alexa.entity.Dentist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DentistRepository extends JpaRepository<Dentist, Long> {
    // Здесь можно добавить кастомные методы для работы с сущностью Dentist, если нужно
}

