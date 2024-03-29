package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.UdmValueAuditItem;

import java.io.Serializable;
import java.util.List;

/**
 * Interface for UDM value audit repository.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/01/2021
 *
 * @author Aliaksandr Liakh
 */
public interface IUdmValueAuditRepository extends Serializable {

    /**
     * Inserts {@link UdmValueAuditItem}.
     *
     * @param auditItem instance of {@link UdmValueAuditItem}
     */
    void insert(UdmValueAuditItem auditItem);

    /**
     * Finds list of {@link UdmValueAuditItem}s by {@link com.copyright.rup.dist.foreign.domain.UdmValue} identifier.
     *
     * @param udmValueId {@link com.copyright.rup.dist.foreign.domain.UdmValue} id
     * @return list of {@link UdmValueAuditItem}s
     */
    List<UdmValueAuditItem> findByUdmValueId(String udmValueId);

    /**
     * Finds list of usernames which have VALUE_EDIT status since the first publication to baseline.
     *
     * @return list of usernames
     */
    List<String> findUserNames();
}
