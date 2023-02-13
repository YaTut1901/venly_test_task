package com.example.venly_test_task.model;

import com.example.venly_test_task.model.dto.RelationDto;

import javax.persistence.*;

@Entity
public class RelationEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.ORDINAL)
    private Type relation;
    private String firstWord;
    private String secondWord;

    public RelationEntity(Type relation, String firstWord, String secondWord) {
        this.relation = relation;
        this.firstWord = firstWord;
        this.secondWord = secondWord;
    }

    public RelationEntity() {
    }

    public RelationDto toDto() {
        return new RelationDto(relation, firstWord, secondWord);
    }
}
