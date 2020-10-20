package com.copyright.rup.dist.foreign.repository.impl.csv.sal;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.report.SalLiabilitiesByRhReportDto;

import org.apache.commons.lang3.StringUtils;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Writes {@link SalLiabilitiesByRhReportDto} usages to {@link OutputStream}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 10/15/20
 *
 * @author Uladzislau Shalamitski
 */
public class SalLiabilitiesByRhReportHandler extends BaseCsvReportHandler<SalLiabilitiesByRhReportDto> {

    private static final List<String> HEADERS = Arrays.asList("RH Account #", "RH Name", "Gross Amount",
        "Service Fee Amount", "Net Amount", "Item Bank Net Amount", "Usage Detail Net Amount", "Count of Passages");

    /**
     * Constructor.
     *
     * @param outputStream {@link OutputStream} instance
     */
    public SalLiabilitiesByRhReportHandler(OutputStream outputStream) {
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
    protected List<String> getBeanProperties(SalLiabilitiesByRhReportDto bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(getBeanPropertyAsString(bean.getRhAccountNumber()));
        beanProperties.add(bean.getRhName());
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getGrossAmount()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getServiceFeeAmount()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getNetAmount()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getItemBankNetAmount()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getUsageDetailNetAmount()));
        beanProperties.add(getBeanPropertyAsString(bean.getCountOfPassages()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }

    private List<String> buildRow(String firstColumn) {
        return Arrays.asList(firstColumn, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
            StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
    }
}
