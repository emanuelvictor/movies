package com.texoit.movies.application;

import com.texoit.movies.domain.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/producers")
@RequiredArgsConstructor
public class ProducersResource {

    /**
     *
     */
    private final ProducerService producerService;

    /**
     * @return List<Movie>
     */
    @GetMapping
    public Set<ProducerService.Dto> findAll() {
        return producerService.findAll();
    }

}
