package com.example.github_api_client.service;

import com.example.github_api_client.dto.GitHubRepositoryDto;
import com.example.github_api_client.exception.custom_exceptions.GHNotForkRepositoryNotFoundException;
import com.example.github_api_client.exception.custom_exceptions.GHRepositoryNotFoundException;
import com.example.github_api_client.exception.custom_exceptions.GHUserNotFoundException;
import com.example.github_api_client.mapper.GitHubRepoDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Artem Kovalov on 13.02.2024
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class GitHubApiServiceBean implements GitHubApiService {

    private final GitHub gitHub;
    private final GitHubRepoDtoMapper repoMapper;

    /**
     * The method get User by given UserName
     *
     * @param userName
     * @return GHUser
     */
    @Override
    public GHUser getGHUser(String userName) {
        try {
            return gitHub.getUser(userName);
        } catch (IOException e) {
            throw new GHUserNotFoundException();
        }
    }

    /**
     * The method get User's repositories by given UserName
     *
     * @param userName
     * @return Map<String, GHRepository>
     */
    @Override
    public Map<String, GHRepository> getGHRepositories(String userName) {
        try {
            Map<String, GHRepository> repositories = getGHUser(userName).getRepositories();
            if (repositories.isEmpty()) {
                throw new GHRepositoryNotFoundException();
            }
            return repositories;
        } catch (IOException e) {
            throw new GHRepositoryNotFoundException();
        }
    }

    /**
     * The method make a list of all repositories. Based on GitHubRepositoryDto
     *
     * @param userName
     * @return List<GitHubRepositoryDto>
     */
    @Override
    public List<GitHubRepositoryDto> getGHRepositoriesList(String userName) {
        return repoMapper.makeGitHubRepositoryDtoList(getGHRepositories(userName));
    }

    /**
     * The method make a list of all not-fork repositories. Based on GitHubRepositoryDto
     *
     * @param userName
     * @return List<GitHubRepositoryDto>
     */
    @Override
    public List<GitHubRepositoryDto> getGHNotForkRepositoriesList(String userName) {
        List<GitHubRepositoryDto> repositoryList = getGHRepositoriesList(userName).stream()
                .filter(repo -> !repo.isFork()).toList();
        if (repositoryList.isEmpty()) {
            throw new GHNotForkRepositoryNotFoundException();
        }
        return repositoryList;
    }
}
