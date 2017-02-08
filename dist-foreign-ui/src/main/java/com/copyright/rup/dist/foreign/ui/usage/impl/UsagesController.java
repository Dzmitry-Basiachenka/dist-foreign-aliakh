package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.repository.api.Sort;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
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
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 01/16/2017
 *
 * @author Mikita Hladkikh
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UsagesController extends CommonController<IUsagesWidget> implements IUsagesController {

    @Autowired
    private IUsageService usageService;

    @Autowired
    private IUsagesFilterController filterController;

    @Override
    public IUsagesFilterWidget initUsagesFilterWidget() {
        IUsagesFilterWidget result = filterController.initWidget();
        result.addListener(FilterChangedEvent.class, this, IUsagesController.ON_FILTER_CHANGED);
        return result;
    }

    @Override
    public void onFilterChanged(FilterChangedEvent event) {
        getWidget().refresh();
    }

    @Override
    public int getSize() {
        return usageService.getUsagesCount(getFilter());
    }

    @Override
    public List<UsageDto> loadBeans(int startIndex, int count, Object[] sortPropertyIds, boolean... sortStates) {
        return usageService.getUsages(getFilter(), new Pageable(startIndex, count),
            Sort.create(sortPropertyIds, sortStates));
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

    private UsageFilter getFilter() {
        return filterController.getWidget().getFilter();
    }
}
