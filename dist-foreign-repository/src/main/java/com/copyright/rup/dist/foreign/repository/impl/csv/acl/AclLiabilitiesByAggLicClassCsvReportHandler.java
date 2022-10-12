package com.copyright.rup.dist.foreign.repository.impl.csv.acl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.report.AclCalculationReportsInfoDto;
import com.copyright.rup.dist.foreign.domain.report.AclLiabilitiesByAggLicClassReportDto;
import com.copyright.rup.dist.foreign.domain.report.AclReportTotalAmountsDto;

import com.google.common.collect.ImmutableList;

import org.apache.commons.lang3.StringUtils;

import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Writes ACL Liabilities By Agg Licensee Class Report {@link OutputStream}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/10/2022
 *
 * @author Ihar Suvorau
 */
public class AclLiabilitiesByAggLicClassCsvReportHandler
    extends BaseCsvReportHandler<AclLiabilitiesByAggLicClassReportDto> {

    private static final List<String> HEADERS =
        Arrays.asList("Agg LC ID", "Agg LC Name", "Gross Amount", "Net Amount", "Print Net Amount",
            "Digital Net Amount", "ACL Net Amount", "MACL Net Amount", "VGW Net Amount", "JACDCL Net Amount");
    private static final List<String> METADATA_HEADERS =
        ImmutableList.of("Report Name", "Report Period", "User", "Report Date", "Scenarios");
    private static final String REPORT_NAME = "Liabilities By Aggregate Licensee Class Report";
    private final DateTimeFormatter formatter =
        DateTimeFormatter.ofPattern(RupDateUtils.US_DATETIME_FORMAT_PATTERN_LONG);

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public AclLiabilitiesByAggLicClassCsvReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    /**
     * Writes total amounts row.
     *
     * @param totalAmountsDto instance of {@link AclReportTotalAmountsDto}
     */
    public void writeTotals(AclReportTotalAmountsDto totalAmountsDto) {
        writeStringRow(Arrays.asList("Grand Total", StringUtils.EMPTY,
            getBeanBigDecimal(totalAmountsDto.getGrossAmount()),
            getBeanBigDecimal(totalAmountsDto.getNetAmount()),
            getBeanBigDecimal(totalAmountsDto.getPrintNetAmount()),
            getBeanBigDecimal(totalAmountsDto.getDigitalNetAmount()),
            getBeanBigDecimal(totalAmountsDto.getAclNetAmount()),
            getBeanBigDecimal(totalAmountsDto.getMaclNetAmount()),
            getBeanBigDecimal(totalAmountsDto.getVgwNetAmount()),
            getBeanBigDecimal(totalAmountsDto.getJacdclNetAmount())));
    }

    /**
     * Writes metadata into the report.
     *
     * @param metadata ACL calculation report metadata
     */
    public void writeMetadata(AclCalculationReportsInfoDto metadata) {
        writeStringRow(Arrays.asList(StringUtils.EMPTY, StringUtils.EMPTY));
        writeStringRow(METADATA_HEADERS);
        writeStringRow(Arrays.asList(REPORT_NAME, String.valueOf(metadata.getPeriod()), metadata.getUser(),
            metadata.getReportDateTime().format(formatter),
            metadata.getScenarios().stream().map(AclScenario::getName).collect(Collectors.joining(", "))));
    }

    @Override
    protected List<String> getBeanProperties(AclLiabilitiesByAggLicClassReportDto bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(getBeanPropertyAsString(bean.getAggregateLicenseeClassId()));
        beanProperties.add(bean.getAggregateLicenseeClassName());
        beanProperties.add(getBeanBigDecimal(bean.getGrossAmount()));
        beanProperties.add(getBeanBigDecimal(bean.getNetAmount()));
        beanProperties.add(getBeanBigDecimal(bean.getPrintNetAmount()));
        beanProperties.add(getBeanBigDecimal(bean.getDigitalNetAmount()));
        beanProperties.add(getBeanBigDecimal(bean.getAclNetAmount()));
        beanProperties.add(getBeanBigDecimal(bean.getMaclNetAmount()));
        beanProperties.add(getBeanBigDecimal(bean.getVgwNetAmount()));
        beanProperties.add(getBeanBigDecimal(bean.getJacdclNetAmount()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
