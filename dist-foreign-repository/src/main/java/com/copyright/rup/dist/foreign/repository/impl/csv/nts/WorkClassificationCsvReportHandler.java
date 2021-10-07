package com.copyright.rup.dist.foreign.repository.impl.csv.nts;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.WorkClassification;

import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Writes {@link WorkClassification}s into a {@link PipedOutputStream}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 07/02/19
 *
 * @author Pavel Liakh
 */
public class WorkClassificationCsvReportHandler extends BaseCsvReportHandler<WorkClassification> {

    private static final List<String> HEADERS =
        Arrays.asList("Wr Wrk Inst", "System Title", "Classification", "Standard Number", "Standard Number Type",
            "RH Account #", "RH Name", "Classification Date", "Classified By");

    /**
     * Constructor.
     *
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    public WorkClassificationCsvReportHandler(PipedOutputStream pipedOutputStream) {
        super(pipedOutputStream);
    }

    @Override
    protected List<String> getBeanProperties(WorkClassification bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(getBeanPropertyAsString(bean.getWrWrkInst()));
        beanProperties.add(bean.getSystemTitle());
        beanProperties.add(bean.getClassification());
        beanProperties.add(bean.getStandardNumber());
        beanProperties.add(bean.getStandardNumberType());
        beanProperties.add(getBeanPropertyAsString(bean.getRhAccountNumber()));
        beanProperties.add(getBeanPropertyAsString(bean.getRhName()));
        beanProperties.add(convertDateToString(bean.getUpdateDate()));
        beanProperties.add(bean.getUpdateUser());
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
