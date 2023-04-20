package com.copyright.rup.dist.foreign.repository.impl.csv.sal;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.report.SalUndistributedLiabilitiesReportDto;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes SAL Undistributed Liabilities Reconciliation Report into a {@link OutputStream} connected to
 * the {@link java.io.InputStream}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 11/04/2020
 *
 * @author Dzmitry Basiachenka
 */
public class SalUndistributedLiabilitiesReportHandler
    extends BaseCsvReportHandler<SalUndistributedLiabilitiesReportDto> {

    private static final List<String> HEADERS = List.of("Date Received", "Fund Pool Name", "Gross Amount",
        "Service Fee Amount", "Net Amount");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public SalUndistributedLiabilitiesReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(SalUndistributedLiabilitiesReportDto bean) {
        List<String> beanProperties = new ArrayList<>(HEADERS.size());
        beanProperties.add(getBeanLocalDate(bean.getDateReceived()));
        beanProperties.add(bean.getFundPoolName());
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getGrossAmount()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getServiceFeeAmount()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getNetAmount()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
