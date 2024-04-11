package com.schooldevops.springbatch.batchsample.jobs.mybatiswriter;

import com.schooldevops.springbatch.batchsample.jobs.models.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomItemWriter implements ItemWriter<Customer> {

    private final CustomService customService;

    public CustomItemWriter(CustomService customService) {
        this.customService = customService;
    }

    @Override
    public void write(Chunk<? extends Customer> chunk) throws Exception {
        for (Customer customer: chunk) {
            log.info("Call Porcess in CustomItemWriter...");
            customService.processToOtherService(customer);
        }
    }
}
