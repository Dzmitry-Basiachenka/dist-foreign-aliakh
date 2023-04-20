package com.copyright.rup.dist.foreign.repository.impl.csv.aacl;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.report.WorkSharesByAggLcClassReportDto;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes Work Shares by Aggregate Licensee Class Report into a {@link OutputStream} connected to
 * the {@link java.io.InputStream}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 04/23/2020
 *
 * @author Ihar Suvorau
 */
public class WorkSharesByAggLcClassReportHandler extends BaseCsvReportHandler<WorkSharesByAggLcClassReportDto> {

    private static final List<String> HEADERS =
        List.of("Agg LC ID", "Agg LC Enrollment", "Agg LC Discipline", "Wr Wrk Inst", "Work Title",
            "RH Account #", "RH Name", "Volume Share", "Value Share", "Total Share", "Net Amount");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public WorkSharesByAggLcClassReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(WorkSharesByAggLcClassReportDto bean) {
        List<String> beanProperties = new ArrayList<>(HEADERS.size());
        beanProperties.add(getBeanPropertyAsString(bean.getAggregateLicenseeClass().getId()));
        beanProperties.add(bean.getAggregateLicenseeClass().getEnrollmentProfile());
        beanProperties.add(bean.getAggregateLicenseeClass().getDiscipline());
        beanProperties.add(getBeanPropertyAsString(bean.getWrWrkInst()));
        beanProperties.add(bean.getWorkTitle());
        beanProperties.add(getBeanPropertyAsString(bean.getRightsholder().getAccountNumber()));
        beanProperties.add(bean.getRightsholder().getName());
        beanProperties.add(roundBigDecimal(bean.getVolumeShare()));
        beanProperties.add(roundBigDecimal(bean.getValueShare()));
        beanProperties.add(roundBigDecimal(bean.getTotalShare()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getNetAmount()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }

    private String roundBigDecimal(BigDecimal value) {
        return value.setScale(10, BigDecimal.ROUND_HALF_UP).toString();
    }
}
