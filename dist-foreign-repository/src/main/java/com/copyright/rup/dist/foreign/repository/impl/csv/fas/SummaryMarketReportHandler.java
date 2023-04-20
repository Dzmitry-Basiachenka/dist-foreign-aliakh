package com.copyright.rup.dist.foreign.repository.impl.csv.fas;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.report.SummaryMarketReportDto;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes calculated market amounts into a {@link OutputStream} connected to the {@link java.io.InputStream}.
 * Is used to generate Summary of Market Report.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 8/27/18
 *
 * @author Ihar Suvorau
 */
public class SummaryMarketReportHandler extends BaseCsvReportHandler<SummaryMarketReportDto> {

    private static final List<String> HEADERS = List.of("RRO Account Number", "RRO Name", "Usage Batch Name",
        "Payment Date", "Market", "Market Total Gross USD", "Total Payment Gross USD");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public SummaryMarketReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(SummaryMarketReportDto bean) {
        List<String> beanProperties = new ArrayList<>(HEADERS.size());
        beanProperties.add(getBeanPropertyAsString(bean.getRroAccountNumber()));
        beanProperties.add(bean.getRroName());
        beanProperties.add(bean.getBatchName());
        beanProperties.add(getBeanLocalDate(bean.getPaymentDate()));
        beanProperties.add(bean.getMarket());
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getMarketTotalAmount()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getTotalPaymentAmount()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
