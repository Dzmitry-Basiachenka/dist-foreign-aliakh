package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBatchService;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageFilterWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Controller for filtering UDM usages.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/04/2021
 *
 * @author Dzmitry Basiachenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UdmUsageFilterController extends CommonController<IUdmUsageFilterWidget>
    implements IUdmUsageFilterController {

    @Autowired
    private IUdmBatchService udmBatchService;

    @Override
    public List<Integer> getPeriods() {
        return udmBatchService.getPeriods();
    }

    @Override
    public List<UdmBatch> getUdmBatches() {
        return udmBatchService.getUdmBatches();
    }

    @Override
    protected UdmUsageFilterWidget instantiateWidget() {
        return new UdmUsageFilterWidget(this);
    }
}
