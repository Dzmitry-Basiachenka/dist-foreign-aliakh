package com.copyright.rup.dist.foreign.ui.scenario.api.acl;

import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclUsageBatch;
import com.copyright.rup.vaadin.widget.api.IController;

import java.util.List;

/**
 * Interface for ACL scenarios controller.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/22/2022
 *
 * @author Dzmitry Basiachenka
 */
public interface IAclScenariosController extends IController<IAclScenariosWidget> {

    /**
     * Gets {@link AclScenario} with calculated amounts and last audit action.
     *
     * @param scenario selected {@link AclScenario}
     * @return scenario {@link AclScenario}
     */
    AclScenario getScenarioWithAmountsAndLastAction(AclScenario scenario);

    /**
     * @return HTML filter representation for selected scenario.
     */
    String getCriteriaHtmlRepresentation();

    /**
     * @return list of all {@link AclScenario}s.
     */
    List<AclScenario> getScenarios();

    /**
     * Checks whether {@link AclScenario} with specified name already exists in database.
     *
     * @param scenarioName name of {@link AclScenario} to check
     * @return {@code true} if {@link AclScenario} with specified name already exists in database,
     * {@code false} - if doesn't
     */
    boolean aclScenarioExists(String scenarioName);

    /**
     * Gets list of all ACl usage batches by period.
     *
     * @param period       period end date
     * @param editableFlag editable flag
     * @return list of {@link AclUsageBatch}s
     */
    List<AclUsageBatch> getUsageBatchesByPeriod(Integer period, boolean editableFlag);

    /**
     * Gets list of all ACl fund pools by license type and period.
     *
     * @param licenseType license type
     * @param period      period end date
     * @return list of {@link AclFundPool}s
     */
    List<AclFundPool> getFundPoolsByLicenseTypeAndPeriod(String licenseType, Integer period);

    /**
     * Gets list of all ACl grant sets by license type and period.
     *
     * @param licenseType  license type
     * @param period       period end date
     * @param editableFlag editable flag
     * @return list of {@link AclGrantSet}s
     */
    List<AclGrantSet> getGrantSetsByLicenseTypeAndPeriod(String licenseType, Integer period, boolean editableFlag);

    /**
     * Gets list of all ACl usage batch periods.
     *
     * @return list of periods
     */
    List<Integer> getAllPeriods();

    /**
     * Creates ACL scenario.
     *
     * @param aclScenario instance of {@link AclScenario}
     */
    void createAclScenario(AclScenario aclScenario);
}
