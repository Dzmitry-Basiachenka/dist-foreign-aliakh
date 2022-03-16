package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.dist.foreign.domain.filter.AclGrantDetailFilter;
import com.copyright.rup.dist.foreign.repository.api.IAclCalculationReportRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IAclCalculationReportService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.io.PipedOutputStream;

/**
 * Verifies {@link AclCalculationReportService}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/15/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclCalculationReportServiceTest {

    private final IAclCalculationReportService aclCalculationReportService = new AclCalculationReportService();
    private IAclCalculationReportRepository aclCalculationReportRepository;

    @Before
    public void setUp() {
        aclCalculationReportRepository = createMock(IAclCalculationReportRepository.class);
        Whitebox.setInternalState(aclCalculationReportService, aclCalculationReportRepository);
    }

    @Test
    public void testWriteUdmUsageCsvReport() {
        AclGrantDetailFilter filter = createMock(AclGrantDetailFilter.class);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        aclCalculationReportRepository.writeAclGrantDetailCsvReport(filter, outputStream);
        expectLastCall().once();
        replay(aclCalculationReportRepository);
        aclCalculationReportService.writeAclGrantDetailCsvReport(filter, outputStream);
        verify(aclCalculationReportRepository);
    }
}
