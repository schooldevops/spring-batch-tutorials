# 연습문제

- Instruct
  - 1. KOSIS (통계청) 사이트에 접속하여 경기종합지수 자료를 다운받는다. 
    - 링크: [클릭하기](https://kosis.kr/statHtml/statHtml.do?orgId=101&tblId=DT_1C8015&vw_cd=MT_ZTITLE&list_id=J1_1&scrId=&seqNo=&lang_mode=ko&obj_var_id=&itm_id=&conn_path=MT_ZTITLE&path=%252FstatisticsList%252FstatisticsListIndex.do)
    - 파일을 csv로 다운받는다. 
  - 2. Chunk 방식으로 파일을 읽고, 전월대비 비율을 계산하여, 다시 Flatfile로 저장하자. 
  - 3. FlatFileItemReader을 이용하여 해당 파일을 읽는다. 
    - 파일은 CSV파일다.
    - 파일 인코딩은 EUC-KR 이므로 해당 설정을 하여 읽어 들인다. 
  - 4. ItemProcess를 구현하고, 전월대비 비율을 구하고, 값을 변경한다. 
  - 5. FlatFileItemWriter을 이용하여 전월대비 비율값을 포함한 리포트 문서를 csv파일로 작성한다. 

