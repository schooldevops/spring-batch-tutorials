package com.schooldevops.springbatch.batchsample.jobs.tasklet;

import com.schooldevops.springbatch.batchsample.jobs.models.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class CustomerTasklet implements Tasklet {

    @Autowired
    CustomerReadService readerService;

    @Override
    public RepeatStatus execute(
            StepContribution contribution,
            ChunkContext chunkContext) throws Exception {
        log.info("------------ Tasklet Start ----------------");
        int page = 1;
        int size = 2;

        while(true) {
            List<Customer> customers = readerService.selectCustomerByPaging(page, size);

            if (customers == null || customers.isEmpty()) break;

            for (Customer customer: customers) {
                log.info("Customer: " + customer);
            }

            page += 1;
        }

        log.info("------------ Tasklet End ----------------");
        return RepeatStatus.FINISHED;
//        return RepeatStatus.CONTINUABLE;

    }
}
