package com.example.yamp.usersvc.controller;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestRetryController {


    @GetMapping("/test-endpoint")
    public void testRetry() {
        log.info("This retry endpoint is invoked at this time: {}", System.currentTimeMillis());
        System.out.println("Test Retry");
    }
}
