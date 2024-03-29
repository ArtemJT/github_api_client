package com.example.github_api_client.web;

import com.example.github_api_client.dto.GitHubRepositoryDto;
import com.example.github_api_client.service.GitHubApiServiceBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Artem Kovalov on 13.02.2024
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class GitHubApiController {

    private final GitHubApiServiceBean gitHubApiServiceBean;

    @GetMapping("/repos/not-fork")
    @ResponseStatus(HttpStatus.OK)
    public List<GitHubRepositoryDto> getNotForkRepositories(@RequestParam String userName) {
        return gitHubApiServiceBean.getGHNotForkRepositoriesList(userName);
    }

    @GetMapping("/repos")
    @ResponseStatus(HttpStatus.OK)
    public List<GitHubRepositoryDto> getRepositories(@RequestParam String userName) {
        return gitHubApiServiceBean.getGHRepositoriesList(userName);
    }
}
