package com.texoit.movies.domain.service;

import com.texoit.movies.domain.entities.Producer;
import com.texoit.movies.domain.entities.dto.ProducerDto;
import com.texoit.movies.domain.repository.IProducerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class ProducerService {

    private final IProducerRepository producerRepository;

    public ProducerDto.Dto findAll() {
        final List<Producer> producers = producerRepository.findAll();
        final Set<ProducerDto> producerDtos = new HashSet<>();

        producers.forEach(producer -> {
            final ProducerDto producerDto = new ProducerDto(producer.getName());
            producer.getMovies().forEach(movieProducer -> {
                movieProducer.getMovie().getIndications().stream().filter(indication -> Optional.ofNullable(indication.getWinner()).orElse(false)).forEach(indication -> { // Colocar um filter aqui
                    if (producerDto.getPreviousWin() == 0)
                        producerDto.setPreviousWin(indication.getPremium().getYear());
                    else if (indication.getPremium().getYear() <= producerDto.getPreviousWin()) {
                        if (producerDto.getPreviousWin() >= producerDto.getFollowingWin())
                            producerDto.setFollowingWin(producerDto.getPreviousWin());
                        producerDto.setPreviousWin(indication.getPremium().getYear());
                    }
                });
                producerDtos.add(producerDto);
            });
        });
//producerDtos.stream().max((o1, o2) -> o1.getInterval() > o2.getInterval())
        final ProducerDto.Dto dto = new ProducerDto.Dto();
        dto.getMin().add(producerDtos.stream().findFirst().get());
        dto.getMax().add(producerDtos.stream().findFirst().get());

        return dto;
    }




}
