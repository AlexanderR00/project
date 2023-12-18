package com.example.alexa.entity.dtos;

import com.example.alexa.entity.StomatologyTranslate;
import lombok.Data;

import java.util.List;

@Data
public class StomatologyTranslatedDTO {
    private StomatologyDTO stomatologyDTO;
    private List<StomatologyTranslate> stomatologyTranslateList;
}
