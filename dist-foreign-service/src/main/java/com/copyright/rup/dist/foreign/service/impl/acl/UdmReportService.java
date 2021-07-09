package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmReportRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PipedOutputStream;

/**
 * Implements {@link IUdmReportService}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 07/05/2021
 *
 * @author Dzmitry Basiachenka
 */
@Service
public class UdmReportService implements IUdmReportService {

    @Autowired
    private IUdmReportRepository udmReportRepository;

    @Override
    public void writeUdmUsageCsvReportSpecialistManager(UdmUsageFilter filter, PipedOutputStream pipedOutputStream) {
        udmReportRepository.writeUdmUsageCsvReportSpecialistManager(filter, pipedOutputStream);
    }

    @Override
    public void writeUdmUsageCsvReportResearcher(UdmUsageFilter filter, PipedOutputStream pipedOutputStream) {
        udmReportRepository.writeUdmUsageCsvReportResearcher(filter, pipedOutputStream);
    }

    @Override
    public void writeUdmUsageCsvReportView(UdmUsageFilter filter, PipedOutputStream pipedOutputStream) {
        udmReportRepository.writeUdmUsageCsvReportView(filter, pipedOutputStream);
    }
}
