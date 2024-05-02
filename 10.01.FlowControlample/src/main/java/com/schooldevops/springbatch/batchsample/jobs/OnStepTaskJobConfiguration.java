package com.schooldevops.springbatch.batchsample.jobs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Random;

@Slf4j
@Configuration
public class OnStepTaskJobConfiguration {

    public static final String ON_STEP_TASK = "ON_STEP_TASK";

    @Autowired
    PlatformTransactionManager transactionManager;

    @Bean(name = "stepOn01")
    public Step stepOn01(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        log.info("------------------ Init myStep -----------------");

        return new StepBuilder("stepOn01", jobRepository)
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        log.info("Execute Step 01 Tasklet ...");

                        Random random = new Random();
                        int randomValue = random.nextInt(1000);

                        if (randomValue % 2 == 0) {
                            return RepeatStatus.FINISHED;
                        } else {
                            throw new RuntimeException("Error This value is Odd: " + randomValue);
                        }
                    }
                }, transactionManager)
                .build();
    }

    @Bean(name = "stepOn02")
    public Step stepOn02(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        log.info("------------------ Init myStep -----------------");

        return new StepBuilder("stepOn02", jobRepository)
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        log.info("Execute Step 02 Tasklet ...");
                        return RepeatStatus.FINISHED;
                    }
                }, transactionManager)
                .build();
    }

    @Bean(name = "stepOn03")
    public Step stepOn03(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        log.info("------------------ Init myStep -----------------");

        return new StepBuilder("stepOn03", jobRepository)
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        log.info("Execute Step 03 Tasklet ...");
                        return RepeatStatus.FINISHED;
                    }
                }, transactionManager)
                .build();
    }

    @Bean
    public Job onStepJob(Step stepOn01, Step stepOn02, Step stepOn03, JobRepository jobRepository) {
        log.info("------------------ Init myJob -----------------");
        return new JobBuilder(ON_STEP_TASK, jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(stepOn01)
                .on("FAILED").to(stepOn03)
                .from(stepOn01).on("COMPLETED").to(stepOn02)
                .end()
                .build();
    }
}
