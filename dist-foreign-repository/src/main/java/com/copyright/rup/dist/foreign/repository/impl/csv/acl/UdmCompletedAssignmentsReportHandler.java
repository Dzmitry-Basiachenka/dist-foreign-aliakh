package com.copyright.rup.dist.foreign.repository.impl.csv.acl;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.report.UdmCompletedAssignmentsReportDto;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link BaseCsvReportHandler} to write UDM Completed Assignments by Employee Report.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/10/2022
 *
 * @author Ihar Suvorau
 */
public class UdmCompletedAssignmentsReportHandler extends BaseCsvReportHandler<UdmCompletedAssignmentsReportDto> {

    private static final List<String> HEADERS =
        List.of("User Name", "# of Usages Completed ", "# of Values Completed");

    /**
     * Constructor.
     *
     * @param outputStream {@link OutputStream} instance
     */
    public UdmCompletedAssignmentsReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(UdmCompletedAssignmentsReportDto bean) {
        List<String> beanProperties = new ArrayList<>(HEADERS.size());
        beanProperties.add(bean.getUserName());
        beanProperties.add(getBeanPropertyAsString(bean.getUsagesCount()));
        beanProperties.add(getBeanPropertyAsString(bean.getValuesCount()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
