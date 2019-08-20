package com.texoit.movies.application.resource;

import com.texoit.movies.domain.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/producers")
@RequiredArgsConstructor
public class ProducersResource {

    /**
     *
     */
    private final ProducerService producerService;

    /**
     * @return ProducerService.ProducerDto.WrapperDto
     */
    @GetMapping
    public Object getIntervals(final Boolean min, final Boolean max) {
        return producerService.getIntervals(min, max);
    }

}
