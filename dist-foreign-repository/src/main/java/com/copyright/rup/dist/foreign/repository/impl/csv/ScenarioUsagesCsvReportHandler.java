package com.copyright.rup.dist.foreign.repository.impl.csv;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.common.util.UsageBatchUtils;

import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Writes scenario usages into a {@link PipedOutputStream} connected to the {@link java.io.PipedInputStream}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 10/11/2017
 *
 * @author Uladzislau Shalamitski
 */
public class ScenarioUsagesCsvReportHandler extends BaseCsvReportHandler<UsageDto> {

    private static final List<String> HEADERS = Arrays.asList("Detail ID", "Usage Batch Name", "Product Family",
        "Fiscal Year", "RRO Account #", "RRO Name", "Payment Date", "Title", "Article", "Standard Number",
        "Wr Wrk Inst", "RH Account #", "RH Name", "Payee Account #", "Payee Name", "Publisher", "Pub Date",
        "Number of Copies", "Reported value", "Gross Amt in USD", "Service Fee Amount", "Net Amt in USD",
        "Service Fee %", "Market", "Market Period From", "Market Period To", "Author");

    /**
     * Constructor.
     *
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    public ScenarioUsagesCsvReportHandler(PipedOutputStream pipedOutputStream) {
        super(pipedOutputStream);
    }

    @Override
    protected List<String> getBeanProperties(UsageDto bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(bean.getId());
        beanProperties.add(bean.getBatchName());
        beanProperties.add(bean.getProductFamily());
        beanProperties.add(UsageBatchUtils.getFiscalYear(bean.getFiscalYear()));
        beanProperties.add(getBeanPropertyAsString(bean.getRroAccountNumber()));
        beanProperties.add(bean.getRroName());
        beanProperties.add(getBeanLocalDate(bean.getPaymentDate()));
        beanProperties.add(bean.getWorkTitle());
        beanProperties.add(bean.getArticle());
        beanProperties.add(bean.getStandardNumber());
        beanProperties.add(getBeanPropertyAsString(bean.getWrWrkInst()));
        beanProperties.add(getBeanPropertyAsString(bean.getRhAccountNumber()));
        beanProperties.add(bean.getRhName());
        beanProperties.add(getBeanPropertyAsString(bean.getPayeeAccountNumber()));
        beanProperties.add(bean.getPayeeName());
        beanProperties.add(bean.getPublisher());
        beanProperties.add(getBeanLocalDate(bean.getPublicationDate()));
        beanProperties.add(getBeanPropertyAsString(bean.getNumberOfCopies()));
        beanProperties.add(getBeanPropertyAsString(bean.getReportedValue()));
        beanProperties.add(getBeanPropertyAsString(bean.getGrossAmount()));
        beanProperties.add(getBeanBigDecimal(bean.getServiceFeeAmount()));
        beanProperties.add(getBeanBigDecimal(bean.getNetAmount()));
        beanProperties.add(getBeanServiceFeePercent(bean.getServiceFee()));
        beanProperties.add(bean.getMarket());
        beanProperties.add(getBeanPropertyAsString(bean.getMarketPeriodFrom()));
        beanProperties.add(getBeanPropertyAsString(bean.getMarketPeriodTo()));
        beanProperties.add(getBeanPropertyAsString(bean.getAuthor()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
