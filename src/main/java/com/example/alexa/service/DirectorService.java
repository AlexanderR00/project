package com.example.alexa.service;

import com.example.alexa.entity.Dentist;
import com.example.alexa.entity.Director;
import com.example.alexa.entity.dtos.DirectorDTO;
import com.example.alexa.entity.dtos.DirectorResponse;
import com.example.alexa.repository.DirectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DirectorService {
    private final DirectorRepository directorRepository;

    public List<DirectorResponse> getAllDirectors(int page, int size, String sortBy, String order) {
        Sort.Direction sortDirection = Sort.Direction.fromString(order);
        Sort sort = Sort.by(sortDirection, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Director> directors = directorRepository.findAll(pageable);
        return directors.getContent().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public DirectorResponse getDirectorById(Long id) {
        Director director = directorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Director not found with id: " + id));
        return DirectorResponse.builder()
                .name(director.getName())
                .lastName(director.getLastName())
                .build();
    }

    public DirectorResponse createDirector(DirectorDTO directorDTO) {
        Director director = convertToEntity(directorDTO);
        directorRepository.save(director);
        return DirectorResponse.builder()
                .name(directorDTO.getName())
                .lastName(directorDTO.getLastName())
                .build();
    }

    private DirectorResponse convertToResponse(Director director) {
        return DirectorResponse.builder()
                        .name(director.getName())
                                .lastName(director.getLastName())
                                        .build();
    }

    private Director convertToEntity(DirectorDTO directorDTO) {
        Director director = new Director();
        director.setName(directorDTO.getName());
        director.setLastName(directorDTO.getLastName());
        return director;
    }
}
