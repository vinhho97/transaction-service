package com.example.serviceB.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ExecutorConfig {

    @Bean
    public ExecutorService transactionExecutor() {
        return new ThreadPoolExecutor(
                10,                    // core threads
                50,                    // max threads
                60L,                   // keep-alive time
                TimeUnit.SECONDS,      // time unit for keep-alive
                new LinkedBlockingQueue<>(100), // work queue with capacity
                new ThreadPoolExecutor.CallerRunsPolicy() // rejection policy
        );
    }
}
