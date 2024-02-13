package com.example.github_api_client.service;

import com.example.github_api_client.dto.GitHubRepositoryDto;
import org.kohsuke.github.GHUser;

import java.io.IOException;
import java.util.List;

/**
 * @author Artem Kovalov on 14.02.2024
 */
public interface GitHubApiService {

    GHUser getGHUser(String userName) throws IOException;

    List<GitHubRepositoryDto> gitHubRepos(String userName) throws IOException;

    List<GitHubRepositoryDto> gitHubReposNotFork(String userName) throws IOException;
}
