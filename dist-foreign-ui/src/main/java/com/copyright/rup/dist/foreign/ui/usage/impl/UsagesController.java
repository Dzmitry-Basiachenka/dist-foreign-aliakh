package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.ui.common.domain.FakeDataGenerator;
import com.copyright.rup.dist.foreign.ui.common.domain.UsageDto;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesWidget;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.time.LocalDate;
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
        return FakeDataGenerator.getUsageDtos().size();
    }

    @Override
    public List<UsageDto> loadBeans(int startIndex, int count, Object[] sortPropertyIds, boolean... sortStates) {
        // TODO: refactor after implementing backend logic for getting usages
        return FakeDataGenerator.getUsageDtos();
    }

    @Override
    protected IUsagesWidget instantiateWidget() {
        return new UsagesWidget();
    }

    @Override
    public InputStream getStream() {
        // TODO {mhladkikh} implement method
        return null;
    }

    @Override
    public String getFileName() {
        LocalDate now = LocalDate.now();
        return VaadinUtils.encodeAndBuildFileName(
            String.format("export_usage_%s_%s_%s", now.getMonthValue(), now.getDayOfMonth(), now.getYear()), "csv");
    }
}
