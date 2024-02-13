package com.example.github_api_client.dto;

import lombok.Builder;

import java.util.List;

/**
 * @author Artem Kovalov on 13.02.2024
 */
@Builder
public record GitHubRepositoryDto(String repoName, String ownerLogin, List<GitHubBranchDto> repoBranches,
                                  Boolean isFork) {
}
