package com.copyright.rup.dist.foreign.repository.impl.csv.aacl;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.FundPool;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes AACL Undistributed Liabilities Reconciliation Report into a {@link OutputStream} connected to
 * the {@link java.io.InputStream}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 06/17/2020
 *
 * @author Uladzislau Shalamitski
 */
public class AaclUndistributedLiabilitiesReportHandler extends BaseCsvReportHandler<FundPool> {

    private static final List<String> HEADERS =
        List.of("Fund Pool Name", "Gross Amount", "Service Fee Amount", "Net Amount");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public AaclUndistributedLiabilitiesReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(FundPool bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(getBeanPropertyAsString(bean.getName()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getTotalAmount()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getServiceFeeAmount()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getNetAmount()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
