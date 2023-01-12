package com.copyright.rup.dist.foreign.repository.impl.csv.fas;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.report.FasBatchSummaryReportDto;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes FAS/FAS2 Batch Summary Report into a {@link OutputStream} connected to the {@link java.io.InputStream}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 05/31/18
 *
 * @author Uladzislau Shalamitski
 */
public class FasBatchSummaryReportHandler extends BaseCsvReportHandler<FasBatchSummaryReportDto> {

    private static final List<String> HEADERS = List.of("Usage Batch Name", "RRO Account Number", "RRO Name",
        "Payment Date", "Load Date", "Gross Fund Pool in USD", "# non-Eligible Details",
        "Gross USD non-Eligible Details", "# Details NTS", "Gross USD NTS", "# FAS/FAS2 Eligible Details",
        "Gross FAS/FAS2 Eligible USD", "# Details in-progress Scenarios", "Gross USD in-progress Scenarios",
        "Royalty Payable USD", "# Details Return to CLA", "Gross USD Return to CLA");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public FasBatchSummaryReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(FasBatchSummaryReportDto bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(getBeanPropertyAsString(bean.getBatchName()));
        beanProperties.add(getBeanPropertyAsString(bean.getRroAccountNumber()));
        beanProperties.add(bean.getRroName());
        beanProperties.add(getBeanLocalDate(bean.getPaymentDate()));
        beanProperties.add(DateFormatUtils.format(bean.getCreateDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getGrossAmount()));
        beanProperties.add(getBeanPropertyAsString(bean.getNonEligibleDetailsCount()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getNonEligibleDetailsGrossAmount()));
        beanProperties.add(getBeanPropertyAsString(bean.getNtsDetailsCount()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getNtsDetailsGrossAmount()));
        beanProperties.add(getBeanPropertyAsString(bean.getFasAndClaFasEligibleDetailsCount()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getFasAndClaFasEligibleDetailsGrossAmount()));
        beanProperties.add(getBeanPropertyAsString(bean.getScenariosDetailsCount()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getScenariosDetailsGrossAmount()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getScenariosDetailsNetAmount()));
        beanProperties.add(getBeanPropertyAsString(bean.getReturnToClaDetailsCount()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getReturnToClaDetailsGrossAmount()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
