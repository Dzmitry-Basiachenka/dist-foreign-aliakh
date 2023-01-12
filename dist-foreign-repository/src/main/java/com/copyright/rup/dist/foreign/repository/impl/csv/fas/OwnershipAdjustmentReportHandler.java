package com.copyright.rup.dist.foreign.repository.impl.csv.fas;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes Ownership Adjustment Report into a {@link OutputStream} connected to the {@link java.io.InputStream}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 05/22/2019
 *
 * @author Aliaksandr Liakh
 */
public class OwnershipAdjustmentReportHandler extends BaseCsvReportHandler<RightsholderDiscrepancy> {

    private static final List<String> HEADERS =
        List.of("RH Account #", "RH Name", "New RH Account #", "New RH Name", "Wr Wrk Inst", "Title", "Status");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public OwnershipAdjustmentReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(RightsholderDiscrepancy bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(getBeanPropertyAsString(bean.getOldRightsholder().getAccountNumber()));
        beanProperties.add(bean.getOldRightsholder().getName());
        beanProperties.add(getBeanPropertyAsString(bean.getNewRightsholder().getAccountNumber()));
        beanProperties.add(bean.getNewRightsholder().getName());
        beanProperties.add(getBeanPropertyAsString(bean.getWrWrkInst()));
        beanProperties.add(bean.getWorkTitle());
        beanProperties.add(bean.getStatus().name());
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
