package com.example.venly_test_task.controller;

import com.example.venly_test_task.model.dto.RelationDto;
import com.example.venly_test_task.service.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return ResponseEntity.ok(service.save(dto));
    }

    @GetMapping
    public ResponseEntity<List<RelationDto>> listRelation(RelationDto filter, @RequestParam(required = false) boolean inverse) {
        return ResponseEntity.ok(service.findAll(filter, inverse));
    }
}
