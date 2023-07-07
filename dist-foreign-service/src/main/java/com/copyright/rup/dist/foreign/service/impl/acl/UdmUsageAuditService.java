package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.repository.api.IUdmUsageAuditRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageAuditService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import io.micrometer.core.annotation.Timed;

/**
 * Implementation of {@link IUdmUsageAuditService}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 06/09/2021
 *
 * @author Aliaksandr Liakh
 */
@Service
@Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
public class UdmUsageAuditService implements IUdmUsageAuditService {

    @Autowired
    private IUdmUsageAuditRepository udmUsageAuditRepository;

    @Override
    public void logAction(String udmUsageId, UsageActionTypeEnum actionType, String actionReason) {
        udmUsageAuditRepository.insert(buildUsageAuditItem(udmUsageId, actionType, actionReason));
    }

    @Override
    public List<UsageAuditItem> getUdmUsageAudit(String udmUsageId) {
        return udmUsageAuditRepository.findByUdmUsageId(udmUsageId);
    }

    @Override
    public void deleteActionsByBatchId(String udmBatchId) {
        udmUsageAuditRepository.deleteByBatchId(udmBatchId);
    }

    private UsageAuditItem buildUsageAuditItem(String udmUsageId, UsageActionTypeEnum actionType, String actionReason) {
        UsageAuditItem auditItem = new UsageAuditItem();
        auditItem.setId(RupPersistUtils.generateUuid());
        auditItem.setUsageId(udmUsageId);
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
