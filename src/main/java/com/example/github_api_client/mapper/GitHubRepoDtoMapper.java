package com.example.github_api_client.mapper;

import com.example.github_api_client.dto.GitHubBranchDto;
import com.example.github_api_client.dto.GitHubRepositoryDto;
import com.example.github_api_client.exception.custom_exceptions.GHRepositoryBranchNotFoundException;
import org.kohsuke.github.GHBranch;
import org.kohsuke.github.GHRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Artem Kovalov on 14.02.2024
 */
@Component
public class GitHubRepoDtoMapper {

    public List<GitHubRepositoryDto> makeGitHubRepositoryDtoList(Map<String, GHRepository> repositories) {
        return repositories.values().stream().map(this::mapGitHubRepositoryToDto).toList();
    }

    private GitHubRepositoryDto mapGitHubRepositoryToDto(GHRepository repository) {
        Map<String, GHBranch> branches = getGHBranches(repository);
        return GitHubRepositoryDto.builder()
                .repoName(repository.getName())
                .ownerLogin(repository.getOwnerName())
                .repoBranches(makeGitHubBranchDtoList(branches))
                .isFork(repository.isFork())
                .build();
    }

    private Map<String, GHBranch> getGHBranches(GHRepository repository) {
        try {
            return repository.getBranches();
        } catch (IOException e) {
            throw new GHRepositoryBranchNotFoundException();
        }
    }

    private List<GitHubBranchDto> makeGitHubBranchDtoList(Map<String, GHBranch> branches) {
        return branches
                .values().stream()
                .map(this::mapGitHubBranchToDto).toList();
    }

    private GitHubBranchDto mapGitHubBranchToDto(GHBranch branch) {
        return GitHubBranchDto.builder()
                .branchName(branch.getName())
                .commitSha1(branch.getSHA1())
                .build();
    }
}
