package com.project.springTest.SpringTest.service;


import com.project.springTest.SpringTest.service.impl.AopServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class AopServiceImplTest {

    @Autowired
    private AopServiceImpl aopService;


    @Test
    void testGetName(){
        aopService.successMethod();
    }

    @Test
    void testFailedMethod(){
        aopService.failedMethod();
    }


}
