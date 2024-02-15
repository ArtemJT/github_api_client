package com.example.github_api_client.mapper;

import com.example.github_api_client.dto.GitHubBranchDto;
import com.example.github_api_client.dto.GitHubRepositoryDto;
import com.example.github_api_client.exception.RepositoryNotFoundException;
import org.kohsuke.github.GHBranch;
import org.kohsuke.github.GHRepository;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Artem Kovalov on 14.02.2024
 */
public record GitHubRepoMapper() {

    public static List<GitHubRepositoryDto> makeGitHubRepositoryDtoList(Map<String, GHRepository> repositories) {
        if (repositories.isEmpty()) {
            throw new RepositoryNotFoundException();
        }
        return repositories.values().stream()
                .map(repo -> {
                    try {
                        return mapGitHubRepositoryToDto(repo);
                    } catch (IOException e) {
                        throw new RepositoryNotFoundException();
                    }
                })
                .toList();
    }

    private static List<GitHubBranchDto> makeGitHubBranchDtoList(GHRepository repository) throws IOException {
        return repository.getBranches().values().stream()
                .map(GitHubRepoMapper::mapGitHubBranchToDto)
                .toList();
    }

    private static GitHubRepositoryDto mapGitHubRepositoryToDto(GHRepository repository) throws IOException {
        return GitHubRepositoryDto.builder()
                .repoName(repository.getName())
                .ownerLogin(repository.getOwnerName())
                .repoBranches(makeGitHubBranchDtoList(repository))
                .isFork(repository.isFork())
                .build();
    }

    private static GitHubBranchDto mapGitHubBranchToDto(GHBranch branch) {
        return GitHubBranchDto.builder()
                .branchName(branch.getName())
                .commitSha1(branch.getSHA1())
                .build();
    }
}
