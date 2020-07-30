package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.integration.telesales.api.ITelesalesService;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.ISalUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageController;
import com.copyright.rup.dist.foreign.ui.usage.impl.CommonUsageController;

import com.vaadin.data.provider.QuerySortOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link ISalUsageController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/29/20
 *
 * @author Ihar Suvorau
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SalUsageController extends CommonUsageController implements ISalUsageController {

    @Autowired
    private ISalUsageFilterController salUsageFilterController;
    @Autowired
    private ITelesalesService telesalesService;

    @Override
    public String getLicenseeName(Long licenseeAccountNumber) {
        return telesalesService.getLicenseeName(licenseeAccountNumber);
    }

    @Override
    public int getBeansCount() {
        //TODO: use service logic here
        return 0;
    }

    @Override
    public List<UsageDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders) {
        //TODO: use service logic here
        return new ArrayList<>();
    }

    @Override
    public void deleteUsageBatch(UsageBatch usageBatch) {
        //TODO: implement in scope of corresponding story
    }

    @Override
    public IStreamSource getExportUsagesStreamSource() {
        //TODO: implement in scope of corresponding story
        return null;
    }

    @Override
    public boolean isValidFilteredUsageStatus(UsageStatusEnum status) {
        //TODO: implement in scope of corresponding story
        return false;
    }

    @Override
    public ICommonUsageFilterController getUsageFilterController() {
        return salUsageFilterController;
    }

    @Override
    protected ICommonUsageWidget instantiateWidget() {
        return new SalUsageWidget(this);
    }
}
