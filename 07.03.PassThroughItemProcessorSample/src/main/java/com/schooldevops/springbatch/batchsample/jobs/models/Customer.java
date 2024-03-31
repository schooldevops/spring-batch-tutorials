package com.schooldevops.springbatch.batchsample.jobs.models;

import lombok.Data;

@Data
public class Customer {

    private Long id;
    private String name;
    private int age;
    private String gender;

}
