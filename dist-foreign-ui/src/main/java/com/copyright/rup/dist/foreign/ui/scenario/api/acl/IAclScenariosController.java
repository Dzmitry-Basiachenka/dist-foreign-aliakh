package com.copyright.rup.dist.foreign.ui.scenario.api.acl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetailDto;
import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.domain.AclPublicationType;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDto;
import com.copyright.rup.dist.foreign.domain.AclUsageBatch;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.vaadin.widget.api.IController;

import java.util.List;
import java.util.Set;

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
     * Gets {@link AclScenarioDto} by scenario id.
     *
     * @param scenarioId scenario id
     * @return instance of {@link AclScenarioDto}
     */
    AclScenarioDto getAclScenarioWithAmountsAndLastAction(String scenarioId);

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
     * Gets list of all {@link DetailLicenseeClass}es.
     *
     * @return list of detail licensee classes.
     */
    List<DetailLicenseeClass> getDetailLicenseeClasses();

    /**
     * Gets list of all {@link AggregateLicenseeClass}es.
     *
     * @return list of detail licensee classes.
     */
    List<AggregateLicenseeClass> getAggregateLicenseeClasses();

    /**
     * Creates ACL scenario.
     *
     * @param aclScenario instance of {@link AclScenario}
     */
    void createAclScenario(AclScenario aclScenario);

    /**
     * @return an {@link IAclScenarioController} instance.
     */
    IAclScenarioController getAclScenarioController();

    /**
     * Verifies ACL batch is valid for creation of ACL scenario.
     *
     * @param batchId            ACL batch id
     * @param grantSetId         ACL grant set  id
     * @param periodPriors       list of period
     * @param distributionPeriod distribution period
     * @return {@code true} - if ACL batch is valid, {@code false} - otherwise
     */
    boolean isValidUsageBatch(String batchId, String grantSetId, Integer distributionPeriod,
                              List<Integer> periodPriors);

    /**
     * Gets list of all ACL publication types.
     *
     * @return list of {@link PublicationType}
     */
    List<PublicationType> getPublicationTypes();

    /**
     * Gets list of all historical ACL publication types.
     *
     * @return list of {@link AclPublicationType}
     */
    List<AclPublicationType> getAclHistoricalPublicationTypes();

    /**
     * Gets list of {@link UsageAge}s.
     *
     * @return list of usage age wights
     */
    List<UsageAge> getUsageAgeWeights();

    /**
     * Insert new ACL publication type.
     *
     * @param publicationType instance of {@link AclPublicationType}
     */
    void insertAclHistoricalPublicationType(AclPublicationType publicationType);

    /**
     * Gets fund pool details with amounts that can't be distributed for selected batch and fund pool.
     *
     * @param batchId    usage batch id
     * @param fundPoolId fund pool id
     * @param grantSetId grant set id
     * @param mapping    {@link DetailLicenseeClass} to {@link AggregateLicenseeClass} mapping
     * @return list of {@link AclFundPoolDetailDto}es
     */
    Set<AclFundPoolDetailDto> getFundPoolDetailsNotToBeDistributed(String batchId, String fundPoolId,
                                                                   String grantSetId,
                                                                   List<DetailLicenseeClass> mapping);

    /**
     * Gets ACl scenario by unique identifier.
     *
     * @param scenarioId scenario uid
     * @return instance of {@link AclScenario}s
     */
    AclScenario getScenarioById(String scenarioId);

    /**
     * Gets list of {@link UsageAge}es by scenario id.
     *
     * @param scenarioId scenario id
     * @return list of {@link UsageAge}es
     */
    List<UsageAge> getUsageAgeWeightsByScenarioId(String scenarioId);

    /**
     * Gets list of {@link AclPublicationType}es by scenario id.
     *
     * @param scenarioId scenario id
     * @return list of {@link AclPublicationType}es
     */
    List<AclPublicationType> getAclPublicationTypesByScenarioId(String scenarioId);

    /**
     * Gets list of {@link DetailLicenseeClass}es by scenario id.
     *
     * @param scenarioId scenario id
     * @return list of {@link DetailLicenseeClass}es
     */
    List<DetailLicenseeClass> getDetailLicenseeClassesByScenarioId(String scenarioId);

    /**
     * Handles click on 'Delete' button.
     */
    void onDeleteButtonClicked();

    /**
     * @return instance of {@link IStreamSource} for export Summary of Work Shares by Aggregate Licensee Class Report.
     */
    IStreamSource getExportAclSummaryOfWorkSharesByAggLcStreamSource();
}
