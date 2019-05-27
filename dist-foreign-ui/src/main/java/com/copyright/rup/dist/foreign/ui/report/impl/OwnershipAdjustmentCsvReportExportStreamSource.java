package com.copyright.rup.dist.foreign.ui.report.impl;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.ui.common.ExportStreamSource;

import java.util.function.Supplier;

/**
 * Subclass of {@link ExportStreamSource} to generate  Ownership Adjustment Report with lazy file name evaluation.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 05/27/2019
 *
 * @author Aliaksandr Liakh
 */
public class OwnershipAdjustmentCsvReportExportStreamSource extends ExportStreamSource {

    private final Supplier<Scenario> scenarioSupplier;

    /**
     * Constructor.
     *
     * @param scenarioSupplier instance of {@link Supplier} to get scenario just before report generation
     * @param reportWriter     an instance of {@link IReportWriter}
     */
    public OwnershipAdjustmentCsvReportExportStreamSource(Supplier<Scenario> scenarioSupplier,
                                                          IReportWriter reportWriter) {
        super(null, reportWriter);
        this.scenarioSupplier = scenarioSupplier;
    }

    @Override
    public String getFileNamePrefix() {
        return String.format("ownership_adjustment_report_%s_", scenarioSupplier.get().getName());
    }
}
