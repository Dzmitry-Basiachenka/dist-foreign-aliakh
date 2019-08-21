package com.copyright.rup.dist.foreign.integration.crm.api;

/**
 * Represents status of 'insert rights distribution' response from CRM system.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/29/18
 *
 * @author Darya Baraukova
 */
public enum InsertRightsDistributionResponseStatusEnum {

    /**
     * CRM process was successful.
     */
    SUCCESS,

    /**
     * CRM process was failed. Error in CRM application.
     */
    CRM_ERROR
}
