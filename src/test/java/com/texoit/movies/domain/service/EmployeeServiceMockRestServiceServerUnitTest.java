//package com.texoit.movies.domain.service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.client.ExpectedCount;
//import org.springframework.test.web.client.MockRestServiceServer;
//import org.springframework.web.client.RestTemplate;
//
//import java.net.URI;
//
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
//import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
//
//@RunWith(SpringRunner.class)
//@ActiveProfiles("test")
//@SpringBootTest
//public class EmployeeServiceMockRestServiceServerUnitTest {
//
//    @Autowired
//    private ProducerService producerService;
//    @Autowired
//    private RestTemplate restTemplate;
//
//    private MockRestServiceServer mockServer;
//    private ObjectMapper mapper = new ObjectMapper();
//
//    @Before
//    public void init() {
//        mockServer = MockRestServiceServer.createServer(restTemplate);
//    }
//
//    @Test
//    public void givenMockingIsDoneByMockRestServiceServer_whenGetIsCalled_thenReturnsMockedObject()() {
//        ProducerService.WrapperDto emp = new ProducerService.WrapperDto();
//        mockServer.expect(ExpectedCount.once(),
//                requestTo(new URI("http://localhost:8080/producer")))
//                .andExpect(method(HttpMethod.GET))
//                .andRespond(withStatus(HttpStatus.OK)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(mapper.writeValueAsString(emp))
//                );
//
//        ProducerService.WrapperDto dto = producerService.findAll();
//        mockServer.verify();
//        Assert.assertEquals(emp, dto);
//    }
//}
