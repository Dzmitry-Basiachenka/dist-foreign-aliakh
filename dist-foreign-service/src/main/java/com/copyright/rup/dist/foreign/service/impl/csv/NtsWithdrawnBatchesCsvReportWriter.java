package com.copyright.rup.dist.foreign.service.impl.csv;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportWriter;
import com.copyright.rup.dist.foreign.domain.UsageBatch;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link BaseCsvReportWriter} to write FAS batches with NTS withdrawn usages report.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 04/01/2019
 *
 * @author Aliaksandr Liakh
 */
public class NtsWithdrawnBatchesCsvReportWriter extends BaseCsvReportWriter<UsageBatch> {

    private static final List<String> HEADERS = List.of("Usage Batch Name", "Gross NTS Withdrawn Amount");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public NtsWithdrawnBatchesCsvReportWriter(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(UsageBatch usageBatch) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(usageBatch.getName());
        beanProperties.add(roundAndGetBeanBigDecimal(usageBatch.getGrossAmount()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
