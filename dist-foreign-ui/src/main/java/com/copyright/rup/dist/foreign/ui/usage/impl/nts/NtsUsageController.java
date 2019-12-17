package com.copyright.rup.dist.foreign.ui.usage.impl.nts;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.PreServiceFeeFund;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.nts.INtsUsageController;
import com.copyright.rup.dist.foreign.ui.usage.impl.CommonUsageController;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of {@link INtsUsageController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/16/19
 *
 * @author Darya Baraukova
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class NtsUsageController extends CommonUsageController implements INtsUsageController {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Override
    public IStreamSource getExportUsagesStreamSource() {
        return getStreamSourceHandler().getCsvStreamSource(() -> "export_usage_",
            pos -> getReportService().writeNtsUsageCsvReport(
                    getUsageFilterController().getWidget().getAppliedFilter(), pos));
    }

    @Override
    public int loadNtsBatch(UsageBatch usageBatch) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Insert NTS batch. Started. UsageBatchName={}, UserName={}", usageBatch.getName(), userName);
        List<String> ntsUsageIds = getUsageBatchService().insertNtsBatch(usageBatch, userName);
        int insertedUsagesCount = CollectionUtils.size(ntsUsageIds);
        LOGGER.info("Insert NTS batch. Finished. UsageBatchName={}, UserName={}, UsagesCount={}",
                usageBatch.getName(), userName, insertedUsagesCount);
        getUsageBatchService().getAndSendForGettingRights(ntsUsageIds, usageBatch.getName());
        getUsageFilterController().getWidget().clearFilter();
        return insertedUsagesCount;
    }

    @Override
    public boolean fundPoolExists(String name) {
        return getFundPoolService().fundPoolNameExists(name);
    }


    @Override
    public List<PreServiceFeeFund> getPreServiceSeeFunds() {
        return getFundPoolService().getPreServiceFeeFunds();
    }

    @Override
    public List<PreServiceFeeFund> getPreServiceFeeFundsNotAttachedToScenario() {
        return getFundPoolService().getPreServiceFeeFundsNotAttachedToScenario();
    }

    @Override
    public void deletePreServiceFeeFund(PreServiceFeeFund fundPool) {
        getFundPoolService().deletePreServiceFeeFund(fundPool);
    }

    @Override
    public Scenario createNtsScenario(String scenarioName, Scenario.NtsFields ntsFields, String description) {
        Scenario scenario = getScenarioService().createNtsScenario(scenarioName, ntsFields, description,
            getUsageFilterController().getWidget().getAppliedFilter());
        getUsageFilterController().getWidget().clearFilter();
        return scenario;
    }

    @Override
    protected ICommonUsageWidget instantiateWidget() {
        return new NtsUsageWidget(this);
    }
}
