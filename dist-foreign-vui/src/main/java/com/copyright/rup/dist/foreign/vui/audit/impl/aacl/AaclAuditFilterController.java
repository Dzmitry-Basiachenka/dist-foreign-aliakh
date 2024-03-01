package com.copyright.rup.dist.foreign.vui.audit.impl.aacl;

import com.copyright.rup.dist.foreign.vui.audit.api.ICommonAuditFilterWidget;
import com.copyright.rup.dist.foreign.vui.audit.api.aacl.IAaclAuditFilterController;
import com.copyright.rup.dist.foreign.vui.audit.impl.CommonAuditFilterController;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link IAaclAuditFilterController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/16/2020
 *
 * @author Anton Azarenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AaclAuditFilterController extends CommonAuditFilterController implements IAaclAuditFilterController {

    private static final long serialVersionUID = 4274662577084969374L;

    @Override
    public List<Integer> getUsagePeriods() {
        return new ArrayList<>();
    }

    @Override
    protected ICommonAuditFilterWidget instantiateWidget() {
        return new AaclAuditFilterWidget(this);
    }
}
