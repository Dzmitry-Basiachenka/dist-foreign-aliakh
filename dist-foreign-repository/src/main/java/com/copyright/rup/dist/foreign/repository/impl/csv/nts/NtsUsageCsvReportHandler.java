package com.copyright.rup.dist.foreign.repository.impl.csv.nts;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.common.util.UsageBatchUtils;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes NTS usages into an {@link OutputStream}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 12/11/2019
 *
 * @author Uladzislau Shalamitski
 */
public class NtsUsageCsvReportHandler extends BaseCsvReportHandler<UsageDto> {

    private static final List<String> HEADERS = List.of("Detail ID", "Detail Status", "Product Family",
        "Usage Batch Name", "RRO Account #", "RRO Name", "RH Account #", "RH Name", "Wr Wrk Inst", "System Title",
        "Standard Number", "Standard Number Type", "Fiscal Year", "Payment Date", "Title", "Article", "Publisher",
        "Pub Date", "Number of Copies", "Reported Value", "Gross Amt in USD", "Market", "Market Period From",
        "Market Period To", "Author", "Comment");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public NtsUsageCsvReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(UsageDto bean) {
        List<String> beanProperties = new ArrayList<>(HEADERS.size());
        beanProperties.add(bean.getId());
        beanProperties.add(bean.getStatus().name());
        beanProperties.add(bean.getProductFamily());
        beanProperties.add(bean.getBatchName());
        beanProperties.add(getBeanPropertyAsString(bean.getRroAccountNumber()));
        beanProperties.add(bean.getRroName());
        beanProperties.add(getBeanPropertyAsString(bean.getRhAccountNumber()));
        beanProperties.add(bean.getRhName());
        beanProperties.add(getBeanPropertyAsString(bean.getWrWrkInst()));
        beanProperties.add(bean.getSystemTitle());
        beanProperties.add(formatStringStartedWithZero(bean.getStandardNumber()));
        beanProperties.add(bean.getStandardNumberType());
        beanProperties.add(UsageBatchUtils.getFiscalYear(bean.getFiscalYear()));
        beanProperties.add(getBeanLocalDate(bean.getPaymentDate()));
        beanProperties.add(bean.getWorkTitle());
        beanProperties.add(bean.getArticle());
        beanProperties.add(bean.getPublisher());
        beanProperties.add(getBeanLocalDate(bean.getPublicationDate()));
        beanProperties.add(getBeanPropertyAsString(bean.getNumberOfCopies()));
        beanProperties.add(getBeanPropertyAsString(bean.getReportedValue()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getGrossAmount()));
        beanProperties.add(bean.getMarket());
        beanProperties.add(getBeanPropertyAsString(bean.getMarketPeriodFrom()));
        beanProperties.add(getBeanPropertyAsString(bean.getMarketPeriodTo()));
        beanProperties.add(bean.getAuthor());
        beanProperties.add(bean.getComment());
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
