package com.example.github_api_client.config;

import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author Artem Kovalov on 13.02.2024
 */
@Configuration
public class GitHubApiConfig {

    @Bean
    public GitHub gitHub() throws IOException {
        return new GitHubBuilder()
                .build();
    }
}
