package com.copyright.rup.dist.foreign.repository.impl.csv.acl;

import com.copyright.rup.dist.foreign.domain.report.AclLiabilityDetailsReportDto;
import com.copyright.rup.dist.foreign.domain.report.AclReportTotalAmountsDto;

import org.apache.commons.lang3.StringUtils;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Writes ACL Liability details into an {@link OutputStream}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/11/2022
 *
 * @author Anton Azarenka
 */
public class AclLiabilityDetailsCsvReportHandler
    extends AclCommonCalculationsCsvReportHandler<AclLiabilityDetailsReportDto> {

    private static final List<String> HEADERS =
        Arrays.asList("RH Account #", "RH Name", "Work Title", "Wr Wrk Inst", "Scenario Name",
            "License Type", "Dist TOU", "Agg LC ID", "Agg LC Name", "Gross Amount", "Net Amount");

    /**
     * Constructor.
     *
     * @param outputStream {@link OutputStream} instance
     */
    public AclLiabilityDetailsCsvReportHandler(OutputStream outputStream) {
        super(outputStream, "ACL Liability Details Report");
    }

    @Override
    public List<String> getBeanProperties(AclLiabilityDetailsReportDto bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(getBeanPropertyAsString(bean.getRhAccountNumber()));
        beanProperties.add(bean.getRhName());
        beanProperties.add(bean.getTitle());
        beanProperties.add(bean.getWrWrkInst());
        beanProperties.add(bean.getScenarioName());
        beanProperties.add(bean.getLicenseType());
        beanProperties.add(bean.getTypeOfUse());
        beanProperties.add(getBeanPropertyAsString(bean.getAggregateLicenseeClassId()));
        beanProperties.add(bean.getAggregateLicenseeClassName());
        beanProperties.add(getBeanBigDecimal(bean.getGrossAmount()));
        beanProperties.add(getBeanBigDecimal(bean.getNetAmount()));
        return beanProperties;
    }

    @Override
    public void writeTotals(AclReportTotalAmountsDto totalAmountsDto) {
        writeStringRow(
            Arrays.asList("Grand Total", StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
                StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
                getBeanBigDecimal(totalAmountsDto.getGrossAmount()),
                getBeanBigDecimal(totalAmountsDto.getNetAmount())));
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
