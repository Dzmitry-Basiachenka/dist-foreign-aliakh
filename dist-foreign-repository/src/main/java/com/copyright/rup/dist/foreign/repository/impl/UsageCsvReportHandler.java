package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.foreign.domain.UsageDto;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Writes usages into an {@link OutputStream}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/06/2017
 *
 * @author Mikita Hladkikh
 */
class UsageCsvReportHandler extends BaseCsvReportHandler<UsageDto> {

    private static final List<String> HEADERS = Arrays.asList("Detail ID", "Detail Status", "Product Family",
        "Usage Batch Name", "Fiscal Year", "RRO Account #", "RRO Name", "Payment Date", "Title", "Article",
        "Standard Number", "Wr Wrk Inst", "RH Account #", "RH Name", "Publisher", "Pub Date", "Number of Copies",
        "Reported value", "Amt in USD", "Gross Amt in USD", "Market", "Market Period From", "Market Period To",
        "Author");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    UsageCsvReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    List<String> getBeanProperties(UsageDto bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(bean.getId());
        beanProperties.add(bean.getStatus().name());
        beanProperties.add(bean.getProductFamily());
        beanProperties.add(bean.getBatchName());
        beanProperties.add(getBeanFiscalYear(bean.getFiscalYear()));
        beanProperties.add(getBeanPropertyAsString(bean.getRroAccountNumber()));
        beanProperties.add(bean.getRroName());
        beanProperties.add(getBeanLocalDate(bean.getPaymentDate()));
        beanProperties.add(bean.getWorkTitle());
        beanProperties.add(bean.getArticle());
        beanProperties.add(bean.getStandardNumber());
        beanProperties.add(getBeanPropertyAsString(bean.getWrWrkInst()));
        beanProperties.add(getBeanPropertyAsString(bean.getRhAccountNumber()));
        beanProperties.add(bean.getRhName());
        beanProperties.add(bean.getPublisher());
        beanProperties.add(getBeanLocalDate(bean.getPublicationDate()));
        beanProperties.add(getBeanPropertyAsString(bean.getNumberOfCopies()));
        beanProperties.add(getBeanPropertyAsString(bean.getReportedValue()));
        beanProperties.add(getBeanPropertyAsString(bean.getGrossAmount()));
        beanProperties.add(getBeanPropertyAsString(bean.getBatchGrossAmount()));
        beanProperties.add(bean.getMarket());
        beanProperties.add(getBeanPropertyAsString(bean.getMarketPeriodFrom()));
        beanProperties.add(getBeanPropertyAsString(bean.getMarketPeriodTo()));
        beanProperties.add(bean.getAuthor());
        return beanProperties;
    }

    @Override
    List<String> getBeanHeaders() {
        return HEADERS;
    }
}
