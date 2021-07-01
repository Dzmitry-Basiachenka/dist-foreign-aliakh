package com.copyright.rup.dist.foreign.ui.usage.api.acl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.service.impl.csv.UdmCsvProcessor;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.vaadin.widget.api.IController;

import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.util.ReflectTools;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * Interface for UDM usage controller.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 04/26/2021
 *
 * @author Ihar Suvorau
 */
public interface IUdmUsageController extends IController<IUdmUsageWidget> {

    /**
     * {@link #onFilterChanged(FilterChangedEvent)}.
     */
    Method ON_FILTER_CHANGED =
        ReflectTools.findMethod(IUdmUsageController.class, "onFilterChanged", FilterChangedEvent.class);

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
    List<UdmUsageDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders);

    /**
     * Return instance of {@link IStreamSource} for errors result.
     *
     * @param fileName         name of processed file
     * @param processingResult information about errors
     * @return instance of {@link IStreamSource}
     */
    IStreamSource getErrorResultStreamSource(String fileName, ProcessingResult processingResult);

    /**
     * Gets instance of CSV processor.
     *
     * @return instance of {@link UdmCsvProcessor}
     */
    UdmCsvProcessor getCsvProcessor();

    /**
     * Initializes {@link IUdmUsageFilterWidget}.
     *
     * @return initialized {@link IUdmUsageFilterWidget}
     */
    IUdmUsageFilterWidget initUsagesFilterWidget();

    /**
     * Handles changes of filter.
     *
     * @param event event
     */
    void onFilterChanged(FilterChangedEvent event);

    /**
     * Inserts UDM batch and UDM usages.
     *
     * @param udmBatch  {@link UdmBatch} instance
     * @param udmUsages list of {@link UdmUsage}s
     * @return count of inserted usages
     */
    int loadUdmBatch(UdmBatch udmBatch, List<UdmUsage> udmUsages);

    /**
     * Checks whether {@link UdmBatch} with the name already exists.
     *
     * @param name UDM batch name
     * @return {@code true} - if batch exists, {@code false} - otherwise
     */
    boolean udmBatchExists(String name);

    /**
     * Shows modal window with UDM usage history.
     *
     * @param udmUsageId {@link UdmUsage} id
     */
    void showUdmUsageHistory(String udmUsageId);

    /**
     * @return instance of {@link IStreamSource} for export for specialist and manager roles.
     */
    IStreamSource getExportUdmUsagesStreamSourceSpecialistManagerRoles();

    /**
     * @return instance of {@link IStreamSource} for export for researcher role.
     */
    IStreamSource getExportUdmUsagesStreamSourceResearcherRole();

    /**
     * @return instance of {@link IStreamSource} for export for view role.
     */
    IStreamSource getExportUdmUsagesStreamSourceViewRole();

    /**
     * Assigns provided usages to logged in user.
     *
     * @param usageIds set of usage ids to assign to logged in user
     */
    void assignUsages(Set<String> usageIds);

    /**
     * Un-assigns provided usages.
     *
     * @param usageIds set of usage ids to un-assign
     */
    void unassignUsages(Set<String> usageIds);
}
