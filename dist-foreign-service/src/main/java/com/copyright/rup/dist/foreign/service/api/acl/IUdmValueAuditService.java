package com.copyright.rup.dist.foreign.service.api.acl;

import com.copyright.rup.dist.foreign.domain.UdmValueActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UdmValueAuditItem;

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
     * Logs UDM value action.
     *
     * @param udmValueId   {@link com.copyright.rup.dist.foreign.domain.UdmValue} id
     * @param actionType   action type
     * @param actionReason action reason
     */
    void logAction(String udmValueId, UdmValueActionTypeEnum actionType, String actionReason);

    /**
     * Gets all actions for UDM value with given id.
     *
     * @param udmValueId {@link com.copyright.rup.dist.foreign.domain.UdmValue} id
     * @return list of {@link UdmValueAuditItem}
     */
    List<UdmValueAuditItem> getUdmValueAudit(String udmValueId);

    /**
     * Gets list of usernames which have VALUE_EDIT status since the first publication to baseline.
     *
     * @return list of usernames
     */
    List<String> getUserNames();
}
