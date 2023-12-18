package com.example.alexa.entity;

import com.example.alexa.enums.StomatologyType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stomatology {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "director_id")
    private Director director;

    @OneToMany(mappedBy = "stomatology")
    private List<Dentist> dentists;

    @OneToMany(mappedBy = "stomatology")
    private List<Patient> patients;

    @Enumerated(EnumType.STRING)
    private StomatologyType type;

    @OneToMany(mappedBy = "stomatology", cascade = CascadeType.ALL)
    private List<StomatologyTranslate> stomatologyTranslates;

    private double wallet;
}

