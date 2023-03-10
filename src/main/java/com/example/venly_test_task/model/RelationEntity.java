package com.example.venly_test_task.model;

import com.example.venly_test_task.model.dto.RelationDto;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
public class RelationEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.ORDINAL)
    private Type type;
    @Pattern(regexp = "[a-zA-Z ]+", message = "Only English lowercase and UPPERCASE letters and spaces allowed!")
    private String firstWord;
    @Pattern(regexp = "[a-zA-Z ]+", message = "Only English lowercase and UPPERCASE letters and spaces allowed!")
    private String secondWord;

    public RelationEntity(Type type, String firstWord, String secondWord) {
        this.type = type;
        this.firstWord = firstWord == null ? null : firstWord.trim().toLowerCase();
        this.secondWord = secondWord == null ? null : secondWord.trim().toLowerCase();
    }

    public RelationEntity() {
    }

    public RelationDto toDto() {
        return new RelationDto(type, firstWord, secondWord);
    }

    public RelationDto toInversedDto() {
        RelationDto relationDto = new RelationDto(type, secondWord, firstWord);
        relationDto.setInversed();
        return relationDto;
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
