package com.copyright.rup.dist.foreign.ui;

/**
 * Contains information about usage batch.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 03/17/17
 *
 * @author Ihar Suvorau
 */
class UsageBatchInfo {

    private String name;
    private String paymentDate;
    private String fiscalYear;
    private String rro;

    /**
     * Constructor.
     *
     * @param name        usage batch name
     * @param paymentDate usage batch payment date
     * @param fiscalYear  usage batch fiscal year
     * @param rro         usage batch rro
     */
    UsageBatchInfo(String name, String paymentDate, String fiscalYear, String rro) {
        this.name = name;
        this.paymentDate = paymentDate;
        this.fiscalYear = fiscalYear;
        this.rro = rro;
    }

    /**
     * @return name.
     */
    String getName() {
        return name;
    }

    /**
     * @return rro.
     */
    String getRro() {
        return rro;
    }

    /**
     * @return payment date.
     */
    String getPaymentDate() {
        return paymentDate;
    }

    /**
     * @return fiscal year.
     */
    String getFiscalYear() {
        return fiscalYear;
    }
}
