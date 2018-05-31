package com.copyright.rup.dist.foreign.repository.impl.csv;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.report.BatchSummaryReportDto;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Writes batch summary report into a {@link OutputStream} connected to the {@link java.io.InputStream}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 05/31/18
 *
 * @author Uladzislau Shalamitski
 */
public class BatchSummaryReportHandler extends BaseCsvReportHandler<BatchSummaryReportDto> {

    private static final List<String> HEADERS = Arrays.asList("Usage Batch Name", "RRO Account Number", "RRO Name",
        "Payment Date", "Gross Fund Pool in USD", "# non-Eligible Details", "Gross USD non-Eligible Details",
        "# Details NTS", "Gross USD NTS", "# FAS/CLA_FAS Eligible Details", "Gross FAS/CLA_FAS Eligible USD",
        "# Details in-progress Scenarios", "Gross USD in-progress Scenarios", "Royalty Payable USD",
        "# Details Return to CLA", "Gross USD Return to CLA");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public BatchSummaryReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(BatchSummaryReportDto bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(getBeanPropertyAsString(bean.getBatchName()));
        beanProperties.add(getBeanPropertyAsString(bean.getRroAccountNumber()));
        beanProperties.add(bean.getRroName());
        beanProperties.add(getBeanLocalDate(bean.getPaymentDate()));
        beanProperties.add(getBeanBigDecimal(bean.getGrossAmount()));
        beanProperties.add(getBeanPropertyAsString(bean.getNonEligibleDetailsCount()));
        beanProperties.add(getBeanBigDecimal(bean.getNonEligibleDetailsGrossAmount()));
        beanProperties.add(getBeanPropertyAsString(bean.getNtsDetailsCount()));
        beanProperties.add(getBeanBigDecimal(bean.getNtsDetailsGrossAmount()));
        beanProperties.add(getBeanPropertyAsString(bean.getFasAndClaFasEligibleDetailsCount()));
        beanProperties.add(getBeanBigDecimal(bean.getFasAndClaFasEligibleDetailsGrossAmount()));
        beanProperties.add(getBeanPropertyAsString(bean.getScenariosDetailsCount()));
        beanProperties.add(getBeanBigDecimal(bean.getScenariosDetailsGrossAmount()));
        beanProperties.add(getBeanBigDecimal(bean.getScenariosDetailsNetAmount()));
        beanProperties.add(getBeanPropertyAsString(bean.getReturnToClaDetailsCount()));
        beanProperties.add(getBeanBigDecimal(bean.getReturnToClaDetailsGrossAmount()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
