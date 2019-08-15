package com.texoit.movies.domain.service;

import com.texoit.movies.domain.entities.Producer;
import com.texoit.movies.domain.repository.IProducerRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class ProducerService {

    private final IProducerRepository producerRepository;

    public Set<Dto> findAll() {
        final List<Producer> producers = producerRepository.findAll();
        final Set<Dto> dtos = new HashSet<>();

        producers.forEach(producer -> {
            final Dto dto = new Dto(producer.getName());
            producer.getMovies().forEach(movieProducer -> {
                movieProducer.getMovie().getIndications().forEach(indication -> { // Colocar um filter aqui
                    if (indication.getWinner() != null && indication.getWinner()) {
                        if (dto.getPreviousWin() == 0) {
                            dto.setPreviousWin(indication.getPremium().getYear());
                        } else {
                            if (indication.getPremium().getYear() <= dto.getPreviousWin()) {
                                if (dto.getPreviousWin() >= dto.getFollowingWin())
                                    dto.setFollowingWin(dto.getPreviousWin());
                                dto.setPreviousWin(indication.getPremium().getYear());
                            }
                        }
                    }
                });
                dtos.add(dto);
            });
        });

        return dtos;
    }

    @Data
    public static class Dto {
        private String producer;
        private int previousWin = 0;
        private int followingWin = 0;

        public Dto() {
        }

        Dto(String producer) {
            this.producer = producer;
        }

        public int getInterval() {
            return followingWin - previousWin;
        }
    }
}
