package com.copyright.rup.dist.foreign.ui.report.api;

import com.copyright.rup.vaadin.widget.api.IWidget;

import java.time.LocalDate;
import java.util.Set;

/**
 * Interface for widget for UDM Weekly Survey Report.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/15/2021
 *
 * @author Aliaksandr Liakh
 */
public interface IUdmWeeklySurveyReportWidget extends IWidget<IUdmWeeklySurveyReportController> {

    /**
     * @return channel or {@code null}.
     */
    String getChannel();

    /**
     * @return usage origin or {@code null}.
     */
    String getUsageOrigin();

    /**
     * @return set of periods.
     */
    Set<Integer> getPeriods();

    /**
     * @return date received to.
     */
    LocalDate getDateReceivedTo();

    /**
     * @return date received from.
     */
    LocalDate getDateReceivedFrom();
}
