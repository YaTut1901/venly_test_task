package com.example.venly_test_task.model;

import com.example.venly_test_task.model.dto.RelationDto;

import javax.persistence.*;

@Entity
public class RelationEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.ORDINAL)
    private Type type;
    private String firstWord;
    private String secondWord;

    public RelationEntity(Type type, String firstWord, String secondWord) {
        this.type = type;
        this.firstWord = firstWord;
        this.secondWord = secondWord;
    }

    public RelationEntity() {
    }

    public RelationDto toDto() {
        return new RelationDto(type, firstWord, secondWord);
    }

    public Type getType() {
        return type;
    }

    public String getFirstWord() {
        return firstWord;
    }

    public String getSecondWord() {
        return secondWord;
    }
}
