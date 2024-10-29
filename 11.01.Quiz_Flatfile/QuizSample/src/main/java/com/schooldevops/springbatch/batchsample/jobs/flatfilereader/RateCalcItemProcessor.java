package com.schooldevops.springbatch.batchsample.jobs.flatfilereader;

import com.schooldevops.springbatch.batchsample.jobs.models.Indicator;
import com.schooldevops.springbatch.batchsample.jobs.models.IndicatorCalculated;
import com.schooldevops.springbatch.batchsample.jobs.models.IndicatorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Item Processor
 * 지수 정보를 읽고, 전월대비 증가율을 계산한다.
 *
 * 중간 결과는 IndicatorCalculated 객체에 각각 넣었다.
 * 최종 결과는 문자열로 변경하여 IndicatorResult로 반환한다.
 *
 * 즉, 이 ItemProcessor은
 * Indicator를 읽어들이고, IndicatorResult를 반환하는 ItemProcessor이다.
 */
@Component
@Qualifier("rateCalcItemProcessor")
@Slf4j
public class RateCalcItemProcessor implements ItemProcessor<Indicator, IndicatorResult> {

    /**
     * 지수의 증감을 계산하는 프로세서 메소드
     * @param item to be processed, never {@code null}.
     * @return IndicatorResult 객체를 반환한다.
     * @throws Exception 변환하다가 오류가 나면 예뢰가 던져진다.
     */
    @Override
    public IndicatorResult process(Indicator item) throws Exception {

        Indicator rateIndicator = new Indicator();
        rateIndicator.setName(item.getName());
        rateIndicator.setMonth_3((item.getMonth_3() - item.getMonth_3()) / 100f);
        rateIndicator.setMonth_4((item.getMonth_4() - item.getMonth_3()) / 100f);
        rateIndicator.setMonth_5((item.getMonth_5() - item.getMonth_4()) / 100f);
        rateIndicator.setMonth_6((item.getMonth_6() - item.getMonth_5()) / 100f);
        rateIndicator.setMonth_7((item.getMonth_7() - item.getMonth_6()) / 100f);
        rateIndicator.setMonth_8((item.getMonth_8() - item.getMonth_7()) / 100f);
        IndicatorCalculated indicatorCalculated = new IndicatorCalculated();
        indicatorCalculated.setIndicator(item);
        indicatorCalculated.setRatio(rateIndicator);

        // 중간에 로그를 찍어보자.
        log.info("-------- : " + indicatorCalculated);

        // 빌더 패턴으로 각 각 필드를 계산하였다.
        return IndicatorResult.builder()
                .name(item.getName())
                .month_3(makeReportValue(indicatorCalculated.getIndicator().getMonth_3(), indicatorCalculated.getRatio().getMonth_3()))
                .month_4(makeReportValue(indicatorCalculated.getIndicator().getMonth_4(), indicatorCalculated.getRatio().getMonth_4()))
                .month_5(makeReportValue(indicatorCalculated.getIndicator().getMonth_5(), indicatorCalculated.getRatio().getMonth_5()))
                .month_6(makeReportValue(indicatorCalculated.getIndicator().getMonth_6(), indicatorCalculated.getRatio().getMonth_6()))
                .month_7(makeReportValue(indicatorCalculated.getIndicator().getMonth_7(), indicatorCalculated.getRatio().getMonth_7()))
                .month_8(makeReportValue(indicatorCalculated.getIndicator().getMonth_8(), indicatorCalculated.getRatio().getMonth_8()))
                .build();
    }

    /**
     * 지수 값을 다음과 같이 표현한다.
     * 값(증감율)
     * @param value 지수값
     * @param rate 증감율
     * @return 포맷된 결과를 반환한다. (소숫점 2자리로 자름)
     */
    public String makeReportValue(float value, float rate) {
        return "%s(%.2f)".formatted(value, rate);
    }
}


