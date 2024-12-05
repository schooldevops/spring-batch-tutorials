package com.schooldevops.springbatch.batchsample.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ChunkListenerConfig {

    @Bean
    public ChunkListener chunkListener() {
        return new ChunkListener() {
            @Override
            public void beforeChunk(ChunkContext context) {
                log.info(" >>>>>> Before Chunk: Chunk {} is starting...", context.getStepContext().getStepName());
            }

            @Override
            public void afterChunk(ChunkContext context) {
                log.info(" >>>>>> After Chunk: Chunk {} is finished...", context.getStepContext().getStepName());
            }

            @Override
            public void afterChunkError(ChunkContext context) {
                log.info(" >>>>>> After Chunk Error: Chunk {} is finished with error...", context.getStepContext().getStepName());
            }
        };
    }
}
