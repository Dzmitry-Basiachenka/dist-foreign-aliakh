package com.copyright.rup.dist.foreign.ui.usage.api.acl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.domain.AclGrantDetailDto;
import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.service.impl.csv.AclGrantDetailCsvProcessor;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.vaadin.widget.api.IController;

import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.util.ReflectTools;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * Interface for ACL grant detail controller.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/27/2022
 *
 * @author Dzmitry Basiachenka
 */
public interface IAclGrantDetailController extends IController<IAclGrantDetailWidget> {

    /**
     * {@link #onFilterChanged(FilterChangedEvent)}.
     */
    Method ON_FILTER_CHANGED =
        ReflectTools.findMethod(IAclGrantDetailController.class, "onFilterChanged", FilterChangedEvent.class);

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
    List<AclGrantDetailDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders);

    /**
     * Gets all available baseline periods.
     *
     * @return list of baseline periods
     */
    List<Integer> getBaselinePeriods();

    /**
     * Checks whether {@link AclGrantSet} with the name already exists.
     *
     * @param name ACL grant set name
     * @return {@code true} - if grant set exists, {@code false} - otherwise
     */
    boolean isGrantSetExist(String name);

    /**
     * Inserts ACL grant set.
     *
     * @param aclGrantSet {@link AclGrantSet} instance
     * @return count of inserted grant details
     */
    int insertAclGrantSet(AclGrantSet aclGrantSet);

    /**
     * Inserts uploaded {@link AclGrantDetailDto}s to specified {@link AclGrantSet}.
     *
     * @param grantSet {@link AclGrantSet} to be updated
     * @param details  list of details to insert
     */
    void insertAclGrantDetails(AclGrantSet grantSet, List<AclGrantDetailDto> details);

    /**
     * Gets list of all ACL grant sets.
     *
     * @return list of all {@link AclGrantSet}s
     */
    List<AclGrantSet> getAllAclGrantSets();

    /**
     * Deletes {@link AclGrantSet} with all its ACL grant details.
     *
     * @param grantSet {@link AclGrantSet} to delete
     */
    void deleteAclGrantSet(AclGrantSet grantSet);

    /**
     * Initializes {@link IAclGrantDetailFilterWidget}.
     *
     * @return initialized {@link IAclGrantDetailFilterWidget}
     */
    IAclGrantDetailFilterWidget initAclGrantDetailFilterWidget();

    /**
     * Updates ACL grants.
     *
     * @param aclGrantDetailDtos set of {@link AclGrantDetailDto} for update
     * @param doUpdateTouStatus  <code>true</code> if system should update TOU status
     */
    void updateAclGrants(Set<AclGrantDetailDto> aclGrantDetailDtos, boolean doUpdateTouStatus);

    /**
     * Gets {@link Rightsholder} from PRM by account number.
     *
     * @param accountNumber rightsholder account number
     * @return instance of {@link Rightsholder}
     */
    Rightsholder getRightsholder(Long accountNumber);

    /**
     * @return instance of {@link IStreamSource} for export.
     */
    IStreamSource getExportAclGrantDetailsStreamSource();

    /**
     * Gets instance of CSV processor to upload grant details.
     *
     * @param grantSetId ID of selected Grant Set
     * @return instance of {@link AclGrantDetailCsvProcessor}
     */
    AclGrantDetailCsvProcessor getCsvProcessor(String grantSetId);

    /**
     * Return instance of {@link IStreamSource} for errors result.
     *
     * @param fileName         name of processed file
     * @param processingResult information about errors
     * @return instance of {@link IStreamSource}
     */
    IStreamSource getErrorResultStreamSource(String fileName, ProcessingResult<AclGrantDetailDto> processingResult);
}
