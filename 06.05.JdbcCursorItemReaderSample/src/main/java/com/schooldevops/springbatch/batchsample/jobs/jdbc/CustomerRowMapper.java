package com.schooldevops.springbatch.batchsample.jobs.jdbc;

import com.schooldevops.springbatch.batchsample.jobs.models.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRowMapper implements RowMapper<Customer> {

    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {

        Customer customer = new Customer();

        customer.setId(rs.getLong("id"));
        customer.setName(rs.getString("name"));
        customer.setAge(rs.getInt("age"));
        customer.setGender(rs.getString("gender"));

        System.out.println("---------------- ROW NUM: " + rowNum);
        return customer;
    }
}