package com.copyright.rup.dist.foreign.service.api.acl;

import com.copyright.rup.dist.foreign.domain.UdmValueAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;

import java.util.List;

/**
 * Interface for UDM value audit service.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/01/2021
 *
 * @author Aliaksandr Liakh
 */
public interface IUdmValueAuditService {

    /**
     * Logs UDM usage action.
     *
     * @param udmValueId   {@link com.copyright.rup.dist.foreign.domain.UdmValue} id
     * @param actionType   usage action type
     * @param actionReason action reason
     */
    void logAction(String udmValueId, UsageActionTypeEnum actionType, String actionReason);

    /**
     * Gets all actions for usage with given id.
     *
     * @param udmValueId {@link com.copyright.rup.dist.foreign.domain.UdmValue} id
     * @return list of {@link UdmValueAuditItem}
     */
    List<UdmValueAuditItem> getUdmUsageAudit(String udmValueId);
}
