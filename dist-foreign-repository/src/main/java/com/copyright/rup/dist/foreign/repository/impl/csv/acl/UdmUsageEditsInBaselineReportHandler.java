package com.copyright.rup.dist.foreign.repository.impl.csv.acl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.report.UdmUsageEditsInBaselineReportDto;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link BaseCsvReportHandler} to write UDM Usage Edits in Baseline Report.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 03/10/2022
 *
 * @author Aliaksandr Liakh
 */
public class UdmUsageEditsInBaselineReportHandler extends BaseCsvReportHandler<UdmUsageEditsInBaselineReportDto> {

    private static final List<String> HEADERS = List.of("Detail ID", "Action Type", "Action Reason",
        "Wr Wrk Inst", "System Title", "Usage Period", "Updated Date", "Updated By");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public UdmUsageEditsInBaselineReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(UdmUsageEditsInBaselineReportDto bean) {
        List<String> beanProperties = new ArrayList<>(HEADERS.size());
        beanProperties.add(bean.getUsageId());
        beanProperties.add(getBeanPropertyAsString(bean.getActionType()));
        beanProperties.add(bean.getActionReason());
        beanProperties.add(getBeanPropertyAsString(bean.getWrWrkInst()));
        beanProperties.add(bean.getSystemTitle());
        beanProperties.add(getBeanPropertyAsString(bean.getPeriod()));
        beanProperties.add(DateFormatUtils.format(bean.getUpdateDate(), RupDateUtils.US_DATETIME_FORMAT_PATTERN_LONG));
        beanProperties.add(bean.getUpdateUser());
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
