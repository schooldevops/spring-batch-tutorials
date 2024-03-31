package com.schooldevops.springbatch.batchsample.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

//@EnableContextResourceLoader
@Configuration
public class DBConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory() throws IOException {
        Resource mybatisConfig = new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml");
        SqlSessionFactoryBean sqlSession = new SqlSessionFactoryBean();
        sqlSession.setDataSource(dataSource);
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources(
                "classpath:mapper/*.xml"
        );

//        Resource[] resources = new PathMatchingResourcePatternResolver().getResources(
//                "classpath:com/schooldevops/springbatch/batchsample/**/mapper/*.xml"
//        );

        sqlSession.setMapperLocations(resources);
        sqlSession.setConfigLocation(mybatisConfig);

        return sqlSession;
    }
}
