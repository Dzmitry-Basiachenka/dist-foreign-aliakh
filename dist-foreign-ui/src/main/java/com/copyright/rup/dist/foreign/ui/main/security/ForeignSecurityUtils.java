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

    /**
     * Permission to access FDA application.
     */
    private static final String FDA_ACCESS_APPLICATION = "FDA_ACCESS_APPLICATION";

    /**
     * Permission to access load usages.
     */
    private static final String FDA_LOAD_USAGE = "FDA_LOAD_USAGE";

    /**
     * Permission to access delete usages.
     */
    private static final String FDA_DELETE_USAGE = "FDA_DELETE_USAGE";

    /**
     * Permission to access create and edit scenarios.
     */
    private static final String FDA_CREATE_EDIT_SCENARIO = "FDA_CREATE_EDIT_SCENARIO";

    private ForeignSecurityUtils() {
        throw new AssertionError("Constructor shouldn't be called directly");
    }

    /**
     * @return {@code true} if user has permission to access application.
     */
    public static boolean hasAccessPermission() {
        return SecurityUtils.hasPermission(FDA_ACCESS_APPLICATION);
    }

    /**
     * @return {@code true} if user has permission to load usages.
     */
    public static boolean hasLoadUsagePermission() {
        return SecurityUtils.hasPermission(FDA_LOAD_USAGE);
    }

    /**
     * @return {@code true} if user has permission to delete usages.
     */
    public static boolean hasDeleteUsagePermission() {
        return SecurityUtils.hasPermission(FDA_DELETE_USAGE);
    }

    /**
     * @return {@code true} if user has permission to create and edit scenarios.
     */
    public static boolean hasCreateEditScenarioPermission() {
        return SecurityUtils.hasPermission(FDA_CREATE_EDIT_SCENARIO);
    }
}
