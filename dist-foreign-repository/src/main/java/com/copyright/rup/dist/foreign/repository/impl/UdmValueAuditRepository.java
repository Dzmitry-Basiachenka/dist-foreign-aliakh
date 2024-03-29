package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.UdmValueAuditItem;
import com.copyright.rup.dist.foreign.repository.api.IUdmValueAuditRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of {@link IUdmValueAuditRepository}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/01/2021
 *
 * @author Aliaksandr Liakh
 */
@Repository
public class UdmValueAuditRepository extends BaseRepository implements IUdmValueAuditRepository {

    private static final long serialVersionUID = 8867792993380377662L;

    @Override
    public void insert(UdmValueAuditItem auditItem) {
        insert("IUdmValueAuditMapper.insert", Objects.requireNonNull(auditItem));
    }

    @Override
    public List<UdmValueAuditItem> findByUdmValueId(String udmValueId) {
        return selectList("IUdmValueAuditMapper.findByUdmValueId", Objects.requireNonNull(udmValueId));
    }

    @Override
    public List<String> findUserNames() {
        return selectList("IUdmValueAuditMapper.findUserNames");
    }
}
