package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IReportService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;

/**
 * Verifies {@link ReportService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 05/16/2018
 *
 * @author Uladzislau Shalamitski
 */
public class ReportServiceTest {

    private IReportService reportService;
    private IUsageRepository usageRepository;

    @Before
    public void setUp() {
        reportService = new ReportService();
        usageRepository = createMock(IUsageRepository.class);
        Whitebox.setInternalState(reportService, "usageRepository", usageRepository);
    }

    @Test
    public void testWriteUndistributedLiabilitiesCsvReport() {
        LocalDate paymentDate = LocalDate.now();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        usageRepository.writeUndistributedLiabilitiesCsvReport(paymentDate, outputStream);
        expectLastCall().once();
        replay(usageRepository);
        reportService.writeUndistributedLiabilitiesCsvReport(paymentDate, outputStream);
        verify(usageRepository);
    }
}
