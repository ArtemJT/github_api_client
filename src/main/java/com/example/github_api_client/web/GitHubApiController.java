package com.example.github_api_client.web;

import com.example.github_api_client.service.GitHubApiServiceBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
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

    @GetMapping("/repos")
    @ResponseStatus(HttpStatus.OK)
    public String getRepo(@RequestHeader String userName, @RequestHeader(HttpHeaders.ACCEPT) String acceptHeader) throws IOException {

        if (acceptHeader.equals("application/json")) {
            log.info("ACCEPT HEADER: " + acceptHeader);
        } else {
            throw new HeadlessException();
        }

        return mapper.writeValueAsString(gitHubApiServiceBean.gitHubReposNotFork(userName));
    }
}
