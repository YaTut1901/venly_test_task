package com.example.venly_test_task.model.dto;

import com.example.venly_test_task.model.RelationEntity;
import com.example.venly_test_task.model.Type;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class RelationDto {
    @JsonProperty("type")
    private final Type type;
    @JsonProperty("firstWord")
    private final String firstWord;
    @JsonProperty("secondWord")
    private final String secondWord;
    @JsonProperty("inversed")
    private boolean inversed;

    @JsonCreator
    public RelationDto(Type type, String firstWord, String secondWord) {
        this.type = type;
        this.firstWord = firstWord;
        this.secondWord = secondWord;
        this.inversed = false;
    }

    public RelationEntity toEntity() {
        return new RelationEntity(type, firstWord, secondWord);
    }

    public void setInversed() {
        this.inversed = true;
    }
}
