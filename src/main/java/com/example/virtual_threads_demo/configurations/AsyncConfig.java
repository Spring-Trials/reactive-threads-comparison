package com.example.virtual_threads_demo.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executors;

@EnableAsync
@Configuration
public class AsyncConfig {
    @Bean
    public AsyncTaskExecutor asyncTaskExecutor() {
        return new AsyncTaskExecutor() {
            @Override
            public void execute(Runnable task) {
                Executors.newVirtualThreadPerTaskExecutor().execute(task);
            }
        };
    }
}
