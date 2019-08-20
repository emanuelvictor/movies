package com.texoit.movies.domain.service;

import com.texoit.movies.domain.AbstractIntegrationTests;
import com.texoit.movies.domain.entities.dto.ProducerDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
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

        final ProducerDto.WrapperDto testWrapperDto = new ProducerDto.WrapperDto(new ProducerDto("Joel Silver", 1990, 1991), new ProducerDto("Matthew Vaughn", 2002, 2015));

        Assert.assertEquals(testWrapperDto, producerService.getIntervals(true, true));
    }

    @Test
    public void getProducersMinFromServiceMustPass() {

        final ProducerDto.WrapperDto testWrapperDto = new ProducerDto.WrapperDto();

        testWrapperDto.setMin(Set.of(new ProducerDto("Joel Silver", 1990, 1991)));

        Assert.assertEquals(testWrapperDto, producerService.getIntervals(true, null));
    }

    @Test
    public void getProducersMaxFromServiceMustPass() {

        final ProducerDto.WrapperDto testWrapperDto = new ProducerDto.WrapperDto();

        testWrapperDto.setMax(Set.of(new ProducerDto("Matthew Vaughn", 2002, 2015)));

        Assert.assertEquals(testWrapperDto, producerService.getIntervals(null, true));
    }

    @Test
    public void getProducersFromWebServiceMustPass() {

        final ProducerDto.WrapperDto testWrapperDto = new ProducerDto.WrapperDto(new ProducerDto("Joel Silver", 1990, 1991), new ProducerDto("Matthew Vaughn", 2002, 2015));

        Mockito.when(restTemplate.getForEntity("http://localhost:8080/producers?min=true&max=true", ProducerDto.WrapperDto.class)).thenReturn(new ResponseEntity<>(testWrapperDto, HttpStatus.OK));

        Assert.assertEquals(testWrapperDto, producerService.getIntervals(true, true));
    }
}
