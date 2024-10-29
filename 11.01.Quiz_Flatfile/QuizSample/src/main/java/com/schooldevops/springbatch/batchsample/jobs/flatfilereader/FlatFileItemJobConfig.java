package com.schooldevops.springbatch.batchsample.jobs.flatfilereader;


import com.schooldevops.springbatch.batchsample.jobs.models.Indicator;
import com.schooldevops.springbatch.batchsample.jobs.models.IndicatorCalculated;
import com.schooldevops.springbatch.batchsample.jobs.models.IndicatorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Configuration
public class FlatFileItemJobConfig {

    /**
     * CHUNK 크기를 지정한다.
     */
    public static final int CHUNK_SIZE = 100;
    /**
     * 입력할 파일의 인코딩
     */
    public static final String READ_ENCODING = "EUC-KR";
    /**
     * 출력할 파일의 인코딩
     */
    public static final String WRITE_ENCODING = "UTF-8";
    /**
     * 잡 이름을 지정하였다.
     * 이 값은 배치가 실행될때 실행할 잡을 나타낸다.
     */
    public static final String FLAT_FILE_WRITER_CHUNK_JOB = "FLAT_FILE_WRITER_CHUNK_JOB";

    /**
     * ItemProcessor은 외부에 만들었기 때문에 빈을 Autowired 한다.
     */
    @Autowired
    @Qualifier("rateCalcItemProcessor")
    private ItemProcessor<Indicator, IndicatorResult> rateCalcItemProcessor;

    /**
     * 헤더 값을 따로 읽어두기 위한 변수
     */
    private String headerStr = "";

    @Bean
    public FlatFileItemReader<Indicator> flatFileItemReader() {

        /**
         * FlatFileItemReaderBuilder을 이용하여 빌더 패턴을 사용하였다.
         */
        return new FlatFileItemReaderBuilder<Indicator>()
                .name("FlatFileItemReader")
                // 읽어들일 파일 위치를 지정하였다. ClassPathResource는 스프링 어플리케이션 내에 파일이다. (실전에서는 이걸 쓰지 않음)
                .resource(new ClassPathResource("./sample_file_2020100.csv"))
                .encoding(READ_ENCODING)
                .linesToSkip(1)    // 첫번째 라인은 스킵한다. (헤더이므로)
                // 스킵한 라인을 어떻게 처리할지 방법을 지정한다. (즉, 여기서는 1개 라인을 스킵하고, 이 값은 headerStr에 할당하였다.)
                .skippedLinesCallback(it -> it.lines().forEach(header -> headerStr = header))
                // csv파일이므로 콤마를 딜리미터로 해서 분할하였다.
                .delimited().delimiter(",")
                // 객체에 할당할 필드 이름
                .names("name", "month_3", "month_4","month_5","month_6","month_7","month_8")
                // 읽어들인 값을 저장할 객체 이름
                .targetType(Indicator.class)
                .build();
    }

    @Bean
    public FlatFileItemWriter<IndicatorResult> flatFileItemWriter() {

        /**
         * 파일 쓰기도 빌더로 구현하였다.
         */
        return new FlatFileItemWriterBuilder<IndicatorResult>()
                .name("flatFileItemWriter")
                // 저장될 파일 위치와 이름을 지정하였다.
                .resource(new FileSystemResource("./output/sample_output.csv"))
                // 저장할 인코딩을 사용했다. 우리는 UTF-8 인코딩으로 저장할 것이다.
                .encoding(WRITE_ENCODING)
                // 라인 딜리미터를 지정했다.
                .delimited().delimiter(",")
                // 각 출력할 필드 이름을 지정한다.
                .names("name", "month_3", "month_4","month_5","month_6","month_7","month_8")
                // 파일을 작성하면 아예 새로 만든다. true가 되면 이어 붙이기가 된다.
                .append(false)
                // 헤더를 지정한다.
                .headerCallback(it -> it.write(headerStr))
                .build();
    }


    @Bean
    public Step flatFileStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        log.info("------------------ Init flatFileStep -----------------");

        return new StepBuilder("flatFileStep", jobRepository)
                .<Indicator, IndicatorResult>chunk(CHUNK_SIZE, transactionManager)
                .reader(flatFileItemReader())
                .processor(rateCalcItemProcessor)
                .writer(flatFileItemWriter())
                .build();
    }

    @Bean
    public Job flatFileJob(Step flatFileStep, JobRepository jobRepository) {
        log.info("------------------ Init flatFileJob -----------------");
        return new JobBuilder(FLAT_FILE_WRITER_CHUNK_JOB, jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(flatFileStep)
                .build();
    }
}
