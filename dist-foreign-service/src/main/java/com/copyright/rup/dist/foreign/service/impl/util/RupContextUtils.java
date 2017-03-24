package com.copyright.rup.dist.foreign.service.impl.util;

import com.copyright.rup.common.context.RupContextHolder;
import com.copyright.rup.common.user.IRupUser;
import com.copyright.rup.dist.common.domain.StoredEntity;

/**
 * Encapsulates logic for rup context.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/13/17
 *
 * @author Aliaksei Pchelnikau
 */
public final class RupContextUtils {

    private RupContextUtils() {
        throw new AssertionError("Constructor shouldn't be called directly");
    }

    /**
     * Gets active user name from security context.
     *
     * @return current user name.
     */
    public static String getUserName() {
        IRupUser activeUser = RupContextHolder.getContext().getActiveUser();
        return IRupUser.NULL != activeUser ? activeUser.getUsername() : StoredEntity.DEFAULT_USER;
    }
}
