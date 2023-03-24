package com.copyright.rup.dist.foreign.repository.impl.csv.acl;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.report.AclFundPoolByAggLcReportDto;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes ACL Fund Pools grouped by Agg LC Report into {@link OutputStream}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 03/24/2023
 *
 * @author Ihar Suvorau
 */
public class AclFundPoolByAggLcCsvReportHandler extends BaseCsvReportHandler<AclFundPoolByAggLcReportDto> {

    private static final List<String> HEADERS =
        List.of("Fund Pool Name", "Period", "License Type", "Agg LC ID", "Agg LC Name", "Net Amount");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public AclFundPoolByAggLcCsvReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(AclFundPoolByAggLcReportDto bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(bean.getFundPoolName());
        beanProperties.add(getBeanPropertyAsString(bean.getPeriod()));
        beanProperties.add(bean.getLicenseType());
        beanProperties.add(getBeanPropertyAsString(bean.getAggregateLicenseeClassId()));
        beanProperties.add(bean.getAggregateLicenseeClassName());
        beanProperties.add(getBeanBigDecimal(bean.getNetAmount()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
