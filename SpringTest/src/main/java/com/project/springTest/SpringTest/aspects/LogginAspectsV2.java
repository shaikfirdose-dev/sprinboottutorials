package com.project.springTest.SpringTest.aspects;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogginAspectsV2 {


    @Before("allServiceMethodPointCut()")
    public void beforeServiceMethodCalls(JoinPoint joinPoint){
        log.info("Before executing method: {}", joinPoint.getSignature());
    }


    @AfterReturning(value = "allServiceMethodPointCut()", returning = "result")
    public void afterReturningServiceMethodCalls(JoinPoint joinPoint, Object result){
        log.info("After returning executing method: {} with result: {}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(value = "allServiceMethodPointCut()")
    public void afterServiceMethodCallsThrows(JoinPoint joinPoint){
        log.info("After reception throwing method: {}", joinPoint.getSignature());
    }

    @Pointcut("execution(* com.project.springTest.SpringTest.service.impl.*.*(..))")
    public void allServiceMethodPointCut(){
    }




}
