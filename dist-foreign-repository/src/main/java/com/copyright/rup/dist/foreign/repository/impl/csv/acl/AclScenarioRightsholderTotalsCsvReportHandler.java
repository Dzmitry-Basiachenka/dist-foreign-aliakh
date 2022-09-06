package com.copyright.rup.dist.foreign.repository.impl.csv.acl;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolder;

import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Writes scenario {@link AclRightsholderTotalsHolder}s into a {@link PipedOutputStream} connected to the
 * {@link java.io.PipedInputStream}.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 09/05/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclScenarioRightsholderTotalsCsvReportHandler extends BaseCsvReportHandler<AclRightsholderTotalsHolder> {

    private static final List<String> HEADERS = Arrays.asList("RH Account #", "RH Name", "Print Gross Amt in USD",
        "Print Service Fee Amt", "Print Net Amt in USD", "Digital Gross Amt in USD", "Digital Service Fee Amt",
        "Digital Net Amt in USD", "# of Titles", "# of Agg Lic Classes", "License Type");

    /**
     * Constructor.
     *
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    public AclScenarioRightsholderTotalsCsvReportHandler(PipedOutputStream pipedOutputStream) {
        super(pipedOutputStream);
    }

    @Override
    protected List<String> getBeanProperties(AclRightsholderTotalsHolder bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(getBeanPropertyAsString(bean.getRightsholder().getAccountNumber()));
        beanProperties.add(bean.getRightsholder().getName());
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getGrossTotalPrint()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getServiceFeeTotalPrint()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getNetTotalPrint()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getGrossTotalDigital()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getServiceFeeTotalDigital()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getNetTotalDigital()));
        beanProperties.add(getBeanPropertyAsString(bean.getNumberOfTitles()));
        beanProperties.add(getBeanPropertyAsString(bean.getNumberOfAggLcClasses()));
        beanProperties.add(bean.getLicenseType());
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
