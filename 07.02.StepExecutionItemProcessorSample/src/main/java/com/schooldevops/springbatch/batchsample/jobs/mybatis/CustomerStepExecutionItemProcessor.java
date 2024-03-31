package com.schooldevops.springbatch.batchsample.jobs.mybatis;

import com.schooldevops.springbatch.batchsample.jobs.models.Customer;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class CustomerStepExecutionItemProcessor implements ItemProcessor<Customer, Customer> {

    private JobExecution jobExecution;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        jobExecution = stepExecution.getJobExecution();
    }

    @Override
    public Customer process(Customer item) throws Exception {

        if (jobExecution != null) {

            String jobName = jobExecution.getJobInstance().getJobName();
            item.setName(item.getName().toLowerCase() + "_(" + jobName + ", " + jobExecution.getJobId()+ ")");
        }

        return item;
    }
}
