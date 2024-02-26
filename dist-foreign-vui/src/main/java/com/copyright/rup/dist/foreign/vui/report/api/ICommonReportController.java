package com.copyright.rup.dist.foreign.vui.report.api;

/**
 * Interface for common report controller.
 * <p>
 * Copyright (C) 2024 copyright.com
 * <p>
 * Date: 02/22/2024
 *
 * @author Anton Azarenka
 */
public interface ICommonReportController {

    /**
     * @return tax notification report controller.
     */
    ITaxNotificationReportController getTaxNotificationReportController();
}
