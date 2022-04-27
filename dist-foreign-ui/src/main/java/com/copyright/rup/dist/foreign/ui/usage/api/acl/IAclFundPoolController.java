package com.copyright.rup.dist.foreign.ui.usage.api.acl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetail;
import com.copyright.rup.dist.foreign.service.impl.csv.AclFundPoolCsvProcessor;
import com.copyright.rup.vaadin.widget.api.IController;

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
     * Initializes {@link IAclUsageFilterWidget}.
     *
     * @return initialized {@link IAclUsageFilterWidget}
     */
    IAclFundPoolFilterWidget initAclFundPoolFilterWidget();

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
     * Inserts ACL fund pool and ACL fund pool details.
     *
     * @param fundPool        {@link AclFundPool} instance
     * @param fundPoolDetails list of {@link AclFundPoolDetail}s
     * @return count of inserted usages
     */
    int loadFundPool(AclFundPool fundPool, List<AclFundPoolDetail> fundPoolDetails);

    /**
     * Return instance of {@link IStreamSource} for errors result.
     *
     * @param fileName         name of processed file
     * @param processingResult information about errors
     * @return instance of {@link IStreamSource}
     */
    IStreamSource getErrorResultStreamSource(String fileName, ProcessingResult<AclFundPoolDetail> processingResult);
}
