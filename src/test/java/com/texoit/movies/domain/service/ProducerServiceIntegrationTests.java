package com.texoit.movies.domain.service;

import com.texoit.movies.domain.AbstractIntegrationTests;
import com.texoit.movies.domain.entities.dto.ProducerDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

public class ProducerServiceIntegrationTests extends AbstractIntegrationTests {

    @Mock
    private RestTemplate restTemplate;

    @Autowired
    private ImportService importService;

    @Autowired
    private ProducerService producerService;

    @Before
    public void init() {

        importService.getMovieRepository().deleteAll();

        importService.importt();
    }

    @Test
    public void getProducersMinAndMaxFromServiceMustPass() {

        final ProducerDTO.WrapperDTO testWrapperDTO = new ProducerDTO.WrapperDTO(new ProducerDTO("Joel Silver", 1990, 1991), new ProducerDTO("Matthew Vaughn", 2002, 2015));

        Assert.assertEquals(testWrapperDTO, producerService.getIntervals(true, true));
    }

    @Test
    public void getProducersMinFromServiceMustPass() {

        final ProducerDTO.WrapperDTO testWrapperDTO = new ProducerDTO.WrapperDTO();

        testWrapperDTO.setMin(Set.of(new ProducerDTO("Joel Silver", 1990, 1991)));

        Assert.assertEquals(testWrapperDTO, producerService.getIntervals(true, null));
    }

    @Test
    public void getProducersMaxFromServiceMustPass() {

        final ProducerDTO.WrapperDTO testWrapperDTO = new ProducerDTO.WrapperDTO();

        testWrapperDTO.setMax(Set.of(new ProducerDTO("Matthew Vaughn", 2002, 2015)));

        Assert.assertEquals(testWrapperDTO, producerService.getIntervals(null, true));
    }

    @Test
    public void getProducersFromWebServiceMustPass() {

        final ProducerDTO.WrapperDTO testWrapperDTO = new ProducerDTO.WrapperDTO(new ProducerDTO("Joel Silver", 1990, 1991), new ProducerDTO("Matthew Vaughn", 2002, 2015));

        Mockito.when(restTemplate.getForEntity("http://localhost:8080/producers?min=true&max=true", ProducerDTO.WrapperDTO.class)).thenReturn(new ResponseEntity<>(testWrapperDTO, HttpStatus.OK));

        Assert.assertEquals(testWrapperDTO, producerService.getIntervals(true, true));
    }
}
