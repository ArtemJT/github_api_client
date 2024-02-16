package com.example.github_api_client.exception;

import com.example.github_api_client.exception.custom_exceptions.GHNotForkRepositoryNotFoundException;
import com.example.github_api_client.exception.custom_exceptions.GHRepositoryBranchNotFoundException;
import com.example.github_api_client.exception.custom_exceptions.GHRepositoryNotFoundException;
import com.example.github_api_client.exception.custom_exceptions.GHUserNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

/**
 * @author Artem Kovalov on 14.02.2024
 */
@ControllerAdvice
public class GitHubApiGlobalExceptionHandler {

    @Value("${templates.exception.mustBeNotEmpty}")
    private String mustNotBeEmpty;
    @Value("${templates.exception.userNotFound}")
    private String notFound;
    @Value("${templates.exception.mediaTypeNotAccept}")
    private String acceptResponse;
    @Value("${templates.exception.repositoryNotFound}")
    private String repositoryNotFound;
    @Value("${templates.exception.notForkRepositoryNotFound}")
    private String notForkRepositoryNotFound;
    @Value("${templates.exception.branchNotFound}")
    private String branchNotFound;

    @ExceptionHandler(GHUserNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDetails> ghUserNotFoundException(WebRequest request) {
        String requestUserName = request.getParameter("userName");
        notFound += requestUserName;
        String response = (requestUserName == null || requestUserName.isBlank()) ? mustNotBeEmpty : notFound;
        return ErrorDetails.getResponseEntity(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    public ResponseEntity<ErrorDetails> httpMediaTypeException() {
        return ErrorDetails.getResponseEntity(acceptResponse, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(GHRepositoryNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDetails> repositoryNotFoundException() {
        return ErrorDetails.getResponseEntity(repositoryNotFound, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GHRepositoryBranchNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDetails> repositoryBranchNotFoundException() {
        return ErrorDetails.getResponseEntity(branchNotFound, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GHNotForkRepositoryNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDetails> notForkRepositoryNotFoundException() {
        return ErrorDetails.getResponseEntity(notForkRepositoryNotFound, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDetails> missingServletRequestParameterException(Exception ex) {
        return ErrorDetails.getResponseEntity(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }
}
