package com.copyright.rup.dist.foreign.ui.usage.api.acl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.AclUsageBatch;
import com.copyright.rup.dist.foreign.domain.AclUsageDto;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.vaadin.widget.api.IController;

import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.util.ReflectTools;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * Interface for ACL usage controller.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/30/2022
 *
 * @author Dzmitry Basiachenka
 */
public interface IAclUsageController extends IController<IAclUsageWidget> {

    /**
     * {@link #onFilterChanged(FilterChangedEvent)}.
     */
    Method ON_FILTER_CHANGED =
        ReflectTools.findMethod(IAclUsageController.class, "onFilterChanged", FilterChangedEvent.class);

    /**
     * Handles changes of filter.
     *
     * @param event event
     */
    void onFilterChanged(FilterChangedEvent event);

    /**
     * @return number of items.
     */
    int getBeansCount();

    /**
     * Loads specified number of beans from the storage with given start index.
     *
     * @param startIndex start index
     * @param count      items count to load
     * @param sortOrders sort orders
     * @return list of items to be displayed on UI
     */
    List<AclUsageDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders);

    /**
     * Gets list of all UDM usage periods.
     *
     * @return list of periods
     */
    List<Integer> getAllPeriods();

    /**
     * Checks whether ACL usage batch with provided name exists.
     *
     * @param usageBatchName name of the {@link AclUsageBatch}
     * @return {@code true} if ACL usage batch with provided name exists, otherwise {@code false}
     */
    boolean isAclUsageBatchExist(String usageBatchName);

    /**
     * Inserts ACL usage batch with populated ACL usages.
     *
     * @param usageBatch instance of {@link AclUsageBatch}
     * @return count of inserted ACL usages
     */
    int insertAclUsageBatch(AclUsageBatch usageBatch);

    /**
     * Initializes {@link IAclUsageFilterWidget}.
     *
     * @return initialized {@link IAclUsageFilterWidget}
     */
    IAclUsageFilterWidget initAclUsageFilterWidget();

    /**
     * @return instance of {@link IStreamSource} for export.
     */
    IStreamSource getExportAclUsagesStreamSource();

    /**
     * Gets list of all {@link PublicationType}s.
     *
     * @return list of {@link PublicationType}s
     */
    List<PublicationType> getPublicationTypes();

    /**
     * Gets list of all {@link DetailLicenseeClass}es.
     *
     * @return list of detail licensee classes.
     */
    List<DetailLicenseeClass> getDetailLicenseeClasses();

    /**
     * Updates ACL usages.
     *
     * @param aclUsageDtos set of {@link AclUsageDto}s to update
     */
    void updateUsages(Set<AclUsageDto> aclUsageDtos);

    /**
     * @return threshold value for size of ACL records.
     */
    int getRecordThreshold();

    /**
     * Gets list of all ACL usage batches.
     *
     * @return list of all {@link AclUsageBatch}es
     */
    List<AclUsageBatch> getAllAclUsageBatches();

    /**
     * Copies ACL usage batch.
     *
     * @param sourceUsageBatchId source usage batch id
     * @param aclUsageBatch      {@link AclUsageBatch} instance
     * @return count of copied ACL usages
     */
    int copyAclUsageBatch(String sourceUsageBatchId, AclUsageBatch aclUsageBatch);

    /**
     * Deletes {@link AclUsageBatch} with all its ACL Usages.
     *
     * @param usageBatch {@link AclUsageBatch} to delete
     */
    void deleteAclUsageBatch(AclUsageBatch usageBatch);

    /**
     * Gets AclScenario names associated with usage batch.
     *
     * @param usageBatchId usage batch id
     * @return AclScenario names associated with usage batch
     */
    List<String> getScenarioNamesAssociatedWithUsageBatch(String usageBatchId);
}
