package me.remind.user.manager.services;

import me.remind.user.manager.domain.github.UserRepositoryResponse;

import java.util.List;

public interface GithubApiService {
    List<UserRepositoryResponse> getUserRepositories(String userName);
}
