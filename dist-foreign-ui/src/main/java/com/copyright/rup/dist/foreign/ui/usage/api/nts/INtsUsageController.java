package com.copyright.rup.dist.foreign.ui.usage.api.nts;

import com.copyright.rup.dist.foreign.domain.PreServiceFeeFund;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageController;

import java.util.List;

/**
 * Interface for NTS usages controller.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/16/19
 *
 * @author Darya Baraukova
 */
//TODO add NTS specific methods from CommonUsageController
public interface INtsUsageController extends ICommonUsageController {

    /**
     * Inserts NTS batch and assigns eligible for NTS usages to it based on fund pool information.
     *
     * @param usageBatch {@link UsageBatch} to insert
     * @return count of inserted usages
     */
    int loadNtsBatch(UsageBatch usageBatch);

    /**
     * Checks whether Fund Pool with provided name exists in databse.
     *
     * @param name fund pool name
     * @return {@code true} - if Fund Pool exists, {@code false} - otherwise
     */
    boolean fundPoolExists(String name);

    /**
     * Gets all {@link PreServiceFeeFund}s.
     *
     * @return list of {@link PreServiceFeeFund}s
     */
    List<PreServiceFeeFund> getPreServiceSeeFunds();

    /**
     * Gets {@link PreServiceFeeFund}s not attached to scenario.
     *
     * @return list of {@link PreServiceFeeFund}s
     */
    List<PreServiceFeeFund> getPreServiceFeeFundsNotAttachedToScenario();

    /**
     * Deletes Pre-Service fee fund.
     *
     * @param fundPool {@link PreServiceFeeFund} to delete
     */
    void deletePreServiceFeeFund(PreServiceFeeFund fundPool);

    /**
     * Creates NTS {@link Scenario} by entered scenario name, rightholder's minimum amount and description.
     *
     * @param scenarioName name of scenario
     * @param ntsFields    NTS scenario specific fields
     * @param description  description for creating scenario
     * @return created scenario
     */
    Scenario createNtsScenario(String scenarioName, Scenario.NtsFields ntsFields, String description);
}
