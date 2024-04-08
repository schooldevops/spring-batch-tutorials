package com.schooldevops.springbatch.batchsample.jobs.flatfilereader;

import com.schooldevops.springbatch.batchsample.jobs.models.Customer;
import org.springframework.batch.item.file.transform.LineAggregator;

public class CustomerLineAggregator implements LineAggregator<Customer> {
    @Override
    public String aggregate(Customer item) {
        return item.getName() + "," + item.getAge();
    }
}
