package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.foreign.domain.UndistributedLiabilitiesReportDto;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
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
class UndistributedLiabilitiesReportHandler extends BaseCsvReportHandler<UndistributedLiabilitiesReportDto> {

    private static final List<String> HEADERS = Arrays.asList("Source RRO Account #", "Source RRO Name", "Payment Date",
        "Gross Undistributed Amount in FDA", "Estimated Service Fee Amount", "Net Estimated Payable Amount",
        "Estimated Service Fee %", "Total FAS Gross Amount Sent to LM", "Total Actual Service Fee Amount in LM",
        "Net Amount Sent to LM", "Actual Service Fee %", "Service Fee True-Up", "Return to CLA Service Fee True-up",
        "Gross Amount Return to CLA", "Total CLA Service Fee Amount", "Net Total Amount Return to CLA");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    UndistributedLiabilitiesReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    List<String> getBeanProperties(UndistributedLiabilitiesReportDto bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(getBeanPropertyAsString(bean.getRroAccountNumber()));
        beanProperties.add(bean.getRroName());
        beanProperties.add(getBeanLocalDate(bean.getPaymentDate()));
        beanProperties.add(getBeanBigDecimal(bean.getGrossAmount()));
        beanProperties.add(getBeanBigDecimal(bean.getEstimatedServiceFeeAmount()));
        beanProperties.add(getBeanBigDecimal(bean.getEstimatedNetAmount()));
        beanProperties.add(getBeanServiceFeePercent(bean.getEstimatedServiceFee()));
        beanProperties.add(getBeanBigDecimal(bean.getGrossAmountSentToLm()));
        beanProperties.add(getBeanBigDecimal(bean.getServiceFeeAmountSentToLm()));
        beanProperties.add(getBeanBigDecimal(bean.getNetAmountSentToLm()));
        beanProperties.add(getBeanServiceFeePercent(bean.getServiceFee()));
        beanProperties.add(getBeanBigDecimal(bean.getServiceFeeTrueUp()));
        beanProperties.add(getBeanBigDecimal(bean.getServiceFeeTrueUpReturnToCla()));
        beanProperties.add(getBeanBigDecimal(bean.getGrossAmountReturnToCla()));
        beanProperties.add(getBeanBigDecimal(bean.getServiceFeeAmountReturnToCla()));
        beanProperties.add(getBeanBigDecimal(bean.getNetAmountReturnToCla()));
        return beanProperties;
    }

    @Override
    List<String> getBeanHeaders() {
        return HEADERS;
    }
}
