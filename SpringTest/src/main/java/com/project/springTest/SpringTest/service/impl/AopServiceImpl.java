package com.project.springTest.SpringTest.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AopServiceImpl {

    public String successMethod(){

        try{
            log.info("Simulating some processing...");
            Thread.sleep(1000); // Simulate processing time
        } catch (InterruptedException e){
            log.info("Processing interrupted {}", e.getMessage());
        }
        return "AOP Service";
    }


    public String failedMethod(){
        log.info("AOP Service method failedMethod() called");
        try{
            log.info("Failed Method...");
            Thread.sleep(1000); // Simulate processing time
            throw new RuntimeException("Simulated exception in failedMethod");
        } catch (InterruptedException e){
            log.info("Failed {}", e.getMessage());
        }

        return "Failed Method executed";
    }
}
