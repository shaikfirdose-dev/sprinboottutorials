package com.project.springTest.SpringTest.aspects;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Slf4j
//@Aspect
public class LogginAspects {

    @After("execution(* com.project.springTest.SpringTest.service.impl.AopServiceImpl.*(..))")
    public void beforeMethodExecute(JoinPoint joinPoint){
        log.info("Before executing method: {}", joinPoint.getSignature());
    }
}
