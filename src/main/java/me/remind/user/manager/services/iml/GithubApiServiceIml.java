package me.remind.user.manager.services.iml;

import me.remind.user.manager.config.GithubConfig;
import me.remind.user.manager.domain.github.UserRepositoryResponse;
import me.remind.user.manager.exceptions.NotFoundException;
import me.remind.user.manager.services.GithubApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GithubApiServiceIml implements GithubApiService {
    private final Logger logger = LoggerFactory.getLogger(GithubApiServiceIml.class);
    private final RestTemplate restTemplate;
    private final GithubConfig githubConfig;

    @Autowired
    public GithubApiServiceIml(RestTemplate restTemplate,
                               GithubConfig githubConfig) {
        this.restTemplate = restTemplate;
        this.githubConfig = githubConfig;
    }

    @Override
    public List<UserRepositoryResponse> getUserRepositories(String userName) {
        String url = githubConfig.getApi().getUrl() +
                "/users/" + userName + "/repos";

        ResponseEntity<List<UserRepositoryResponse>> response = restTemplate.exchange(url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<UserRepositoryResponse>>() {
                });

        if (response.getStatusCode() != HttpStatus.OK) {
            logger.error("Github API failed with response status: " + response.getStatusCodeValue());
            throw new NotFoundException("Github user by " + userName + " is not found!");
        }

        return response.getBody();
    }
}
