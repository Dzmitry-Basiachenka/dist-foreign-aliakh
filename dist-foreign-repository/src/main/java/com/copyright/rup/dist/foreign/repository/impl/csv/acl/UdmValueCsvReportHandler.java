package com.copyright.rup.dist.foreign.repository.impl.csv.acl;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.UdmValueDto;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes UDM values into an {@link OutputStream}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 07/26/2023
 *
 * @author Anton Azarenka
 */
public class UdmValueCsvReportHandler extends BaseCsvReportHandler<UdmValueDto> {

    private static final List<String> HEADERS =
        List.of("Value ID", "Value Period", "Status", "Assignee", "RH Account #", "RH Name", "Wr Wrk Inst",
            "System Title", "System Standard Number", "Last Value Period", "Last Pub Type", "Pub Type",
            "Last Price in USD", "Last Price Flag", "Last Price Source", "Price Source", "Last Price Comment", "Price",
            "Currency", "Price Type", "Price Access Type", "Price Year", "Price Comment", "Price in USD", "Price Flag",
            "Currency Exchange Rate", "Currency Exchange Rate Date", "Last Content", "Last Content Flag",
            "Last Content Source", "Content Source", "Last Content Comment", "Content", "Content Comment",
            "Content Flag", "Content Unit Price", "CUP Flag", "Last Comment", "Comment", "Updated By", "Updated Date");

    /**
     * Constructor.
     *
     * @param outputStream {@link OutputStream} instance
     */
    protected UdmValueCsvReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(UdmValueDto bean) {
        List<String> beanProperties = new ArrayList<>(HEADERS.size());
        beanProperties.add(bean.getId());
        beanProperties.add(getBeanPropertyAsString(bean.getValuePeriod()));
        beanProperties.add(bean.getStatus().name());
        beanProperties.add(bean.getAssignee());
        beanProperties.add(getBeanPropertyAsString(bean.getRhAccountNumber()));
        beanProperties.add(bean.getRhName());
        beanProperties.add(getBeanPropertyAsString(bean.getWrWrkInst()));
        beanProperties.add(bean.getSystemTitle());
        beanProperties.add(bean.getSystemStandardNumber());
        beanProperties.add(getBeanPropertyAsString(bean.getLastValuePeriod()));
        beanProperties.add(bean.getLastPubType());
        beanProperties.add(getBeanPropertyAsString(bean.getPublicationType()));
        beanProperties.add(getBeanBigDecimal(bean.getLastPriceInUsd()));
        beanProperties.add(getFlagAsString(bean.isLastPriceFlag()));
        beanProperties.add(getBeanPropertyAsString(bean.getLastPriceSource()));
        beanProperties.add(getBeanPropertyAsString(bean.getPriceSource()));
        beanProperties.add(bean.getLastPriceComment());
        beanProperties.add(getBeanBigDecimal(bean.getPrice()));
        beanProperties.add(bean.getCurrency());
        beanProperties.add(bean.getPriceType());
        beanProperties.add(bean.getPriceAccessType());
        beanProperties.add(getBeanPropertyAsString(bean.getPriceYear()));
        beanProperties.add(bean.getPriceComment());
        beanProperties.add(getBeanBigDecimal(bean.getPriceInUsd()));
        beanProperties.add(getFlagAsString(bean.isPriceFlag()));
        beanProperties.add(getBeanBigDecimal(bean.getCurrencyExchangeRate()));
        beanProperties.add(getBeanLocalDate(bean.getCurrencyExchangeRateDate()));
        beanProperties.add(getBeanBigDecimal(bean.getLastContent()));
        beanProperties.add(getFlagAsString(bean.isLastContentFlag()));
        beanProperties.add(bean.getLastContentSource());
        beanProperties.add(bean.getContentSource());
        beanProperties.add(bean.getLastContentComment());
        beanProperties.add(getBeanBigDecimal(bean.getContent()));
        beanProperties.add(bean.getContentComment());
        beanProperties.add(getFlagAsString(bean.isContentFlag()));
        beanProperties.add(getBeanBigDecimal(bean.getContentUnitPrice()));
        beanProperties.add(getFlagAsString(bean.isContentUnitPriceFlag()));
        beanProperties.add(bean.getLastComment());
        beanProperties.add(bean.getComment());
        beanProperties.add(bean.getUpdateUser());
        beanProperties.add(convertDateToString(bean.getUpdateDate()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }

    private String getFlagAsString(boolean flag) {
        return flag ? "Y" : "N";
    }
}
