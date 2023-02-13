package com.example.venly_test_task.controller;

import com.example.venly_test_task.model.dto.RelationDto;
import com.example.venly_test_task.service.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/relation")
public class RelationController {
    private RelationService service;

    @Autowired
    public RelationController(RelationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<RelationDto> addRelation(@RequestBody RelationDto dto) {
        return ResponseEntity.ok(service.save(dto.toEntity()).toDto());
    }
}
