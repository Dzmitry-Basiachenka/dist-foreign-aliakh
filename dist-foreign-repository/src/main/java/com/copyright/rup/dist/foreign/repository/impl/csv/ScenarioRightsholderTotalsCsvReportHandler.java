package com.copyright.rup.dist.foreign.repository.impl.csv;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;

import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes scenario {@link RightsholderTotalsHolder}s into a {@link PipedOutputStream} connected to the
 * {@link java.io.PipedInputStream}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 04/17/2019
 *
 * @author Pavel Liakh
 */
public class ScenarioRightsholderTotalsCsvReportHandler extends BaseCsvReportHandler<RightsholderTotalsHolder> {

    private static final List<String> HEADERS = List.of("RH Account #", "RH Name", "Payee Account #",
        "Payee Name", "Gross Amt in USD", "Service Fee Amount", "Net Amt in USD", "Service Fee %");

    /**
     * Constructor.
     *
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    public ScenarioRightsholderTotalsCsvReportHandler(PipedOutputStream pipedOutputStream) {
        super(pipedOutputStream);
    }

    @Override
    protected List<String> getBeanProperties(RightsholderTotalsHolder bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(getBeanPropertyAsString(bean.getRightsholder().getAccountNumber()));
        beanProperties.add(bean.getRightsholder().getName());
        beanProperties.add(getBeanPropertyAsString(bean.getPayee().getAccountNumber()));
        beanProperties.add(bean.getPayee().getName());
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getGrossTotal()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getServiceFeeTotal()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getNetTotal()));
        beanProperties.add(getBeanServiceFeePercent(bean.getServiceFee()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
