package com.example.github_api_client.service;

import com.example.github_api_client.dto.GitHubRepositoryDto;
import com.example.github_api_client.mapper.GitHubRepoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author Artem Kovalov on 13.02.2024
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class GitHubApiServiceBean implements GitHubApiService {

    private final GitHub gitHub;

    @Override
    public GHUser getGHUser(String userName) throws IOException {
        return gitHub.getUser(userName);
    }

    @Override
    public List<GitHubRepositoryDto> gitHubRepos(String userName) throws IOException {
        return GitHubRepoMapper.makeGitHubRepositoryDtoList(getGHUser(userName).getRepositories());
    }

    @Override
    public List<GitHubRepositoryDto> gitHubReposNotFork(String userName) throws IOException {
        return gitHubRepos(userName).stream().filter(repo -> !repo.isFork()).toList();
    }
}
