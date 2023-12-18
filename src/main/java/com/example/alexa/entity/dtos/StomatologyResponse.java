package com.example.alexa.entity.dtos;

import com.example.alexa.enums.StomatologyType;
import lombok.Data;

@Data
public class StomatologyResponse {
    private StomatologyType type;

    private String translatedAddress;

    private String translatedName;

    private double wallet;

}
