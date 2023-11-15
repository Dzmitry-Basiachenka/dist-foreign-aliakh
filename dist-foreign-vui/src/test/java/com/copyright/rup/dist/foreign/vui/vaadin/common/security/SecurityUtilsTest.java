package com.copyright.rup.dist.foreign.vui.vaadin.common.security;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WrappedSession;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashSet;
import java.util.Set;

/**
 * Verifies {@link SecurityUtils}.
 * <p/>
 * Copyright (C) 2014 copyright.com
 * <p/>
 * Date: 05/23/14
 *
 * @author Mikalai Bezmen
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({VaadinSession.class, SecurityContextHolder.class})
public class SecurityUtilsTest {

    private static final String EXISTING_PERMISSION_NAME = "TestPermission";
    private static final String NON_EXISTING_PERMISSION_NAME = EXISTING_PERMISSION_NAME + '1';
    private static final String USER_NAME = "testUser";

    @Test
    public void testGetUserName() {
        Set<String> expectedPermissions = Set.of(EXISTING_PERMISSION_NAME);
        MockSecurityContext context = new MockSecurityContext(expectedPermissions);
        SecurityContextHolder.setContext(context);
        assertEquals(USER_NAME, SecurityUtils.getUserName());
    }

    @Test
    public void testHasPermissionWithNullPermissionName() {
        assertFalse(SecurityUtils.hasPermission(null));
    }

    @Test
    public void testHasPermission() {
        Set<String> expectedPermissions = Set.of(EXISTING_PERMISSION_NAME);
        MockSecurityContext context = new MockSecurityContext(expectedPermissions);
        SecurityContextHolder.setContext(context);
        assertFalse(SecurityUtils.hasPermission(NON_EXISTING_PERMISSION_NAME));
        assertTrue(SecurityUtils.hasPermission(EXISTING_PERMISSION_NAME));
    }

    @Test
    public void testHasAnyPermissionEmptyParameter() {
        assertFalse(SecurityUtils.hasAnyPermission());
    }

    @Test
    public void testHasAnyPermission() {
        Set<String> expectedPermissions = Set.of(EXISTING_PERMISSION_NAME);
        MockSecurityContext context = new MockSecurityContext(expectedPermissions);
        SecurityContextHolder.setContext(context);
        assertTrue(SecurityUtils.hasAnyPermission(EXISTING_PERMISSION_NAME, NON_EXISTING_PERMISSION_NAME));
        assertTrue(SecurityUtils.hasAnyPermission(EXISTING_PERMISSION_NAME));
        assertFalse(SecurityUtils.hasAnyPermission(NON_EXISTING_PERMISSION_NAME));
    }

    @Test
    public void testGetEmptyPermissions() {
        MockSecurityContext context = new MockSecurityContext(Set.of());
        SecurityContextHolder.setContext(context);
        assertTrue(CollectionUtils.isEmpty(SecurityUtils.getPermissions()));
    }

    @Test
    public void testGetPermissions() {
        Set<String> expectedPermissions = Set.of(EXISTING_PERMISSION_NAME);
        MockSecurityContext context = new MockSecurityContext(expectedPermissions);
        SecurityContextHolder.setContext(context);
        assertTrue(CollectionUtils.isNotEmpty(SecurityUtils.getPermissions()));
    }

    @Test
    public void testGetAuthentication() {
        mockStatic(VaadinSession.class);
        mockStatic(SecurityContextHolder.class);
        VaadinSession vaadinSession = createMock(VaadinSession.class);
        WrappedSession wrappedSession = createMock(WrappedSession.class);
        SecurityContext securityContext = createMock(SecurityContext.class);
        Authentication authentication = new UsernamePasswordAuthenticationToken(USER_NAME, null, null);
        expect(SecurityContextHolder.getContext()).andReturn(securityContext).once();
        expect(securityContext.getAuthentication()).andReturn(null).once();
        expect(VaadinSession.getCurrent()).andReturn(vaadinSession).once();
        expect(vaadinSession.getSession()).andReturn(wrappedSession).once();
        expect(wrappedSession.getAttribute("SPRING_SECURITY_CONTEXT"))
            .andReturn(securityContext).once();
        expect(securityContext.getAuthentication()).andReturn(authentication).once();
        replay(SecurityContextHolder.class, VaadinSession.class, vaadinSession, wrappedSession, securityContext);
        Authentication actualResult = SecurityUtils.getAuthentication();
        assertEquals(authentication, actualResult);
        verify(SecurityContextHolder.class, VaadinSession.class, vaadinSession, wrappedSession, securityContext);
    }

    private static class MockSecurityContext implements SecurityContext {
        private final Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        /**
         * Constructor.
         *
         * @param grantedAuthorities permissions.
         */
        MockSecurityContext(Set<String> grantedAuthorities) {
            this.grantedAuthorities.clear();
            for (String permission : grantedAuthorities) {
                this.grantedAuthorities.add(new SimpleGrantedAuthority(permission));
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
