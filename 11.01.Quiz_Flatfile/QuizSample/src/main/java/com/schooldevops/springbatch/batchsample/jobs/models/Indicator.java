package com.schooldevops.springbatch.batchsample.jobs.models;

import lombok.Data;

/**
 * 지수 데이터를 읽어들여서 저장할 객체
 */
@Data
public class Indicator {

    /**
     * 이름
     */
    private String name;
    /**
     * 3월지수
     */
    private float month_3;
    /**
     * 4월지수
     */
    private float month_4;
    /**
     * 5월지수
     */
    private float month_5;
    /**
     * 6월지수
     */
    private float month_6;
    /**
     * 7월지수
     */
    private float month_7;
    /**
     * 8월지수
     */
    private float month_8;

}
