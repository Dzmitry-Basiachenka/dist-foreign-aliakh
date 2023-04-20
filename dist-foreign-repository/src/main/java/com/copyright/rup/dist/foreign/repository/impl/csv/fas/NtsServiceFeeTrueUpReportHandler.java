package com.copyright.rup.dist.foreign.repository.impl.csv.fas;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.report.NtsServiceFeeTrueUpReportDto;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes NTS service fee true-up into an {@link OutputStream}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 07/29/20
 *
 * @author Stanislau Rudak
 */
public class NtsServiceFeeTrueUpReportHandler extends BaseCsvReportHandler<NtsServiceFeeTrueUpReportDto> {

    private static final List<String> HEADERS =
        List.of("NTS Fund Pool Amount", "Pre-Service Fee Amount", "Pre-Service Fee Funds",
            "Post-Service Fee Amount", "Gross Amount", "Estimated Service Fee %", "Estimated Service Fee Amount",
            "Estimated Net Amount", "Gross Amount Sent to LM", "Service Fee % Sent to LM",
            "Service Fee Amount Sent to LM", "Net Amount Sent to LM", "Service Fee True-up");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public NtsServiceFeeTrueUpReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(NtsServiceFeeTrueUpReportDto bean) {
        List<String> beanProperties = new ArrayList<>(HEADERS.size());
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getBatchGrossAmount()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getPreServiceFeeAmount()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getPreServiceFeeFunds()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getPostServiceFeeAmount()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getGrossAmount()));
        beanProperties.add(getBeanServiceFeePercent(bean.getEstimatedServiceFee()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getEstimatedServiceFeeAmount()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getEstimatedNetAmount()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getGrossAmountSentToLm()));
        beanProperties.add(getBeanServiceFeePercent(bean.getServiceFeeSentToLm()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getServiceFeeAmountSentToLm()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getNetAmountSentToLm()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getServiceFeeTrueUpAmount()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
