package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline.value;

import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineValueFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineValueFilterWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Implementation of {@link IUdmBaselineValueFilterController}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/23/2021
 *
 * @author Anton Azarenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UdmBaselineValueFilterController extends CommonController<IUdmBaselineValueFilterWidget>
    implements IUdmBaselineValueFilterController {

    @Override
    protected IUdmBaselineValueFilterWidget instantiateWidget() {
        return new UdmBaselineValueFilterWidget(this);
    }

    @Override
    public List<Integer> getPeriods() {
        //TODO will implement later
        return Collections.emptyList();
    }
}
