package com.copyright.rup.dist.foreign.repository.impl.csv.acl;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.report.UdmAssigneeStatusReportDto;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link BaseCsvReportHandler} to write UDM Assignees by Status Report.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 04/20/2022
 *
 * @author Mikita Maistrenka
 */
public class UdmAssigneesByStatusReportHandler extends BaseCsvReportHandler<UdmAssigneeStatusReportDto> {

    private static final List<String> HEADERS = List.of("Status", "Assignee", "Count");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public UdmAssigneesByStatusReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(UdmAssigneeStatusReportDto bean) {
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
