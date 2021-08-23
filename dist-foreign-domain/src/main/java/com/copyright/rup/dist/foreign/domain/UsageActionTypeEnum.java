package com.copyright.rup.dist.foreign.domain;

/**
 * Represents types of actions for {@link Usage}s.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 10/16/2017
 *
 * @author Uladzislau_Shalamitski
 */
public enum UsageActionTypeEnum {

    /**
     * Action type for loading {@link Usage}.
     */
    LOADED,

    /**
     * Action type for NTS usages created based on {@link UsageStatusEnum#ARCHIVED} usages.
     */
    CREATED,

    /**
     * Action type for successfully found work.
     */
    WORK_FOUND,

    /**
     * Action type for case when work was not found.
     */
    WORK_NOT_FOUND,

    /**
     * Action type for case when there are multiple works were found.
     */
    MULTIPLE_RESULTS,

    /**
     * Action type for sending {@link Usage} for research.
     */
    WORK_RESEARCH,

    /**
     * Action type for successful finding rightsholder of {@link Usage} in RMS.
     */
    RH_FOUND,

    /**
     * Action type for unsuccessful finding rightsholder of {@link Usage} in RMS.
     */
    RH_NOT_FOUND,

    /**
     * Action type for case when right for a work was found in RMS, but is denied for RH.
     */
    WORK_NOT_GRANTED,

    /**
     * Action type for sending {@link Usage} to RMS for Rights Assignment.
     */
    SENT_FOR_RA,

    /**
     * Action type for changing {@link Usage}'s status to {@link UsageStatusEnum#NTS_WITHDRAWN}
     * and product family to "NTS".
     */
    ELIGIBLE_FOR_NTS,

    /**
     * Action type for NTS usages with US tax rightsholder.
     */
    US_TAX_COUNTRY,

    /**
     * Action type for ACL UDM usage edit.
     */
    USAGE_EDIT,

    /**
     * Action type for ACL UDM usage assignment.
     */
    ASSIGNEE_CHANGE,

    /**
     * Action type for eligible usages.
     */
    ELIGIBLE,

    /**
     * Action type for ineligible usages.
     */
    INELIGIBLE,

    /**
     * Action type for adding {@link Usage} to {@link Scenario}.
     */
    ADDED_TO_SCENARIO,

    /**
     * Action type for excluding {@link Usage} from {@link Scenario}.
     */
    EXCLUDED_FROM_SCENARIO,

    /**
     * Action type for {@link Usage}s that receive new rightholders during reconciliation.
     */
    RH_UPDATED,

    /**
     * Action type for sending {@link Usage} to LM.
     */
    SENT_TO_LM,

    /**
     * Action type for paid {@link Usage}.
     */
    PAID,

    /**
     * Action type for {@link Usage} sent to CRM.
     */
    ARCHIVED
}
