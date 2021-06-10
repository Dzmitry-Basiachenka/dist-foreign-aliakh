package com.copyright.rup.dist.foreign.ui.main.security;

import static org.junit.Assert.assertEquals;

import com.google.common.collect.Sets;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * Verifies that each role has unique set of permissions.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/8/2017
 *
 * @author Mikita Hladkikh
 */
@RunWith(Parameterized.class)
public final class ForeignSecurityUtilsTest {

    private static final String FDA_ACCESS_APPLICATION = "FDA_ACCESS_APPLICATION";
    private static final String FDA_VIEW_SCENARIO = "FDA_VIEW_SCENARIO";
    private static final String FDA_VIEW_ONLY_PERMISSION = "FDA_VIEW_ONLY_PERMISSION";
    private final Set<String> permissions;

    /**
     * Constructor.
     *
     * @param permissions the Set of permissions.
     */
    public ForeignSecurityUtilsTest(Set<String> permissions) {
        this.permissions = permissions;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> setUpUserPermissions() {
        Object[] viewOnlyRole = {Sets.newHashSet(FDA_ACCESS_APPLICATION, FDA_VIEW_SCENARIO, FDA_VIEW_ONLY_PERMISSION)};
        Object[] distributionManagerRole =
            {Sets.newHashSet(FDA_ACCESS_APPLICATION, FDA_VIEW_SCENARIO, "FDA_MANAGER_PERMISSION")};
        Object[] distributionSpecialistRole = {Sets.newHashSet(
            FDA_ACCESS_APPLICATION, "FDA_DELETE_USAGE", "FDA_LOAD_USAGE", "FDA_LOAD_FUND_POOL",
            "FDA_VIEW_SCENARIO", "FDA_DELETE_FUND_POOL", "FDA_LOAD_RESEARCHED_USAGE", "FDA_CREATE_DELETE_FUND",
            "FDA_ASSIGN_CLASSIFICATION", "FDA_DELETE_SCENARIO", "FDA_EXCLUDE_FROM_SCENARIO",
            "FDA_SEND_FOR_WORK_RESEARCH", "FDA_SEND_FOR_CLASSIFICATION", "FDA_LOAD_CLASSIFIED_USAGE",
            "FDA_SPECIALIST_PERMISSION")};
        Object[] distributionResearchRole = {Collections.singleton("FDA_RESEARCHER_PERMISSION")};
        Object[] roleWithoutPermissions = {Collections.emptySet()};
        return Arrays.asList(
            viewOnlyRole,
            distributionManagerRole,
            distributionSpecialistRole,
            distributionResearchRole,
            roleWithoutPermissions);
    }

    @Before
    public void setUp() {
        MockSecurityContext context = new MockSecurityContext();
        context.setGrantedAuthorities(permissions);
        SecurityContextHolder.setContext(context);
    }

    @After
    public void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void testUserRolePermissions() {
        assertEquals(permissions.contains(FDA_ACCESS_APPLICATION),
            ForeignSecurityUtils.hasAccessPermission());
        assertEquals(permissions.contains("FDA_DELETE_USAGE"),
            ForeignSecurityUtils.hasDeleteUsagePermission());
        assertEquals(permissions.contains("FDA_LOAD_USAGE"),
            ForeignSecurityUtils.hasLoadUsagePermission());
        assertEquals(permissions.contains("FDA_LOAD_FUND_POOL"),
            ForeignSecurityUtils.hasLoadFundPoolPermission());
        assertEquals(permissions.contains("FDA_DELETE_FUND_POOL"),
            ForeignSecurityUtils.hasDeleteFundPoolPermission());
        assertEquals(permissions.contains("FDA_LOAD_RESEARCHED_USAGE"),
            ForeignSecurityUtils.hasLoadResearchedUsagePermission());
        assertEquals(permissions.contains("FDA_CREATE_DELETE_FUND"),
            ForeignSecurityUtils.hasCreateDeleteFundPermission());
        assertEquals(permissions.contains("FDA_ASSIGN_CLASSIFICATION"),
            ForeignSecurityUtils.hasLoadResearchedUsagePermission());
        assertEquals(permissions.contains("FDA_CREATE_EDIT_SCENARIO"),
            ForeignSecurityUtils.hasCreateEditScenarioPermission());
        assertEquals(permissions.contains("FDA_VIEW_SCENARIO"),
            ForeignSecurityUtils.hasViewScenarioPermission());
        assertEquals(permissions.contains("FDA_DELETE_SCENARIO"),
            ForeignSecurityUtils.hasDeleteScenarioPermission());
        assertEquals(permissions.contains("FDA_EXCLUDE_FROM_SCENARIO"),
            ForeignSecurityUtils.hasExcludeFromScenarioPermission());
        assertEquals(permissions.contains("FDA_SEND_FOR_WORK_RESEARCH"),
            ForeignSecurityUtils.hasSendForWorkResearchPermission());
        assertEquals(permissions.contains("FDA_SEND_FOR_CLASSIFICATION"),
            ForeignSecurityUtils.hasSendForClassificationPermission());
        assertEquals(permissions.contains("FDA_LOAD_CLASSIFIED_USAGE"),
            ForeignSecurityUtils.hasLoadClassifiedUsagePermission());
        assertEquals(permissions.contains("FDA_UPDATE_RIGHTSHOLDER"),
            ForeignSecurityUtils.hasUpdateRightsholderPermission());
        assertEquals(permissions.contains("FDA_VIEW_ONLY_PERMISSION"),
            ForeignSecurityUtils.hasViewOnlyPermission());
        assertEquals(permissions.contains("FDA_MANAGER_PERMISSION"),
            ForeignSecurityUtils.hasManagerPermission());
        assertEquals(permissions.contains("FDA_SPECIALIST_PERMISSION"),
            ForeignSecurityUtils.hasSpecialistPermission());
        assertEquals(permissions.contains("FDA_RESEARCHER_PERMISSION"),
            ForeignSecurityUtils.hasResearcherPermission());
    }

    private static class MockSecurityContext implements SecurityContext {

        private static final String USER_NAME = "User@copyright.com";
        private final Set<GrantedAuthority> grantedAuthorities = Sets.newHashSet();

        /**
         * Sets authorities.
         *
         * @param userPermissions the Set of permissions.
         */
        void setGrantedAuthorities(Set<String> userPermissions) {
            grantedAuthorities.clear();
            for (String permission : userPermissions) {
                grantedAuthorities.add(new SimpleGrantedAuthority(permission));
            }
        }

        @Override
        public Authentication getAuthentication() {
            return new UsernamePasswordAuthenticationToken(USER_NAME, null, grantedAuthorities);
        }

        @Override
        public void setAuthentication(Authentication authentication) {
            //stub implementation
        }
    }
}
