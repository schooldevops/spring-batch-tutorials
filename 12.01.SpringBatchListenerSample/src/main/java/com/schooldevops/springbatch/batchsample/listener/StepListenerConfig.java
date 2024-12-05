package com.schooldevops.springbatch.batchsample.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class StepListenerConfig {
    @Bean
    public StepExecutionListener stepExecutionListener() {
        return new StepExecutionListener() {
            @Override
            public void beforeStep(StepExecution stepExecution) {
                log.info("------ Before Step: Step {} is starting...", stepExecution.getStepName());

            }

            @Override
            public ExitStatus afterStep(StepExecution stepExecution) {
                log.info("------ After Step: Step {} is finished.", stepExecution.getStepName());
                return stepExecution.getExitStatus();
            }
        };
    }
}
