package com.schooldevops.springbatch.batchsample.jobs.models;

import lombok.Data;

/**
 * 계산된 중간 정보를 저장하는 객체
 * 사실 없어도 되지만 나중에 다양한 변경을 한다면 중간 데이터를 남겨두는 것도 좋음
 */
@Data
public class IndicatorCalculated {

    /**
     * 읽어들인 지수 정보
     */
    private Indicator indicator;
    /**
     * 전월대비 ratio 정보를 저장할 데이터
     */
    private Indicator ratio;

}
