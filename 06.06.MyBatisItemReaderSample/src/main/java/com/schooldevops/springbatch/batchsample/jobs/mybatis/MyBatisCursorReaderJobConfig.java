package com.schooldevops.springbatch.batchsample.jobs.mybatis;

import com.schooldevops.springbatch.batchsample.jobs.models.Customer;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.mybatis.spring.batch.builder.MyBatisCursorItemReaderBuilder;
import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class MyBatisCursorReaderJobConfig {

    /**
     * CHUNK 크기를 지정한다.
     */
    public static final int CHUNK_SIZE = 2;
    public static final String ENCODING = "UTF-8";
    public static final String MYBATIS_CURSOR_JOB = "MYBATIS_CURSOR_JOB";

    @Autowired
    DataSource dataSource;

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Autowired
    PlatformTransactionManager platformTransactionManager;

    @Bean
    public MyBatisCursorItemReader<Customer> myBatisCursorItemReader() {

        return new MyBatisCursorItemReaderBuilder<Customer>()
                .sqlSessionFactory(sqlSessionFactory)
                .queryId("com.schooldevops.springbatch.batchsample.jobs.selectCursorCustomers")
                .build();
    }

    @Bean(name = "customerCursorV2FlatFileItemWriter")
    public FlatFileItemWriter<Customer> customerCursorV2FlatFileItemWriter() {
        return new FlatFileItemWriterBuilder<Customer>()
                .name("customerCursorV2FlatFileItemWriter")
                .resource(new FileSystemResource("./output/customer_new_v5.csv"))
                .encoding(ENCODING)
                .delimited().delimiter(",")
                .names("Name", "Age", "Gender")
                .build();
    }


    @Bean
    public Step customerMybatisCursorStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        log.info("------------------ Init customerJdbcCursorStep -----------------");

        return new StepBuilder("customerMybatisCursorStep", jobRepository)
                .<Customer, Customer>chunk(CHUNK_SIZE, transactionManager)
                .reader(myBatisCursorItemReader())
                .processor(new CustomerItemProcessor())
                .writer(customerCursorV2FlatFileItemWriter())
                .build();
    }

    @Bean
    public Job customerMybatisCursorPagingJob(Step customerJdbcCursorStep, JobRepository jobRepository, TransactionManagerCustomizers platformTransactionManagerCustomizers) {
        log.info("------------------ Init customerJdbcCursorPagingJob -----------------");
        return new JobBuilder(MYBATIS_CURSOR_JOB, jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(customerMybatisCursorStep(jobRepository, platformTransactionManager))
                .build();
    }
}
