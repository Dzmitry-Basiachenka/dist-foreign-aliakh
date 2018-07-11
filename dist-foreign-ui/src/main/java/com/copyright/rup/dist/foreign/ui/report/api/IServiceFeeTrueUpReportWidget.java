package com.copyright.rup.dist.foreign.ui.report.api;

import com.copyright.rup.vaadin.widget.api.IWidget;

import java.time.LocalDate;

/**
 * Interface for service fee true-up report controller.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 7/11/2018
 *
 * @author Uladzislau_Shalamitski
 */
public interface IServiceFeeTrueUpReportWidget extends IWidget<IServiceFeeTrueUpReportController> {

    /**
     * @return selected to date.
     */
    LocalDate getToDate();

    /**
     * @return selected from date.
     */
    LocalDate getFromDate();

    /**
     * @return selected payment date to.
     */
    LocalDate getPaymentDateTo();
}
