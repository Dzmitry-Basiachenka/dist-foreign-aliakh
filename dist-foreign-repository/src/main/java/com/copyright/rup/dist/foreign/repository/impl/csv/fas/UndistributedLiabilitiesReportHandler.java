package com.copyright.rup.dist.foreign.repository.impl.csv.fas;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.report.UndistributedLiabilitiesReportDto;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes calculated undistributed amounts into a {@link OutputStream} connected to the {@link java.io.InputStream}.
 * Is used to generate Undistributed Liabilities Reconciliation Report.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 05/14/18
 *
 * @author Uladzislau Shalamitski
 */
public class UndistributedLiabilitiesReportHandler extends BaseCsvReportHandler<UndistributedLiabilitiesReportDto> {

    private static final List<String> HEADERS =
        List.of("Source RRO Account #", "Source RRO Name", "Payment Date", "Gross Undistributed Amount in FDA",
            "Estimated Service Fee Amount", "Net Estimated Payable Amount", "Estimated Service Fee %",
            "Gross Undistributed Withdrawn Amt in FDA");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public UndistributedLiabilitiesReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(UndistributedLiabilitiesReportDto bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(getBeanPropertyAsString(bean.getRroAccountNumber()));
        beanProperties.add(bean.getRroName());
        beanProperties.add(getBeanLocalDate(bean.getPaymentDate()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getGrossAmount()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getEstimatedServiceFeeAmount()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getEstimatedNetAmount()));
        beanProperties.add(getBeanServiceFeePercent(bean.getEstimatedServiceFee()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getWithdrawnGrossAmount()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
