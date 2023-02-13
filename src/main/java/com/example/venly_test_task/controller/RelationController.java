package com.example.venly_test_task.controller;

import com.example.venly_test_task.model.dto.RelationDto;
import com.example.venly_test_task.service.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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

    @GetMapping("/path")
    public ResponseEntity<List<RelationDto>> findPath(@RequestParam String from, @RequestParam String to) {
        Future<List<RelationDto>> path = service.findPath(from, to);
        while (true) {
            if (path.isDone()) {
                try {
                    return ResponseEntity.ok(path.get());
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
