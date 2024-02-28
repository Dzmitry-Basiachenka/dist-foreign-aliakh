package com.copyright.rup.dist.foreign.vui.report.impl;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.vui.report.api.ICommonReportController;
import com.copyright.rup.dist.foreign.vui.report.api.fas.IFasReportController;
import com.copyright.rup.dist.foreign.vui.report.api.nts.INtsReportController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

/**
 * Report controller provider.
 * <p>
 * Copyright (C) 2024 copyright.com
 * <p>
 * Date: 02/22/2024
 *
 * @author Anton Azarenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReportControllerProvider {

    private final Map<String, ICommonReportController> reportControllerMap = new HashMap<>();

    @Autowired
    private IFasReportController fasReportController;
    @Autowired
    private INtsReportController ntsReportController;

    /**
     * Initializes report controller map.
     */
    @PostConstruct
    public void init() {
        reportControllerMap.put(FdaConstants.FAS_PRODUCT_FAMILY, fasReportController);
        reportControllerMap.put(FdaConstants.NTS_PRODUCT_FAMILY, ntsReportController);
    }

    /**
     * Returns controller based on provided product family.
     *
     * @param productFamily product family
     * @return instance of {@link ICommonReportController}
     */
    public ICommonReportController getController(String productFamily) {
        return reportControllerMap.get(productFamily);
    }
}
