package com.texoit.movies.domain.service;

import com.texoit.movies.domain.entities.*;
import com.texoit.movies.domain.repository.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class ProducerService {

    private final IMovieRepository movieRepository;
    private final IStudioRepository studioRepository;
    private final IProducerRepository producerRepository;
    private final IPremiumRepository premiumRepository;
    private final IIndicationRepository indicationRepository;

    public Set<Dto> findAll() {
        final List<Producer> producers = producerRepository.findAll();
        final Set<Dto> dtos = new HashSet<>();

        producers.forEach(producer -> {
            final Dto dto = new Dto();
            producer.getMovies().forEach(movieProducer -> {
                if (producer.getName().equals("Kevin Costner"))
                    System.out.println(producer.getName());
                dto.setProducer(producer.getName());
                movieProducer.getMovie().getIndications().forEach(indication -> {
                    if (indication.getWinner() != null && indication.getWinner()) {
                        if (dto.getPreviousWin() == 0) {
                            dto.setPreviousWin(indication.getPremium().getYear());
                        } else {
                            if (indication.getPremium().getYear() <= dto.getPreviousWin()) {
                                if (dto.getPreviousWin() >= dto.getFollowingWin())
                                    dto.setFollowingWin(dto.getPreviousWin());
                                dto.setPreviousWin(indication.getPremium().getYear());
                            }
//                            else
//                                dto.setFollowingWin(indication.getPremium().getYear());
                        }

                    }
                });
                if (dto.getFollowingWin() > 0)
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

        public int getInterval() {
            return followingWin - previousWin;
        }
    }
}
