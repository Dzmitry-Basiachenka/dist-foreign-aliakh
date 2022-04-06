package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.usage;

import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;
import com.vaadin.ui.MenuBar;

/**
 * Mediator for the ACL usage widget.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/07/2022
 *
 * @author Aliaksandr Liakh
 */
public class AclUsageMediator implements IMediator {

    private MenuBar aclUsageBatchMenuBar;

    @Override
    public void applyPermissions() {
        aclUsageBatchMenuBar.setVisible(ForeignSecurityUtils.hasSpecialistPermission());
    }

    public void setAclUsageBatchMenuBar(MenuBar aclUsageBatchMenuBar) {
        this.aclUsageBatchMenuBar = aclUsageBatchMenuBar;
    }
}
