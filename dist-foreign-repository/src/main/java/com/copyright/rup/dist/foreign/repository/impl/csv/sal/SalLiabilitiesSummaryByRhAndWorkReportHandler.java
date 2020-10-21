package com.copyright.rup.dist.foreign.repository.impl.csv.sal;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.report.SalLiabilitiesSummaryByRhAndWorkReportDto;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Implementation of {@link BaseCsvReportHandler} to write SAL Liabilities Summary by Rightsholder and Work Report.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 10/13/2020
 *
 * @author Aliaksandr Liakh
 */
public class SalLiabilitiesSummaryByRhAndWorkReportHandler
    extends BaseCsvReportHandler<SalLiabilitiesSummaryByRhAndWorkReportDto> {

    private static final List<String> HEADERS = ImmutableList.of("RH Account #", "RH Name", "Wr Wrk Inst",
        "System Title", "Gross Amount", "Service Fee Amount", "Net Amount");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public SalLiabilitiesSummaryByRhAndWorkReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    /**
     * Writes scenario names into the report.
     *
     * @param scenarios set of scenarios
     */
    public void writeScenarioNames(List<Scenario> scenarios) {
        writeStringRow(buildRow(StringUtils.EMPTY));
        writeStringRow(buildRow("Scenarios"));
        scenarios.forEach(scenario -> writeStringRow(buildRow(scenario.getName())));
    }

    @Override
    protected List<String> getBeanProperties(SalLiabilitiesSummaryByRhAndWorkReportDto bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(getBeanPropertyAsString(bean.getRhAccountNumber()));
        beanProperties.add(bean.getRhName());
        beanProperties.add(bean.getWrWrkInst());
        beanProperties.add(bean.getSystemTitle());
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getGrossAmount()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getServiceFeeAmount()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getNetAmount()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }

    private List<String> buildRow(String firstColumn) {
        return Arrays.asList(firstColumn, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
            StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
    }
}
