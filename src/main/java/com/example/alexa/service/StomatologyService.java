package com.example.alexa.service;

import com.example.alexa.entity.Director;
import com.example.alexa.entity.Stomatology;
import com.example.alexa.entity.StomatologyTranslate;
import com.example.alexa.entity.dtos.StomatologyDTO;
import com.example.alexa.entity.dtos.StomatologyResponse;
import com.example.alexa.entity.dtos.StomatologyTranslatedDTO;
import com.example.alexa.repository.DirectorRepository;
import com.example.alexa.repository.StomatologyRepository;
import com.example.alexa.repository.StomatologyTranslateRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class StomatologyService {
    private final StomatologyRepository stomatologyRepository;
    private final DirectorRepository directorRepository;
    private final StomatologyTranslateRepository stomatologyTranslateRepository;

    public StomatologyService(StomatologyRepository stomatologyRepository, DirectorRepository directorRepository, StomatologyTranslateRepository stomatologyTranslateRepository) {
        this.stomatologyRepository = stomatologyRepository;
        this.directorRepository = directorRepository;
        this.stomatologyTranslateRepository = stomatologyTranslateRepository;
    }

    public List<StomatologyResponse> getAllStomatologies(int page, int size, String sortBy, String order, String language) {
        Sort sort = order.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Stomatology> stomatologies = stomatologyRepository.findAll(pageable);
        return stomatologies.stream()
                .map(driverLicense -> convertToDTO(driverLicense, language))
                .collect(Collectors.toList());
    }

    private StomatologyResponse convertToDTO(Stomatology stomatology, String language) {
        StomatologyResponse stomatologyResponse = new StomatologyResponse();

        if (stomatology.getStomatologyTranslates() != null && !stomatology.getStomatologyTranslates().isEmpty()) {
            StomatologyTranslate stomatologyTranslate = stomatology.getStomatologyTranslates().stream()
                    .filter(translation -> language.equals(translation.getLanguageCode()))
                    .findFirst()
                    .orElse(null);

            if (stomatologyTranslate != null) {
                stomatologyResponse.setType(stomatology.getType());
                stomatologyResponse.setTranslatedName(stomatologyTranslate.getTranslatedName());
                stomatologyResponse.setTranslatedAddress(stomatologyTranslate.getTranslatedAddress());
                stomatologyResponse.setWallet(stomatology.getWallet());
            }
        }

        return stomatologyResponse;
    }

    public StomatologyResponse getStomatologyById(Long id,String language) {
        Stomatology stomatology = stomatologyRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Stomatology not found with id: " + id));
        return convertToDTO(stomatology,language);
    }

    public StomatologyResponse createStomatology(StomatologyTranslatedDTO stomatologyTranslatedDTO, Long directorId) {
        StomatologyDTO stomatologyDTO = stomatologyTranslatedDTO.getStomatologyDTO();
        List<StomatologyTranslate> stomatologyTranslateList = stomatologyTranslatedDTO.getStomatologyTranslateList();
        Director director = directorRepository.findById(directorId).orElseThrow();

        Stomatology stomatology = new Stomatology();
        stomatology.setType(stomatologyDTO.getType());
        stomatology.setWallet(stomatologyDTO.getWallet());
        stomatology.setDirector(director);

        Stomatology savedBook = stomatologyRepository.save(stomatology);

        for (StomatologyTranslate translate : stomatologyTranslateList) {
            translate.setStomatology(stomatology);
            stomatologyTranslateRepository.save(translate);
        }
        StomatologyResponse stomatologyResponse = new StomatologyResponse();
        stomatologyResponse.setType(savedBook.getType());
        stomatologyResponse.setWallet(savedBook.getWallet());

        return stomatologyResponse;
    }

}

