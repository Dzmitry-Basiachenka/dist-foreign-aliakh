package com.copyright.rup.dist.foreign.ui.main.security;

import static org.junit.Assert.assertEquals;

import com.google.common.collect.Sets;

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

    private Set<String> permissions;

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
        Object[] viewOnlyRole = {Sets.newHashSet("FDA_ACCESS_APPLICATION")};
        Object[] roleWithoutPermissions = {Collections.emptySet()};
        return Arrays.asList(viewOnlyRole, roleWithoutPermissions);
    }

    @Before
    public void setUp() {
        MockSecurityContext context = new MockSecurityContext();
        context.setGrantedAuthorities(permissions);
        SecurityContextHolder.setContext(context);
    }

    @Test
    public void testUserRolePermissions() {
        assertEquals(permissions.contains("FDA_ACCESS_APPLICATION"), ForeignSecurityUtils.hasAccessPermission());
    }

    private static class MockSecurityContext implements SecurityContext {

        private static final String USER_NAME = "User@copyright.com";
        private Set<GrantedAuthority> grantedAuthorities = Sets.newHashSet();

        /**
         * Sets authorities.
         *
         * @param userPermissions the Set of permissions.
         */
        public void setGrantedAuthorities(Set<String> userPermissions) {
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
