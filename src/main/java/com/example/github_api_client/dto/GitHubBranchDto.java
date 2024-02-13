package com.example.github_api_client.dto;

import lombok.Builder;

/**
 * @author Artem Kovalov on 13.02.2024
 */
@Builder
public record GitHubBranchDto(String branchName, String commitSha1) {

}
