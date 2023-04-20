package com.copyright.rup.dist.foreign.repository.impl.csv.fas;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.report.FasServiceFeeTrueUpReportDto;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes FAS/FAS2 service fee true-up into a {@link OutputStream} connected to the {@link java.io.InputStream}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 7/11/18
 *
 * @author Uladzislau Shalamitski
 */
public class FasServiceFeeTrueUpReportHandler extends BaseCsvReportHandler<FasServiceFeeTrueUpReportDto> {

    private static final List<String> HEADERS =
        List.of("Source RRO Account #", "Source RRO Name", "Payment Date", "Total Gross Amount Sent to LM",
            "Total Actual Service Fee Amount in LM", "Actual Service Fee %", "Net Amount Sent to LM",
            "Estimated Service Fee Amount Sent to LM", "Estimated Service Fee %", "Service Fee True-up",
            "Total Gross Amount Return to CLA", "Estimated CLA Service Fee Amount", "Actual CLA Service Fee Amount",
            "Net Total Amount Return to CLA", "Return to CLA Service Fee True-up");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public FasServiceFeeTrueUpReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(FasServiceFeeTrueUpReportDto bean) {
        List<String> beanProperties = new ArrayList<>(HEADERS.size());
        beanProperties.add(getBeanPropertyAsString(bean.getRroAccountNumber()));
        beanProperties.add(bean.getRroName());
        beanProperties.add(getBeanLocalDate(bean.getPaymentDate()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getGrossAmountSentToLm()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getServiceFeeAmountSentToLm()));
        beanProperties.add(getBeanServiceFeePercent(bean.getServiceFeeSentToLm()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getNetAmountSentToLm()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getEstimatedServiceFeeAmountSentToLm()));
        beanProperties.add(getBeanServiceFeePercent(bean.getEstimatedServiceFee()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getServiceFeeTrueUp()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getGrossAmountReturnToCla()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getEstimatedServiceFeeAmountReturnToCla()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getServiceFeeAmountReturnToCla()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getNetAmountReturnToCla()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getServiceFeeTrueUpReturnToCla()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
