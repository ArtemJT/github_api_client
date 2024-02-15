package com.example.github_api_client.web;

import com.example.github_api_client.service.GitHubApiServiceBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author Artem Kovalov on 13.02.2024
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class GitHubApiController {

    private final GitHubApiServiceBean gitHubApiServiceBean;
    private final ObjectMapper mapper;

    @GetMapping("/repos/not-fork")
    @ResponseStatus(HttpStatus.OK)
    public String getNotForkRepos(@RequestParam String userName) throws IOException {
        return mapper.writeValueAsString(gitHubApiServiceBean.gitHubReposNotFork(userName));
    }

    @GetMapping("/repos")
    @ResponseStatus(HttpStatus.OK)
    public String getRepos(@RequestParam String userName) throws IOException {
        return mapper.writeValueAsString(gitHubApiServiceBean.gitHubRepos(userName));
    }
}
