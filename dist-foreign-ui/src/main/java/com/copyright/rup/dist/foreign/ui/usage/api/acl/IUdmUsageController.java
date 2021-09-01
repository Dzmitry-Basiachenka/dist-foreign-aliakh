package com.copyright.rup.dist.foreign.ui.usage.api.acl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.domain.CompanyInformation;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.UdmActionReason;
import com.copyright.rup.dist.foreign.domain.UdmAuditFieldToValuesMap;
import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.domain.UdmIneligibleReason;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.service.impl.csv.UdmCsvProcessor;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.vaadin.widget.api.IController;

import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.ui.Window;
import com.vaadin.util.ReflectTools;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
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
     * @param closeListener listener to handle window close event
     */
    void showUdmUsageHistory(String udmUsageId, Window.CloseListener closeListener);

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
     * @param udmUsages set of usages to assign to logged in user
     */
    void assignUsages(Set<UdmUsageDto> udmUsages);

    /**
     * Un-assigns provided usages.
     *
     * @param usageIds set of usage ids to un-assign
     */
    void unassignUsages(Set<String> usageIds);

    /**
     * Updates UDM usage.
     *
     * @param udmUsageDto            {@link UdmUsageDto} to update
     * @param fieldToValueChangesMap map of field name to its values (old and new)
     * @param isResearcher           {@code true} if the user has Researcher role, {@code false} otherwise
     */
    void updateUsage(UdmUsageDto udmUsageDto, UdmAuditFieldToValuesMap fieldToValueChangesMap, boolean isResearcher);

    /**
     * @return list of {@link UdmActionReason}.
     */
    List<UdmActionReason> getAllActionReasons();

    /**
     * @return list of {@link UdmIneligibleReason}.
     */
    List<UdmIneligibleReason> getAllIneligibleReasons();

    /**
     * @return list of {@link DetailLicenseeClass}.
     */
    List<DetailLicenseeClass> getDetailLicenseeClasses();

    /**
     * Gets list of {@link UdmBatch}es.
     *
     * @return list of found {@link UdmBatch}es
     */
    List<UdmBatch> getUdmBatches();

    /**
     * Deletes {@link UdmBatch} and all its details.
     *
     * @param udmBatch {@link UdmBatch} to delete
     */
    void deleteUdmBatch(UdmBatch udmBatch);

    /**
     * Returns true if selected UDM batch is completed based on intermediate statuses.
     *
     * @param udmBatchId batch identifier
     * @return @code true} - if UDM batch processing is completed, {@code false} - otherwise
     */
    boolean isUdmBatchProcessingCompleted(String udmBatchId);

    /**
     * Gets company information by provided company identifier.
     *
     * @param companyId company identifier
     * @return instance of {@link CompanyInformation}
     */
    CompanyInformation getCompanyInformation(Long companyId);

    /**
     * Calculates annualized copies.
     *
     * @param reportedTypeOfUse     reported type of use
     * @param quantity              quantity
     * @param annualMultiplier      annual multiplier
     * @param statisticalMultiplier statistical multiplier
     * @return annualized copies
     */
    BigDecimal calculateAnnualizedCopies(String reportedTypeOfUse, Long quantity, Integer annualMultiplier,
                                         BigDecimal statisticalMultiplier);

    /**
     * Updates UDM usages.
     *
     * @param udmUsageDtoToFieldValuesMap {@link UdmUsageDto} map of usages to update with audit changes
     * @param isResearcher                {@code true} if the user has Researcher role, {@code false} otherwise
     */
    void updateUsages(Map<UdmUsageDto, UdmAuditFieldToValuesMap> udmUsageDtoToFieldValuesMap, boolean isResearcher);

    /**
     * Gets all available periods.
     *
     * @return list of periods
     */
    List<Integer> getPeriods();
}
