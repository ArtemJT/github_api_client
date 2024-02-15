package com.example.github_api_client.exception;

import org.kohsuke.github.GHFileNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * @author Artem Kovalov on 14.02.2024
 */
@ControllerAdvice
public class GitHubApiGlobalExceptionHandler {

    @Value("${templates.exception.gHFileNotFound.mustBeNotEmpty}")
    private String mustNotBeEmpty;
    @Value("${templates.exception.gHFileNotFound.userNotFound}")
    private String notFound;
    @Value("${templates.exception.mediaTypeNotAccept}")
    private String acceptResponse;
    @Value("${templates.exception.repositoryNotFound}")
    private String repositoryNotFound;
    @Value("${templates.exception.notForkRepositoryNotFound}")
    private String notForkRepositoryNotFound;

    @ExceptionHandler(GHFileNotFoundException.class)
    public ResponseEntity<ErrorDetails> ghFileNotFoundException(WebRequest request) {
        String requestUserName = request.getParameter("userName");
        notFound += requestUserName;
        String response = (requestUserName == null || requestUserName.isBlank()) ? mustNotBeEmpty : notFound;
        return ErrorDetails.getResponseEntity(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<ErrorDetails> httpMediaTypeException() {
        return ErrorDetails.getResponseEntity(acceptResponse, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(RepositoryNotFoundException.class)
    public ResponseEntity<ErrorDetails> repositoryNotFoundException() {
        return ErrorDetails.getResponseEntity(repositoryNotFound, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotForkRepositoryNotFoundException.class)
    public ResponseEntity<ErrorDetails> notForkRepositoryNotFoundException() {
        return ErrorDetails.getResponseEntity(notForkRepositoryNotFound, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorDetails> missingServletRequestParameterException(Exception ex) {
        return ErrorDetails.getResponseEntity(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }
}
