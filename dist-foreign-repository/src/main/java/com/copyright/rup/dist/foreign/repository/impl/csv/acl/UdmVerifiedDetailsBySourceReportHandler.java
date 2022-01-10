package com.copyright.rup.dist.foreign.repository.impl.csv.acl;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.report.UdmVerifiedDetailsBySourceReportDto;

import com.google.common.collect.ImmutableList;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link BaseCsvReportHandler} to write UDM Verified Details By Source Report.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 01/05/2022
 *
 * @author Mikita Maistrenka
 */
public class UdmVerifiedDetailsBySourceReportHandler extends BaseCsvReportHandler<UdmVerifiedDetailsBySourceReportDto> {

    private static final List<String> HEADERS = ImmutableList.of("Det LC ID", "Det LC Name", "Agg LC ID", "Agg LC Name",
        "Channel", "Usage Origin", "# of Details");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public UdmVerifiedDetailsBySourceReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(UdmVerifiedDetailsBySourceReportDto bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(getBeanPropertyAsString(bean.getDetailLicenseeClassId()));
        beanProperties.add(bean.getDetailLicenseeClassName());
        beanProperties.add(getBeanPropertyAsString(bean.getAggregateLicenseeClassId()));
        beanProperties.add(bean.getAggregateLicenseeClassName());
        beanProperties.add(bean.getChannel());
        beanProperties.add(bean.getUsageOrigin());
        beanProperties.add(getBeanPropertyAsString(bean.getTotalCountOfDetailsInEligibleStatus()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
