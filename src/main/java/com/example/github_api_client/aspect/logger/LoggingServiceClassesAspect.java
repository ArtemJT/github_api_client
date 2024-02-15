package com.example.github_api_client.aspect.logger;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static ch.qos.logback.core.pattern.color.ANSIConstants.*;

/**
 * @author Artem Kovalov on 15.02.2024
 */
@Aspect
@Component
@Slf4j
public record LoggingServiceClassesAspect() {

    private static final String LOG_COLOR_RESET = ESC_START + RESET + ESC_END;

    @Pointcut("execution(public * com.example.github_api_client.service.GitHubApiServiceBean.*(..))")
    public void callAtGitHubApiServicePublicMethods() {
    }

    @Before("callAtGitHubApiServicePublicMethods()")
    public void logBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String argsLog = args.length > 0 ? "Args count - " + args.length : "";
        log.info(setLogMessage(joinPoint) + "start. " + argsLog + LOG_COLOR_RESET);
    }

    @AfterReturning(value = "callAtGitHubApiServicePublicMethods()", returning = "returningValue")
    public void logAfter(JoinPoint joinPoint, Object returningValue) {
        StringBuilder message = new StringBuilder();
        if (returningValue != null) {
            message.append(" Returns - ");
            if (returningValue instanceof Collection<?> returningCollect) {
                message.append("Collection size - ").append(returningCollect.size());
            } else if (returningValue instanceof byte[]) {
                message.append("File as byte[]");
            } else {
                message.append(returningValue);
            }
        }
        log.info(setLogMessage(joinPoint) + "end. " + message + LOG_COLOR_RESET);
    }

    @AfterThrowing(value = "callAtGitHubApiServicePublicMethods()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Exception exception) {
        String logMessage = setLogMessage(joinPoint);
        String exceptionName = exception.getClass().getSimpleName();
        String logColorRed = ESC_START + RED_FG + ESC_END;
        log.info(logMessage + logColorRed + "Exception: " + exceptionName + LOG_COLOR_RESET);
    }

    private String setLogMessage(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        return ESC_START + BLUE_FG + ESC_END + "Service: " + methodName + " - ";
    }
}
