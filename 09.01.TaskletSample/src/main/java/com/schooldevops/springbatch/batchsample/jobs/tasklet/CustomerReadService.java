package com.schooldevops.springbatch.batchsample.jobs.tasklet;

import com.schooldevops.springbatch.batchsample.jobs.models.Customer;
import com.schooldevops.springbatch.batchsample.mappers.CustomerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CustomerReadService {

    @Autowired
    CustomerMapper customerMapper;

    public List<Customer> selectCustomerByPaging(int page, int size) {
        Map<String, Object> parameter = Map.of("offset", (page - 1) * size, "limit", size);
        return customerMapper.selectCustomers(parameter);
    }
}
