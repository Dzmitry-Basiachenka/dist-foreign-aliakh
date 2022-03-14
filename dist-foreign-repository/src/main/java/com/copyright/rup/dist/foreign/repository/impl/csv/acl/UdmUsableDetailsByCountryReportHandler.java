package com.copyright.rup.dist.foreign.repository.impl.csv.acl;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.report.UdmUsableDetailsByCountryReportDto;

import com.google.common.collect.ImmutableList;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link BaseCsvReportHandler} to write UDM Usable Details by Country Report.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 03/11/2022
 *
 * @author Mikita Maistrenka
 */
public class UdmUsableDetailsByCountryReportHandler extends BaseCsvReportHandler<UdmUsableDetailsByCountryReportDto> {

    private static final List<String> HEADERS = ImmutableList.of("Period", "Channel", "Usage Origin", "Country",
        "Det LC ID", "Det LC Name", "Total Count");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public UdmUsableDetailsByCountryReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(UdmUsableDetailsByCountryReportDto bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(getBeanPropertyAsString(bean.getPeriod()));
        beanProperties.add(bean.getChannel());
        beanProperties.add(bean.getUsageOrigin());
        beanProperties.add(bean.getCountry());
        beanProperties.add(getBeanPropertyAsString(bean.getDetailLicenseeClassId()));
        beanProperties.add(bean.getDetailLicenseeClassName());
        beanProperties.add(getBeanPropertyAsString(bean.getTotalCount()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
