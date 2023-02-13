package com.example.venly_test_task.service;

import com.example.venly_test_task.model.RelationEntity;
import com.example.venly_test_task.repository.RelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelationService {
    private RelationRepository repository;

    @Autowired
    public RelationService(RelationRepository repository) {
        this.repository = repository;
    }

    public RelationEntity save(RelationEntity entity) {
        return repository.save(entity);
    }
}
