package me.remind.user.manager.services;

import me.remind.user.manager.config.GithubConfig;
import me.remind.user.manager.domain.github.UserRepositoryResponse;
import me.remind.user.manager.exceptions.NotFoundException;
import me.remind.user.manager.services.iml.GithubApiServiceIml;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GithubApiServiceUnitTests {
    @Mock
    private RestTemplate restTemplate;

    @Mock
    private GithubConfig config;

    @Mock
    private GithubConfig.GithubApiConfig apiConfig;

    @InjectMocks
    private GithubApiServiceIml service;

    @Test
    public void given_valid_userName_should_return_response() {
        // arrange
        String userName = "test1";
        UserRepositoryResponse response = new UserRepositoryResponse();

        when(config.getApi()).thenReturn(apiConfig);
        when(apiConfig.getUrl()).thenReturn("http://api.github.com");
        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.<HttpEntity<?>> any(),
                ArgumentMatchers.<ParameterizedTypeReference<List<UserRepositoryResponse>>>any()
        )).thenReturn(new ResponseEntity<>(Collections.singletonList(response), HttpStatus.OK));

        // act
        List<UserRepositoryResponse> result = service.getUserRepositories(userName);

        // assert
        assertEquals(Collections.singletonList(response), result);
    }

    @Test
    public void given_invalid_userName_should_throw_not_found_exception() {
        // arrange
        String userName = "test1";
        UserRepositoryResponse response = new UserRepositoryResponse();

        when(config.getApi()).thenReturn(apiConfig);
        when(apiConfig.getUrl()).thenReturn("http://api.github.com");
        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.<HttpEntity<?>> any(),
                ArgumentMatchers.<ParameterizedTypeReference<List<UserRepositoryResponse>>>any()
        )).thenReturn(new ResponseEntity<>(Collections.singletonList(response), HttpStatus.NOT_FOUND));

        // act
        assertThrows(NotFoundException.class, () -> {
            service.getUserRepositories(userName);
        });
    }
}
