package com.example.github_api_client.service;

import com.example.github_api_client.dto.GitHubBranchDto;
import com.example.github_api_client.dto.GitHubRepositoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHBranch;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
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

    @Override
    public GHUser getGHUser(String userName) throws IOException {
        GHUser user = gitHub.getUser(userName);
        log.info("USER LOGIN: " + user.getLogin());
        return user;
    }

    @Override
    public List<GitHubRepositoryDto> gitHubRepos(String userName) throws IOException {
        GHUser ghUser = getGHUser(userName);
        Map<String, GHRepository> repositories = ghUser.getRepositories();
        List<GitHubRepositoryDto> gitHubRepositoryDtoList = new ArrayList<>();
        for (GHRepository repository : repositories.values()) {
            List<GitHubBranchDto> branchList = new ArrayList<>();
            for (GHBranch branch : repository.getBranches().values()) {
                var repoBranch = GitHubBranchDto.builder()
                        .branchName(branch.getName())
                        .commitSha1(branch.getSHA1())
                        .build();
                branchList.add(repoBranch);
            }
            var repo = GitHubRepositoryDto.builder()
                    .repoName(repository.getName())
                    .ownerLogin(repository.getOwnerName())
                    .repoBranches(branchList)
                    .isFork(repository.isFork())
                    .build();
            gitHubRepositoryDtoList.add(repo);
        }
        return gitHubRepositoryDtoList;
    }

    @Override
    public List<GitHubRepositoryDto> gitHubReposNotFork(String userName) throws IOException {
        return gitHubRepos(userName).stream().filter(repo -> !repo.isFork()).toList();
    }
}
