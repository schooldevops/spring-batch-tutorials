package com.schooldevops.springbatch.batchsample.mappers;

import com.schooldevops.springbatch.batchsample.jobs.models.Customer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CustomerMapper {

    List<Customer> selectCustomers(Map<String, Object> params);
}
