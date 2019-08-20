package com.texoit.movies.domain.service;

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

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
public class ProducerServiceIntegrationTest {

    @Mock
    private RestTemplate restTemplate;

    @Autowired
    private ProducerService producerService;

    @Autowired
    private ImportService importService;

    @Before
    public void init() {

        importService.getMovieRepository().deleteAll();

        importService.importt();
    }

    @Test
    public void getProducersFromServiceMustPass() {

        final ProducerDto.WrapperDto testWrapperDto = new ProducerDto.WrapperDto(new ProducerDto("Joel Silver", 1990, 1991), new ProducerDto("Matthew Vaughn", 2002, 2015));

        Assert.assertEquals(testWrapperDto,  producerService.findAll());
    }

    @Test
    public void getProducersFromWebServiceMustPass() {

        final ProducerDto.WrapperDto testWrapperDto = new ProducerDto.WrapperDto(new ProducerDto("Joel Silver", 1990, 1991), new ProducerDto("Matthew Vaughn", 2002, 2015));

        Mockito.when(restTemplate.getForEntity("http://localhost:8080/producers", ProducerDto.WrapperDto.class)).thenReturn(new ResponseEntity<>(testWrapperDto, HttpStatus.OK));

        Assert.assertEquals(testWrapperDto,  producerService.findAll());
    }
}
