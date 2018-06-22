package com.copyright.rup.dist.foreign.repository.impl.csv;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.report.UndistributedLiabilitiesReportDto;

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
public class UndistributedLiabilitiesReportHandler extends BaseCsvReportHandler<UndistributedLiabilitiesReportDto> {

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
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getGrossAmountSentToLm()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getServiceFeeAmountSentToLm()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getNetAmountSentToLm()));
        beanProperties.add(getBeanServiceFeePercent(bean.getServiceFee()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getServiceFeeTrueUp()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getServiceFeeTrueUpReturnToCla()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getGrossAmountReturnToCla()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getServiceFeeAmountReturnToCla()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getNetAmountReturnToCla()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
