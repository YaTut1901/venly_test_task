package com.example.venly_test_task.service;

import com.example.venly_test_task.model.RelationEntity;
import com.example.venly_test_task.model.dto.RelationDto;
import com.example.venly_test_task.repository.RelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<RelationDto> findAll(RelationDto filter) {
        return repository.findAllWith(filter.toEntity()).stream()
                .map(RelationEntity::toDto)
                .collect(Collectors.toList());
    }
}
