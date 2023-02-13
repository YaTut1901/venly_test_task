package com.example.venly_test_task.repository;

import com.example.venly_test_task.model.RelationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RelationRepository extends JpaRepository<RelationEntity, Long> {

    @Query("SELECT r FROM RelationEntity r WHERE (:#{#entity.type} is null or r.type = :#{#entity.type}) "
            + "and (:#{#entity.firstWord} is null or r.firstWord = :#{#entity.firstWord}) "
            + "and (:#{#entity.secondWord} is null or r.secondWord = :#{#entity.secondWord})")
    List<RelationEntity> findAllWith(RelationEntity entity);

    @Transactional(propagation = Propagation.REQUIRED)
    @Query("SELECT r FROM RelationEntity r WHERE (:firstWord = r.firstWord and :secondWord = r.secondWord) "
            + "or (:firstWord = r.secondWord and :secondWord = r.firstWord)")
    List<RelationEntity> findIfPairExists(String firstWord, String secondWord);

    @Transactional(propagation = Propagation.MANDATORY)
    @Query("SELECT r FROM RelationEntity r WHERE :word = r.firstWord or :word = r.secondWord")
    List<RelationEntity> findPairsByWord(String word);
}
