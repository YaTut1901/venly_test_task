package com.example.venly_test_task.model.dto;

import com.example.venly_test_task.model.RelationEntity;
import com.example.venly_test_task.model.Type;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class RelationDto {
    @JsonProperty("type")
    private final Type type;
    @JsonProperty("firstWord")
    private final String firstWord;
    @JsonProperty("secondWord")
    private final String secondWord;

    public RelationDto(Type type, String firstWord, String secondWord) {
        this.type = type;
        this.firstWord = firstWord;
        this.secondWord = secondWord;
    }

    public RelationEntity toEntity() {
        return new RelationEntity(type, firstWord, secondWord);
    }
}
