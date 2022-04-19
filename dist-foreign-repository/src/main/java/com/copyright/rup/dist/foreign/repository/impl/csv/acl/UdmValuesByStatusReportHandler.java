package com.copyright.rup.dist.foreign.repository.impl.csv.acl;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.report.UdmValuesByStatusReportDto;

import com.google.common.collect.ImmutableList;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link BaseCsvReportHandler} to write UDM Values by Status Report.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 04/20/2022
 *
 * @author Mikita Maistrenka
 */
public class UdmValuesByStatusReportHandler extends BaseCsvReportHandler<UdmValuesByStatusReportDto> {

    private static final List<String> HEADERS = ImmutableList.of("Status", "Assignee", "Count");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public UdmValuesByStatusReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(UdmValuesByStatusReportDto bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(bean.getStatus());
        beanProperties.add(bean.getAssignee());
        beanProperties.add(getBeanPropertyAsString(bean.getCount()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
