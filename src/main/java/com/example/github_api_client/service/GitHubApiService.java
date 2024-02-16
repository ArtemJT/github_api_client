package com.example.github_api_client.service;

import com.example.github_api_client.dto.GitHubRepositoryDto;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;

import java.util.List;
import java.util.Map;

/**
 * @author Artem Kovalov on 14.02.2024
 */
public interface GitHubApiService {

    GHUser getGHUser(String userName);

    Map<String, GHRepository> getGHRepositories(String userName);

    List<GitHubRepositoryDto> getGHRepositoriesList(String userName);

    List<GitHubRepositoryDto> getGHNotForkRepositoriesList(String userName);
}
