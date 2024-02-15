package com.example.github_api_client.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Artem Kovalov on 15.02.2024
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotForkRepositoryNotFoundException extends RuntimeException {
}
