package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;
import com.vaadin.ui.Button;

/**
 * Mediator for the view ACL grant set widget.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/22/2022
 *
 * @author Aliaksandr Liakh
 */
class ViewAclGrantSetMediator implements IMediator {

    private Button deleteButton;

    @Override
    public void applyPermissions() {
        deleteButton.setVisible(ForeignSecurityUtils.hasSpecialistPermission());
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }
}
