package com.copyright.rup.dist.foreign.ui.usage.impl.aclci;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.aclci.IAclciUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.aclci.IAclciUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.impl.CommonUsageController;
import com.vaadin.data.provider.QuerySortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link IAclciUsageController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 11/21/2022
 *
 * @author Aliaksanr Liakh
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AclciUsageController extends CommonUsageController implements IAclciUsageController {

    @Autowired
    private IAclciUsageFilterController aclciUsageFilterController;

    @Override
    public int getBeansCount() {
        return 0; //TODO: implement
    }

    @Override
    public List<UsageDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders) {
        return new ArrayList<>(); //TODO: implement
    }

    @Override
    public ICommonUsageFilterController getUsageFilterController() {
        return aclciUsageFilterController;
    }

    @Override
    public boolean isValidFilteredUsageStatus(UsageStatusEnum status) {
        return false; //TODO: implement
    }

    @Override
    public IStreamSource getExportUsagesStreamSource() {
        return null; //TODO: implement
    }

    @Override
    public boolean isBatchProcessingCompleted(String batchId) {
        return false; //TODO: implement
    }

    @Override
    public void deleteUsageBatch(UsageBatch usageBatch) {
        //TODO: implement
    }

    @Override
    protected ICommonUsageWidget instantiateWidget() {
        return new AclciUsageWidget();
    }
}
