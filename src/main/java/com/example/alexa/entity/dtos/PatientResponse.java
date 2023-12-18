package com.example.alexa.entity.dtos;

import lombok.Data;

@Data
public class PatientResponse {
    private String name;
    private String lastName;
    private int age;
    private double wallet;
}
