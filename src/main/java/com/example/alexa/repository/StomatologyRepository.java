package com.example.alexa.repository;

import com.example.alexa.entity.Stomatology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StomatologyRepository extends JpaRepository<Stomatology, Long> {
    // Здесь можно добавить кастомные методы для работы с сущностью Stomatology, если нужно
}

