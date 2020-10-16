package com.copyright.rup.dist.foreign.repository.impl.csv.sal;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.report.LiabilitiesByRhReportDto;

import org.apache.commons.lang3.StringUtils;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Writes {@link LiabilitiesByRhReportDto} usages to {@link OutputStream}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 10/15/20
 *
 * @author Uladzislau Shalamitski
 */
public class LiabilitiesByRhReportHandler extends BaseCsvReportHandler<LiabilitiesByRhReportDto> {

    private static final List<String> HEADERS = Arrays.asList("RH Account #", "RH Name", "Gross Amount",
        "Service Fee Amount", "Net Amount", "Item Bank Net Amount", "Usage Detail Net Amount", "Count of Passages");

    /**
     * Constructor.
     *
     * @param outputStream {@link OutputStream} instance
     */
    public LiabilitiesByRhReportHandler(OutputStream outputStream) {
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
    protected List<String> getBeanProperties(LiabilitiesByRhReportDto bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(getBeanPropertyAsString(bean.getRhAccountNumber()));
        beanProperties.add(bean.getRhName());
        beanProperties.add(getBeanPropertyAsString(bean.getGrossAmount()));
        beanProperties.add(getBeanPropertyAsString(bean.getServiceFeeAmount()));
        beanProperties.add(getBeanPropertyAsString(bean.getNetAmount()));
        beanProperties.add(getBeanPropertyAsString(bean.getItemBankNetAmount()));
        beanProperties.add(getBeanPropertyAsString(bean.getUsageDetailNetAmount()));
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
