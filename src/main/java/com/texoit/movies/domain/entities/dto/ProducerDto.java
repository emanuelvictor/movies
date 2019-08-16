package com.texoit.movies.domain.entities.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class ProducerDto {
    private String producer;
    private int previousWin = 0;
    private int followingWin = 0;

    public ProducerDto(String producer) {
        this.producer = producer;
    }

    public int getInterval() {
        return followingWin - previousWin;
    }

    @Data
    @NoArgsConstructor
    public static class Dto {
        private Set<ProducerDto> min = new HashSet<>();
        private Set<ProducerDto> max = new HashSet<>();
    }
}