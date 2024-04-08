package com.schooldevops.springbatch.batchsample.jobs.flatfilereader;

import org.springframework.batch.item.file.FlatFileHeaderCallback;

import java.io.IOException;
import java.io.Writer;

public class CustomerHeader implements FlatFileHeaderCallback {
    @Override
    public void writeHeader(Writer writer) throws IOException {
        writer.write("ID,AGE");
    }
}
