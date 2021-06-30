package com.copyright.rup.dist.foreign.service.api.acl;

import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;

import java.util.List;

/**
 * Interface for UDM usage audit service.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 06/09/2021
 *
 * @author Aliaksandr Liakh
 */
public interface IUdmUsageAuditService {

    /**
     * Logs UDM usage action.
     *
     * @param udmUsageId   {@link com.copyright.rup.dist.foreign.domain.UdmUsage} id
     * @param actionType   usage action type
     * @param actionReason action reason
     */
    void logAction(String udmUsageId, UsageActionTypeEnum actionType, String actionReason);

    /**
     * Gets all actions for usage with given id.
     *
     * @param udmUsageId {@link com.copyright.rup.dist.foreign.domain.UdmUsage} id
     * @return list of {@link UsageAuditItem}
     */
    List<UsageAuditItem> getUdmUsageAudit(String udmUsageId);

    /**
     * Deletes UDM usage actions by UDM batch identifier.
     *
     * @param udmBatchId UDM batch identifier
     */
    void deleteActionsByBatchId(String udmBatchId);
}
