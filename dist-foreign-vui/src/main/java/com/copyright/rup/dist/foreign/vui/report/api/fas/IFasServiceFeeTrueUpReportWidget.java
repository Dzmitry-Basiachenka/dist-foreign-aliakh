package com.copyright.rup.dist.foreign.vui.report.api.fas;

import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IWidget;

import java.time.LocalDate;

/**
 * Interface for service fee true-up report controller for FAS/FAS2.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 7/11/2018
 *
 * @author Uladzislau_Shalamitski
 */
public interface IFasServiceFeeTrueUpReportWidget extends IWidget<IFasServiceFeeTrueUpReportController> {

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
