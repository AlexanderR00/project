package com.example.alexa.entity;

import com.example.alexa.enums.DentistClassification;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dentist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastName;
    private String level;

    @Enumerated(EnumType.STRING)
    private DentistClassification classification; // Использование перечисления DentistClassification

    @ManyToOne
    @JoinColumn(name = "stomatology_id")
    private Stomatology stomatology;

    // Геттеры, сеттеры и другие методы
}


