package com.copyright.rup.dist.foreign.vui.vaadin.common.security;

import com.google.common.collect.Sets;
import com.vaadin.flow.server.VaadinSession;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * Encapsulates logic for security utility.
 * <p/>
 * Copyright (C) 2013 copyright.com
 * <p/>
 * Date: 10/7/13
 *
 * @author Siarhei Sabetski
 * @author Nikita Levyankov
 * @author Mikalai Bezmen
 * @author Aliaksandr Radkevich
 */
public final class SecurityUtils {

    private SecurityUtils() {
        throw new AssertionError("Constructor shouldn't be called directly");
    }

    /**
     * Gets current user name from security context.
     *
     * @return current user name.
     */
    public static String getUserName() {
        return getAuthentication().getName();
    }

    /**
     * Verifies that security context contains the permission that was specified as the parameter.
     *
     * @param permissionName the name of the permission.
     * @return true if user has permission with specified permission name.
     */
    public static boolean hasPermission(String permissionName) {
        return null != permissionName && getPermissions().contains(permissionName);
    }

    /**
     * Verifies that security context contains any of permissions that were specified as the parameter.
     *
     * @param permissionsNames the names of the permissions.
     * @return true if user has at least one permission with name from specified array.
     */
    public static boolean hasAnyPermission(String... permissionsNames) {
        return ArrayUtils.isNotEmpty(permissionsNames) &&
            !Collections.disjoint(getPermissions(), Sets.newHashSet(permissionsNames));
    }

    /**
     * @return the {@link Authentication} from {@link SecurityContext}
     * if it's not null, otherwise the {@link Authentication} from {@link VaadinSession}
     * will be returned.
     */
    static Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null == authentication) {
            authentication = ((SecurityContext) VaadinSession.getCurrent()
                .getSession().getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication();
        }
        return authentication;
    }

    /**
     * @return set of the permissions from given security context.
     */
    static Set<String> getPermissions() {
        Collection<? extends GrantedAuthority> authorities = getAuthentication().getAuthorities();
        if (null != authorities && !authorities.isEmpty()) {
            Set<String> permissions = Sets.newHashSetWithExpectedSize(authorities.size());
            for (GrantedAuthority authority : authorities) {
                permissions.add(authority.getAuthority());
            }
            return permissions;
        }
        return Collections.emptySet();
    }

}
