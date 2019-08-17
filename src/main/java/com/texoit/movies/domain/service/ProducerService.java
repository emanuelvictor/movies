package com.texoit.movies.domain.service;

import com.texoit.movies.domain.entities.Indication;
import com.texoit.movies.domain.entities.Movie;
import com.texoit.movies.domain.entities.MovieProducer;
import com.texoit.movies.domain.entities.Producer;
import com.texoit.movies.domain.entities.dto.ProducerDto;
import com.texoit.movies.domain.repository.IProducerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProducerService {

    private final IProducerRepository producerRepository;

    public ProducerDto.WrapperDto findAll() {
        final List<Producer> producers = producerRepository.findAll();
        final Set<ProducerDto> producerDtos = new HashSet<>();


        producers.forEach(producer -> {
            final List<MovieProducer> movieProducers = producer.getMovies().stream()
                    .filter(movieProducer ->
                            /*movieProducer.getProducer().getName().equals("Kevin Costner") && */!movieProducer.getMovie().getIndications().isEmpty() && movieProducer.getMovie().getIndications().get(0).getWinner() != null && movieProducer.getMovie().getIndications().get(0).getWinner()
                    )
                    .sorted(Comparator.comparingInt(o ->
                            o.getMovie().getIndications().get(0).getPremium().getYear())
                    )
                    .collect(Collectors.toList());

            if (movieProducers.size() > 1)
                for (int i = 0; i < movieProducers.size(); i++) {
                    final ProducerDto producerMinDto = new ProducerDto(producer.getName(), movieProducers.get(0).getMovie().getIndications().get(0).getPremium().getYear(), movieProducers.get(1).getMovie().getIndications().get(0).getPremium().getYear());
                    if (i + 1 != movieProducers.size())
                        if (movieProducers.get(i).getMovie().getIndications().get(0).getPremium().getYear() - movieProducers.get(i + 1).getMovie().getIndications().get(0).getPremium().getYear() < producerMinDto.getInterval()) {
                            producerMinDto.setPreviousWin(movieProducers.get(i).getMovie().getIndications().get(0).getPremium().getYear());
                            producerMinDto.setFollowingWin(movieProducers.get(i + 1).getMovie().getIndications().get(0).getPremium().getYear());
                        }

//                if (producerMinDto.getProducer().equals("Kevin Costner"))
                    if (producerMinDto.getInterval() > 0)
                        producerDtos.add(producerMinDto);
                }


        });


        final ProducerDto.WrapperDto dto = new ProducerDto.WrapperDto();
        dto.getMin().add(producerDtos.stream().sorted(Comparator.comparingInt(ProducerDto::getInterval)).collect(Collectors.toList()).get(0));
        dto.getMax().add(producerDtos.stream().sorted(Comparator.comparingInt(ProducerDto::getInterval)).collect(Collectors.toList()).get(producerDtos.size() - 1));

        return dto;
    }


}
