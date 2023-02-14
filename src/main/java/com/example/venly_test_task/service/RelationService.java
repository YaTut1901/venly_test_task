package com.example.venly_test_task.service;

import com.example.venly_test_task.exception.ExistingPairException;
import com.example.venly_test_task.exception.PathNotFoundException;
import com.example.venly_test_task.model.RelationEntity;
import com.example.venly_test_task.model.dto.RelationDto;
import com.example.venly_test_task.repository.RelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RelationService {
    private RelationRepository repository;

    @Autowired
    public RelationService(RelationRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public RelationDto save(RelationDto dto) {
        if (!repository.findIfPairExists(dto.getFirstWord(), dto.getSecondWord()).isEmpty()) {
            throw new ExistingPairException("Such pair already exists!");
        }
        return repository.save(dto.toEntity()).toDto();
    }

    public List<RelationDto> findAll(RelationDto filter, boolean inverse) {
        return repository.findAllWith(filter.toEntity()).stream()
                .flatMap(e -> inverse ? Stream.of(e.toDto(), e.toInversedDto())
                        : Stream.of(e.toDto()))
                .collect(Collectors.toList());
    }


    @Async
    @Transactional(propagation = Propagation.REQUIRED)
    public Future<List<RelationDto>> findPath(String from, String to) {
        List<RelationEntity> pairs = repository.findPairsByWord(from);

        if (pairs.isEmpty()) {
            throw new PathNotFoundException("There is no relations for start point of path ('from' parameter)");
        }

        Optional<RelationEntity> target = pairs.stream()
                .filter(p -> p.getFirstWord().equals(to) || p.getSecondWord().equals(to))
                .findFirst();

        return new AsyncResult<>(target.map(entity -> List.of(entity.toDto()))
                .orElseGet(() -> pairs.stream()
                        .flatMap(p -> findPathRecursive(addNextElement(new ArrayList<>(), p), p.getFirstWord().equals(from) ? p.getSecondWord()
                                : p.getFirstWord(), to, copyListAndAddNextElement(new ArrayList<>(), p), new ArrayList<>()).stream())
                        .min(Comparator.comparing(List::size))
                        .map(l -> l.stream()
                                .map(RelationEntity::toDto)
                                .collect(Collectors.toList()))
                        .orElseThrow(() -> new PathNotFoundException(String.format("No path found from %s to %s", from, to)))));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    List<List<RelationEntity>> findPathRecursive(List<RelationEntity> visited, String from, String to, List<RelationEntity> current, List<List<RelationEntity>> paths) {
        List<RelationEntity> pairs = repository.findPairsByWord(from);

        if (pairs.isEmpty()) {
            return paths;
        }

        Optional<RelationEntity> target = pairs.stream()
                .filter(p -> p.getFirstWord().equals(to) || p.getSecondWord().equals(to))
                .findFirst();

        if (target.isPresent()) {
            current.add(target.get());
            paths.add(current);
            return paths;
        }

        return pairs.stream()
                .filter(p -> !visited.contains(p))
                .flatMap(p -> findPathRecursive(addNextElement(visited, p), p.getFirstWord().equals(from) ? p.getSecondWord()
                        : p.getFirstWord(), to, copyListAndAddNextElement(current, p), paths).stream())
                .collect(Collectors.toList());
    }

    private List<RelationEntity> copyListAndAddNextElement(List<RelationEntity> list, RelationEntity next) {
        ArrayList<RelationEntity> res = new ArrayList<>(list);
        res.add(next);

        return res;
    }

    private List<RelationEntity> addNextElement(List<RelationEntity> list, RelationEntity next) {
        list.add(next);

        return list;
    }
}
