package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.UdmValueActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UdmValueAuditItem;
import com.copyright.rup.dist.foreign.repository.api.IUdmValueAuditRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmValueAuditService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import io.micrometer.core.annotation.Timed;

/**
 * Implementation of {@link IUdmValueAuditService}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/01/2021
 *
 * @author Aliaksandr Liakh
 */
@Service
@Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
public class UdmValueAuditService implements IUdmValueAuditService {

    @Autowired
    private IUdmValueAuditRepository udmValueAuditRepository;

    @Override
    public void logAction(String udmValueId, UdmValueActionTypeEnum actionType, String actionReason) {
        udmValueAuditRepository.insert(buildUdmValueAuditItem(udmValueId, actionType, actionReason));
    }

    @Override
    public List<UdmValueAuditItem> getUdmValueAudit(String udmValueId) {
        return udmValueAuditRepository.findByUdmValueId(udmValueId);
    }

    @Override
    public List<String> getUserNames() {
        return udmValueAuditRepository.findUserNames();
    }

    private UdmValueAuditItem buildUdmValueAuditItem(String udmValueId, UdmValueActionTypeEnum actionType,
                                                     String actionReason) {
        UdmValueAuditItem auditItem = new UdmValueAuditItem();
        auditItem.setId(RupPersistUtils.generateUuid());
        auditItem.setValueId(udmValueId);
        auditItem.setActionType(actionType);
        auditItem.setActionReason(actionReason);
        String userName = RupContextUtils.getUserName();
        auditItem.setCreateUser(userName);
        auditItem.setUpdateUser(userName);
        Date currentDate = Date.from(Instant.now().atZone(ZoneId.systemDefault()).toInstant());
        auditItem.setCreateDate(currentDate);
        auditItem.setUpdateDate(currentDate);
        return auditItem;
    }
}
