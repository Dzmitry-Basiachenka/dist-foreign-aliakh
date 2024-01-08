package com.copyright.rup.dist.foreign.repository.impl.csv.aacl;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.report.WorkSharesByAggLcClassReportDto;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes Work Shares by Aggregate Licensee Class Summary Report into a {@link OutputStream}
 * connected to the {@link java.io.InputStream}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 04/22/2020
 *
 * @author Uladzilsua Shalamitski
 */
public class WorkSharesByAggLcClassSummaryReportHandler
    extends BaseCsvReportHandler<WorkSharesByAggLcClassReportDto> {

    private static final List<String> HEADERS =
        List.of("Agg LC ID", "Agg LC Enrollment", "Agg LC Discipline", "Total Shares");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public WorkSharesByAggLcClassSummaryReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(WorkSharesByAggLcClassReportDto bean) {
        List<String> beanProperties = new ArrayList<>(HEADERS.size());
        beanProperties.add(getBeanPropertyAsString(bean.getAggregateLicenseeClass().getId()));
        beanProperties.add(getBeanPropertyAsString(bean.getAggregateLicenseeClass().getEnrollmentProfile()));
        beanProperties.add(getBeanPropertyAsString(bean.getAggregateLicenseeClass().getDiscipline()));
        beanProperties.add(roundBigDecimal(bean.getTotalShare()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }

    private String roundBigDecimal(BigDecimal value) {
        return value.setScale(10, RoundingMode.HALF_UP).toString();
    }
}
