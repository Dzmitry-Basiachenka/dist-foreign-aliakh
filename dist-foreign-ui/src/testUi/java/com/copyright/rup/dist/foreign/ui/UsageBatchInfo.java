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

    private String id;
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
     * Constructor.
     *
     * @param id          usage batch identifier
     * @param name        usage batch name
     * @param paymentDate usage batch payment date
     * @param fiscalYear  usage batch fiscal year
     * @param rro         usage batch rro
     */
    UsageBatchInfo(String id, String name, String paymentDate, String fiscalYear, String rro) {
        this(name, paymentDate, fiscalYear, rro);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String getName() {
        return name;
    }

    String getRro() {
        return rro;
    }

    String getPaymentDate() {
        return paymentDate;
    }

    String getFiscalYear() {
        return fiscalYear;
    }
}
