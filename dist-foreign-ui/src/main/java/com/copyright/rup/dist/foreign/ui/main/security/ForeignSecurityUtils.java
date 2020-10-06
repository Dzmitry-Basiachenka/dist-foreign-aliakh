package com.copyright.rup.dist.foreign.ui.main.security;

import com.copyright.rup.vaadin.security.SecurityUtils;

/**
 * Utility class for security purposes.
 * Allows to determine whether currently logged in user has some permissions or not.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/13/17
 *
 * @author Aliaksei Pchelnikau
 * @author Ihar Suvorau
 */
public final class ForeignSecurityUtils {


    private ForeignSecurityUtils() {
        throw new AssertionError("Constructor shouldn't be called directly");
    }

    /**
     * @return {@code true} if user has permission to access application.
     */
    public static boolean hasAccessPermission() {
        return SecurityUtils.hasPermission("FDA_ACCESS_APPLICATION");
    }

    /**
     * @return {@code true} if user has permission to load usages.
     */
    public static boolean hasLoadUsagePermission() {
        return SecurityUtils.hasPermission("FDA_LOAD_USAGE");
    }

    /**
     * @return {@code true} if user has permission to load fund pool.
     */
    public static boolean hasLoadFundPoolPermission() {
        return SecurityUtils.hasPermission("FDA_LOAD_FUND_POOL");
    }

    /**
     * @return {@code true} if user has permission to delete fund pool.
     */
    public static boolean hasDeleteFundPoolPermission() {
        return SecurityUtils.hasPermission("FDA_DELETE_FUND_POOL");
    }

    /**
     * @return {@code true} if user has permission to load researched usages.
     */
    public static boolean hasLoadResearchedUsagePermission() {
        return SecurityUtils.hasPermission("FDA_LOAD_RESEARCHED_USAGE");
    }

    /**
     * @return {@code true} if user has permission to delete usages.
     */
    public static boolean hasDeleteUsagePermission() {
        return SecurityUtils.hasPermission("FDA_DELETE_USAGE");
    }

    /**
     * @return {@code true} if user has permission to create and edit scenarios.
     */
    public static boolean hasCreateEditScenarioPermission() {
        return SecurityUtils.hasPermission("FDA_CREATE_EDIT_SCENARIO");
    }

    /**
     * @return {@code true} if user has permission to view scenarios.
     */
    public static boolean hasViewScenarioPermission() {
        return SecurityUtils.hasPermission("FDA_VIEW_SCENARIO");
    }

    /**
     * @return {@code true} if user has permission to send usages for work research.
     */
    public static boolean hasSendForWorkResearchPermission() {
        return SecurityUtils.hasPermission("FDA_SEND_FOR_WORK_RESEARCH");
    }

    /**
     * @return {@code true} if user has permission to send usages for classification.
     */
    public static boolean hasSendForClassificationPermission() {
        return SecurityUtils.hasPermission("FDA_SEND_FOR_CLASSIFICATION");
    }

    /**
     * @return {@code true} if user has permission to load classified usages.
     */
    public static boolean hasLoadClassifiedUsagePermission() {
        return SecurityUtils.hasPermission("FDA_LOAD_CLASSIFIED_USAGE");
    }

    /**
     * @return {@code true} if user has permission to create and delete Additional Funds.
     */
    public static boolean hasCreateDeleteFundPermission() {
        return SecurityUtils.hasPermission("FDA_CREATE_DELETE_FUND");
    }

    /**
     * @return {@code true} if user has permission to assigning works classifications.
     */
    public static boolean hasAssignClassificationPermission() {
        return SecurityUtils.hasPermission("FDA_ASSIGN_CLASSIFICATION");
    }

    /**
     * @return {@code true} if user has permission to delete scenarios.
     */
    public static boolean hasDeleteScenarioPermission() {
        return SecurityUtils.hasPermission("FDA_DELETE_SCENARIO");
    }

    /**
     * @return {@code true} if user has permission to exclude usages from scenario.
     */
    public static boolean hasExcludeFromScenarioPermission() {
        return SecurityUtils.hasPermission("FDA_EXCLUDE_FROM_SCENARIO");
    }

    /**
     * @return {@code true} if user has permission for submitting scenarios for the approval.
     */
    public static boolean hasSubmitScenarioPermission() {
        return SecurityUtils.hasPermission("FDA_SUBMIT_SCENARIO");
    }

    /**
     * @return {@code true} if user has permission for approving scenarios.
     */
    public static boolean hasApproveScenarioPermission() {
        return SecurityUtils.hasPermission("FDA_APPROVE_SCENARIO");
    }

    /**
     * @return {@code true} if user has permission for rejecting scenarios.
     */
    public static boolean hasRejectScenarioPermission() {
        return SecurityUtils.hasPermission("FDA_REJECT_SCENARIO");
    }

    /**
     * @return {@code true} if user has permission for sending scenarios to LM.
     */
    public static boolean hasSendScenarioToLmPermission() {
        return SecurityUtils.hasPermission("FDA_DISTRIBUTE_SCENARIO");
    }

    /**
     * @return {@code true} if user has permission for reconciling rightsholders.
     */
    public static boolean hasReconcileRightsholdersPermission() {
        return SecurityUtils.hasPermission("FDA_RECONCILE_RIGHTSHOLDERS");
    }

    /**
     * @return {@code true} if user has permission for scenario refreshing.
     */
    public static boolean hasRefreshScenarioPermission() {
        return SecurityUtils.hasPermission("FDA_REFRESH_SCENARIO");
    }
}
