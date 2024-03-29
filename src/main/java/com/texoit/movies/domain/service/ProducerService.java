package com.texoit.movies.domain.service;

import com.texoit.movies.domain.entities.MovieProducer;
import com.texoit.movies.domain.entities.Producer;
import com.texoit.movies.domain.entities.dto.ProducerDTO;
import com.texoit.movies.domain.repository.IProducerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProducerService {

    /**
     *
     */
    private final IProducerRepository producerRepository;

    /**
     * @param min Boolean
     * @param max Boolean
     * @return Object
     */
    public Object getIntervals(final Boolean min, final Boolean max) {

        final Set<ProducerDTO> producerDTOS = this.getIntervals();

        final ProducerDTO.WrapperDTO wrapperDto = new ProducerDTO.WrapperDTO();

        if (min != null && min)
            wrapperDto.setMin(Set.of(producerDTOS.stream().sorted(Comparator.comparingInt(ProducerDTO::getInterval)).collect(Collectors.toList()).get(0)));
        if (max != null && max)
            wrapperDto.setMax(Set.of(producerDTOS.stream().sorted(Comparator.comparingInt(ProducerDTO::getInterval)).collect(Collectors.toList()).get(producerDTOS.size() - 1)));

        if (min == null && max == null)
            return this.getIntervals();
        else
            return wrapperDto;

    }

    /**
     * @return List<Producer>
     */
    private Set<ProducerDTO> getIntervals() {

        final List<Producer> producers = producerRepository.findAll();
        final Set<ProducerDTO> producerDTOS = new HashSet<>();

        producers.forEach(producer -> {

            final List<MovieProducer> movieProducers = producer.getMovies().stream()
                    .filter(movieProducer ->
                            !movieProducer.getMovie().getIndications().isEmpty() && movieProducer.getMovie().getIndications().get(0).getWinner() != null && movieProducer.getMovie().getIndications().get(0).getWinner()
                    )
                    .sorted(Comparator.comparingInt(o ->
                            o.getMovie().getIndications().get(0).getPremium().getYear())
                    )
                    .collect(Collectors.toList());

            if (movieProducers.size() > 1)
                for (int i = 0; i < movieProducers.size(); i++) {
                    final ProducerDTO producerDto = new ProducerDTO(producer.getName(), movieProducers.get(0).getMovie().getIndications().get(0).getPremium().getYear(), movieProducers.get(1).getMovie().getIndications().get(0).getPremium().getYear());
                    if (i + 1 != movieProducers.size())
                        if (movieProducers.get(i).getMovie().getIndications().get(0).getPremium().getYear() - movieProducers.get(i + 1).getMovie().getIndications().get(0).getPremium().getYear() < producerDto.getInterval()) {
                            producerDto.setPreviousWin(movieProducers.get(i).getMovie().getIndications().get(0).getPremium().getYear());
                            producerDto.setFollowingWin(movieProducers.get(i + 1).getMovie().getIndications().get(0).getPremium().getYear());
                        }

                    if (producerDto.getInterval() > 0)
                        producerDTOS.add(producerDto);
                }


        });

        return producerDTOS;

    }


}
