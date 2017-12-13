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
     * @return true if user has permission for submitting scenarios for the approval.
     */
    public static boolean hasSubmitScenarioPermission() {
        return SecurityUtils.hasPermission("FDA_SUBMIT_SCENARIO");
    }

    /**
     * @return true if user has permission for approving scenarios.
     */
    public static boolean hasApproveScenarioPermission() {
        return SecurityUtils.hasPermission("FDA_APPROVE_SCENARIO");
    }

    /**
     * @return true if user has permission for rejecting scenarios.
     */
    public static boolean hasRejectScenarioPermission() {
        return SecurityUtils.hasPermission("FDA_REJECT_SCENARIO");
    }
}
