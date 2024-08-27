package com.todo.springadvancetask.weather;

import com.todo.springadvancetask.dto.schedule.WheatherDto;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootTest
public class WeatherApiTest {

  private RestTemplate restTemplate;

  @Autowired
  public WeatherApiTest(RestTemplateBuilder builder) {
    restTemplate = builder.build();
  }

  @Test
  void Test() {
    URI uri = UriComponentsBuilder
        .fromUriString("https://f-api.github.io")
        .path("/f-api/weather.json")
        .build()
        .toUri();
    ResponseEntity<List<WheatherDto>> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<WheatherDto>>() {});
    List<WheatherDto> wheatherDtos = responseEntity.getBody();
  }
}
