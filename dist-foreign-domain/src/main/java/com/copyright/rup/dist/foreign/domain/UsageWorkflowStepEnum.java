package com.copyright.rup.dist.foreign.domain;

/**
 * Enum for Usage additional workflow steps to make usage Eligible for adding to scenario.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/07/19
 *
 * @author Darya Baraukova
 */
public enum UsageWorkflowStepEnum {

    /**
     * Load Usage Batch step.
     */
    LOAD_BATCH,

    /**
     * Load Fund Pool step.
     */
    LOAD_FUNDPOOL,

    /**
     * Send for Research step.
     */
    RESEARCH,

    /**
     * Load Researched Details step.
     */
    LOAD_RESEARCHED,

    /**
     * Assign Classification step.
     */
    CLASSIFICATION
}
