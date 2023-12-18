package com.example.alexa.entity.dtos;

import com.example.alexa.enums.StomatologyType;
import lombok.Data;

@Data
public class StomatologyDTO {
    private StomatologyType type;

    private double wallet;
}
