package com.example.venly_test_task.service;

import com.example.venly_test_task.model.dto.RelationDto;
import com.example.venly_test_task.repository.RelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RelationService {
    private RelationRepository repository;

    @Autowired
    public RelationService(RelationRepository repository) {
        this.repository = repository;
    }

    public RelationDto save(RelationDto dto) {
        return repository.save(dto.toEntity()).toDto();
    }

    public List<RelationDto> findAll(RelationDto filter, boolean inverse) {
        return repository.findAllWith(filter.toEntity()).stream()
                .flatMap(e -> inverse ? Stream.of(e.toDto(), e.toInversedDto())
                        : Stream.of(e.toDto()))
                .collect(Collectors.toList());
    }
}
