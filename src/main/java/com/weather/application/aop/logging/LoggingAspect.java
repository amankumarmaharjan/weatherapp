package com.weather.application.aop.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Aspect
@Component
public class LoggingAspect {

    public void logBefore(JoinPoint joinPoint, Logger log) {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String parameters = (String) ((Stream) Arrays.stream(args).sequential()).map(Object::toString).collect(Collectors.joining());
        log.info("Entering method: {}.{} with parameters: {}", className, methodName, parameters);
    }

    public void logAfter(JoinPoint joinPoint, Logger log, long executionTime) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        log.info("Exiting method: {}.{}: Execution time: {} milliseconds", className, methodName, executionTime);
    }

    @Around("@annotation(LogExecution)")
    public Object executionTimeLogger(ProceedingJoinPoint joinPoint) {
        Logger log = logger(joinPoint);
        try {
            long startTime = System.currentTimeMillis();
            logBefore(joinPoint, log);
            Object proceed = joinPoint.proceed();
            long executionTime = (System.currentTimeMillis() - startTime);
            logAfter(joinPoint, log, executionTime);
            return proceed;
        } catch (Throwable e) {
            log.error("ERROR  occurred while calculating method execution time for {}", joinPoint.getSignature(), e);
            return e;
        }
    }

    private Logger logger(JoinPoint joinPoint) {
        return LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringTypeName());
    }

    @AfterThrowing(value = "@annotation(LogExecution)", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        logger(joinPoint).error("Exception in {}() with cause = '{}' and exception = '{}'",
            joinPoint.getSignature().getName(),
            e.getCause() != null ? e.getCause() : "NULL",
            e.getMessage(),
            e);
    }

}
