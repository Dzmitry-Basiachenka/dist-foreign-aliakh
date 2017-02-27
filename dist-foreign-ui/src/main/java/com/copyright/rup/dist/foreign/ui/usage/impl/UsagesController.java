package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.repository.api.Sort;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesWidget;
import com.copyright.rup.vaadin.security.SecurityUtils;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Controller for {@link UsagesWidget}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 01/16/2017
 *
 * @author Mikita Hladkikh
 * @author Aliaksandr Radkevich
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UsagesController extends CommonController<IUsagesWidget> implements IUsagesController {

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Autowired
    private IUsageBatchService usageBatchService;

    @Autowired
    private IUsageService usageService;

    @Autowired
    private IUsagesFilterController filterController;

    @Autowired
    private IPrmIntegrationService prmIntegrationService;

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
        return usageService.getUsagesCount(filterController.getWidget().getAppliedFilter());
    }

    @Override
    public List<UsageDto> loadBeans(int startIndex, int count, Object[] sortPropertyIds, boolean... sortStates) {
        return usageService.getUsages(filterController.getWidget().getAppliedFilter(), new Pageable(startIndex, count),
            Sort.create(sortPropertyIds, sortStates));
    }

    @Override
    public InputStream getStream() {
        try {
            PipedOutputStream outputStream = new PipedOutputStream();
            PipedInputStream pipedInputStream = new PipedInputStream(outputStream);
            executorService.execute(
                () -> usageService.writeUsageCsvReport(filterController.getWidget().getAppliedFilter(), outputStream));
            return pipedInputStream;
        } catch (IOException e) {
            throw new RupRuntimeException(e);
        }
    }

    @Override
    public String getFileName() {
        LocalDate now = LocalDate.now();
        return VaadinUtils.encodeAndBuildFileName(
            String.format("export_usage_%s_%s_%s", now.getMonthValue(), now.getDayOfMonth(), now.getYear()), "csv");
    }

    @Override
    public String getRroName(Long rroAccountNumber) {
        return prmIntegrationService.getRighstholderName(rroAccountNumber);
    }

    @Override
    public boolean usageBatchExists(String name) {
        return usageBatchService.usageBatchExists(name);
    }

    @Override
    public int loadUsageBatch(UsageBatch usageBatch, List<Usage> usages, String userName) {
        filterController.getWidget().clearFilter();
        return usageBatchService.insertUsages(usageBatch, usages, userName);
    }

    @Override
    protected IUsagesWidget instantiateWidget() {
        return new UsagesWidget();
    }

    @Override
    public List<String> getScenariosNamesAssociatedWithUsageBatch(String batchId) {
        // TODO: implement method after introducing scenarios
        return Collections.emptyList();
    }

    @Override
    public List<UsageBatch> getUsageBatches() {
        return usageBatchService.getUsageBatches();
    }

    @Override
    public void deleteUsageBatch(String batchId) {
        usageBatchService.deleteUsageBatch(batchId, SecurityUtils.getUserName());
        filterController.getWidget().clearFilter();
    }
}
