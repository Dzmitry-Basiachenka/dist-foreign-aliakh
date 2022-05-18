package com.copyright.rup.dist.foreign.ui.usage.api.acl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetail;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetailDto;
import com.copyright.rup.dist.foreign.service.impl.csv.AclFundPoolCsvProcessor;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.vaadin.widget.api.IController;

import com.vaadin.util.ReflectTools;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Interface for ACL fund pool controller.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/17/2022
 *
 * @author Anton Azarenka
 */
public interface IAclFundPoolController extends IController<IAclFundPoolWidget> {

    /**
     * {@link #onFilterChanged(FilterChangedEvent)}.
     */
    Method ON_FILTER_CHANGED =
        ReflectTools.findMethod(IAclFundPoolController.class, "onFilterChanged", FilterChangedEvent.class);

    /**
     * Initializes {@link IAclUsageFilterWidget}.
     *
     * @return initialized {@link IAclUsageFilterWidget}
     */
    IAclFundPoolFilterWidget initAclFundPoolFilterWidget();

    /**
     * Handles changes of filter.
     *
     * @param event event
     */
    void onFilterChanged(FilterChangedEvent event);

    /**
     * Checks whether {@link com.copyright.rup.dist.foreign.domain.AclFundPool} with the name already exists.
     *
     * @param name ACL fund pool name
     * @return {@code true} - if fund pool exists, {@code false} - otherwise
     */
    boolean isFundPoolExist(String name);

    /**
     * Gets instance of CSV processor.
     *
     * @return instance of {@link AclFundPoolCsvProcessor}
     */
    AclFundPoolCsvProcessor getCsvProcessor();

    /**
     * Inserts manual ACL fund pool and ACL fund pool details.
     *
     * @param fundPool        {@link AclFundPool} instance
     * @param fundPoolDetails list of {@link AclFundPoolDetail}s
     * @return count of inserted usages
     */
    int loadManualFundPool(AclFundPool fundPool, List<AclFundPoolDetail> fundPoolDetails);

    /**
     * Creates LDMT ACL fund pool and adds unused ACL fund pool details to it.
     *
     * @param fundPool {@link AclFundPool} instance
     * @return count of added usages
     */
    int createLdmtFundPool(AclFundPool fundPool);

    /**
     * Checks whether LDMT {@link AclFundPoolDetail} exists for Fund Pool creation with provided license type.
     *
     * @param licenseType license type
     * @return {@code true} - if detail exists, {@code false} - otherwise
     */
    boolean isLdmtDetailExist(String licenseType);

    /**
     * @return list of {@link AclFundPoolDetailDto}s by applied filter.
     */
    List<AclFundPoolDetailDto> getDtos();

    /**
     * Return instance of {@link IStreamSource} for errors result.
     *
     * @param fileName         name of processed file
     * @param processingResult information about errors
     * @return instance of {@link IStreamSource}
     */
    IStreamSource getErrorResultStreamSource(String fileName, ProcessingResult<AclFundPoolDetail> processingResult);

    /**
     * @return instance of {@link IStreamSource} for export.
     */
    IStreamSource getExportAclFundPoolDetailsStreamSource();
}
