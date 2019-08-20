package com.texoit.movies.domain.entities.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class ProducerDTO {
    private String producer;
    private int previousWin = 0;
    private int followingWin = 0;

    public ProducerDTO(final String producer, final int previousWin, final int followingWin) {
        this.producer = producer;
        this.previousWin = previousWin;
        this.followingWin = followingWin;
    }

    public int getInterval() {
        return followingWin - previousWin;
    }

    @Data
    @NoArgsConstructor
    public static class WrapperDTO {
        private Set<ProducerDTO> min = new HashSet<>();
        private Set<ProducerDTO> max = new HashSet<>();

        public WrapperDTO(final ProducerDTO min, final ProducerDTO max) {
            this.min.add(min);
            this.max.add(max);
        }
    }
}
