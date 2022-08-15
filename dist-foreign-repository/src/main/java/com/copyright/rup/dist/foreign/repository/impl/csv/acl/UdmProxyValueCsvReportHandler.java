package com.copyright.rup.dist.foreign.repository.impl.csv.acl;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.UdmProxyValueDto;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Writes UDM proxy values into an {@link OutputStream}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/07/2021
 *
 * @author Uladzislau Shalamitski
 */
public class UdmProxyValueCsvReportHandler extends BaseCsvReportHandler<UdmProxyValueDto> {

    private static final List<String> HEADERS =
        Arrays.asList("Period", "Pub Type Code", "Content Unit Price", "Content Unit Price Count");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public UdmProxyValueCsvReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(UdmProxyValueDto bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(getBeanPropertyAsString(bean.getPeriod()));
        beanProperties.add(bean.getPubTypeName());
        beanProperties.add(getBeanBigDecimal(bean.getContentUnitPrice()));
        beanProperties.add(getBeanPropertyAsString(bean.getContentUnitPriceCount()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
