package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.ui.common.domain.FakeDataGenerator;
import com.copyright.rup.dist.foreign.ui.common.domain.UsageDetailDto;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Controller for {@link UsagesWidget}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/16/2017
 *
 * @author Mikita Hladkikh
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UsagesController extends CommonController<IUsagesWidget> implements IUsagesController {

    @Autowired
    private IUsagesFilterController filterController;

    @Override
    public IUsagesFilterWidget initUsagesFilterWidget() {
        return filterController.initWidget();
    }

    @Override
    public int getSize() {
        // TODO: refactor after implementing backend logic for getting usages
        return FakeDataGenerator.getUsageDetailDTOs().size();
    }

    @Override
    public List<UsageDetailDto> loadBeans(int startIndex, int count, Object[] sortPropertyIds, boolean... sortStates) {
        // TODO: refactor after implementing backend logic for getting usages
        return FakeDataGenerator.getUsageDetailDTOs();
    }

    @Override
    protected IUsagesWidget instantiateWidget() {
        return new UsagesWidget();
    }
}
