package com.texoit.movies.application;

import com.texoit.movies.domain.entities.Producer;
import com.texoit.movies.domain.repository.IProducerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/producers")
@RequiredArgsConstructor
public class ProducersResource {

    /**
     *
     */
    private final IProducerRepository producerRepository;

    /**
     * @return List<Movie>
     */
    @GetMapping
    public List<Producer> findAll() {
        return producerRepository.findAll();
    }

}
