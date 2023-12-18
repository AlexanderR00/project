package com.example.alexa.entity.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class DirectorResponse {
    private String name;
    private String lastName;
}
