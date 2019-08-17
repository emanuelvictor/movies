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

    public ProducerDto(String producer, int previousWin, int followingWin) {
        this.producer = producer;
        this.previousWin = previousWin;
        this.followingWin = followingWin;
    }

    public int getInterval() {
        return followingWin - previousWin;
    }

    @Data
    @NoArgsConstructor
    public static class WrapperDto {
        private Set<ProducerDto> min = new HashSet<>();
        private Set<ProducerDto> max = new HashSet<>();
    }
}
