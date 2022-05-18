package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.reporting.impl.StreamSource;
import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetail;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetailDto;
import com.copyright.rup.dist.foreign.domain.filter.AclFundPoolDetailFilter;
import com.copyright.rup.dist.foreign.service.api.acl.IAclCalculationReportService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclFundPoolService;
import com.copyright.rup.dist.foreign.service.impl.csv.AclFundPoolCsvProcessor;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolWidget;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.InputStream;
import java.io.PipedOutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Verifies {@link AclFundPoolController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/18/2022
 *
 * @author Anton Azarenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({StreamSource.class})
public class AclFundPoolControllerTest {

    private static final String LICENSE_TYPE = "ACL";
    private final AclFundPoolController controller = new AclFundPoolController();
    private CsvProcessorFactory csvProcessorFactory;
    private IAclFundPoolService fundPoolService;
    private IAclFundPoolFilterController fundPoolFilterController;
    private IAclCalculationReportService aclCalculationReportService;
    private IStreamSourceHandler streamSourceHandler;

    @Before
    public void setUp() {
        fundPoolService = createMock(IAclFundPoolService.class);
        csvProcessorFactory = createMock(CsvProcessorFactory.class);
        fundPoolFilterController = createMock(IAclFundPoolFilterController.class);
        aclCalculationReportService = createMock(IAclCalculationReportService.class);
        streamSourceHandler = createMock(IStreamSourceHandler.class);
        Whitebox.setInternalState(controller, csvProcessorFactory);
        Whitebox.setInternalState(controller, fundPoolService);
        Whitebox.setInternalState(controller, fundPoolFilterController);
        Whitebox.setInternalState(controller, aclCalculationReportService);
        Whitebox.setInternalState(controller, streamSourceHandler);
    }

    @Test
    public void testInstantiateWidget() {
        IAclFundPoolWidget widget = controller.instantiateWidget();
        assertNotNull(widget);
        assertEquals(AclFundPoolWidget.class, widget.getClass());
    }

    @Test
    public void testGetCsvProcessor() {
        AclFundPoolCsvProcessor processor = new AclFundPoolCsvProcessor();
        expect(csvProcessorFactory.getAclFundPoolCvsProcessor()).andReturn(processor).once();
        replay(csvProcessorFactory);
        assertSame(processor, controller.getCsvProcessor());
        verify(csvProcessorFactory);
    }

    @Test
    public void testIsAclFundPoolExist() {
        expect(fundPoolService.fundPoolExists("Fund Pool Name")).andReturn(true).once();
        replay(fundPoolService);
        assertTrue(controller.isFundPoolExist("Fund Pool Name"));
        verify(fundPoolService);
    }

    @Test
    public void testIsLdmtDetailExist() {
        expect(fundPoolService.isLdmtDetailExist(LICENSE_TYPE)).andReturn(true).once();
        replay(fundPoolService);
        assertTrue(controller.isLdmtDetailExist(LICENSE_TYPE));
        verify(fundPoolService);
    }

    @Test
    public void testLoadManualFundPool() {
        AclFundPool fundPool = buildFundPool(true);
        fundPoolService.insertManualAclFundPool(fundPool, Collections.singletonList(buildFundPoolDetail()));
        expectLastCall().once();
        replay(fundPoolService);
        int count = controller.loadManualFundPool(fundPool, Collections.singletonList(buildFundPoolDetail()));
        assertEquals(1, count);
        verify(fundPoolService);
    }

    @Test
    public void testCreateLdmtFundPool() {
        AclFundPool fundPool = buildFundPool(false);
        expect(fundPoolService.insertLdmtAclFundPool(fundPool)).andReturn(1).once();
        replay(fundPoolService);
        int count = controller.createLdmtFundPool(fundPool);
        assertEquals(1, count);
        verify(fundPoolService);
    }

    @Test
    public void testGetDtos() {
        AclFundPoolDetailFilter filter = new AclFundPoolDetailFilter();
        List<AclFundPoolDetailDto> grantDetails = Collections.singletonList(new AclFundPoolDetailDto());
        IAclFundPoolFilterWidget fundPoolFilterWidget = createMock(IAclFundPoolFilterWidget.class);
        expect(fundPoolFilterController.getWidget()).andReturn(fundPoolFilterWidget).once();
        expect(fundPoolFilterWidget.getAppliedFilter()).andReturn(filter).once();
        expect(fundPoolService.getDtosByFilter(filter)).andReturn(grantDetails).once();
        replay(fundPoolFilterController, fundPoolFilterWidget, fundPoolService);
        assertSame(grantDetails, controller.getDtos());
        verify(fundPoolFilterController, fundPoolFilterWidget, fundPoolService);
    }

    @Test
    public void testGetExportAclGrantDetailsStreamSource() {
        AclFundPoolDetailFilter filter = new AclFundPoolDetailFilter();
        OffsetDateTime date = OffsetDateTime.of(2019, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
        mockStatic(OffsetDateTime.class);
        Capture<Supplier<String>> fileNameSupplierCapture = newCapture();
        Capture<Consumer<PipedOutputStream>> posConsumerCapture = newCapture();
        String fileName = "export_fund_pool_details_";
        Supplier<String> fileNameSupplier = () -> fileName;
        Supplier<InputStream> isSupplier = () -> IOUtils.toInputStream(StringUtils.EMPTY, StandardCharsets.UTF_8);
        PipedOutputStream pos = new PipedOutputStream();
        expect(OffsetDateTime.now()).andReturn(date).once();
        IAclFundPoolFilterWidget fundPoolFilterWidget = createMock(IAclFundPoolFilterWidget.class);
        expect(fundPoolFilterController.getWidget()).andReturn(fundPoolFilterWidget).once();
        expect(fundPoolFilterWidget.getAppliedFilter()).andReturn(filter).once();
        expect(streamSourceHandler.getCsvStreamSource(capture(fileNameSupplierCapture), capture(posConsumerCapture)))
            .andReturn(new StreamSource(fileNameSupplier, "csv", isSupplier)).once();
        aclCalculationReportService.writeAclFundPoolDetailsCsvReport(filter, pos);
        expectLastCall().once();
        replay(OffsetDateTime.class, fundPoolFilterWidget, fundPoolFilterController, streamSourceHandler,
            aclCalculationReportService);
        IStreamSource streamSource = controller.getExportAclFundPoolDetailsStreamSource();
        assertEquals("export_fund_pool_details_01_02_2019_03_04.csv", streamSource.getSource().getKey().get());
        assertEquals(fileName, fileNameSupplierCapture.getValue().get());
        Consumer<PipedOutputStream> posConsumer = posConsumerCapture.getValue();
        posConsumer.accept(pos);
        assertNotNull(posConsumer);
        verify(OffsetDateTime.class, fundPoolFilterWidget, fundPoolFilterController, streamSourceHandler,
            aclCalculationReportService);
    }

    private AclFundPoolDetail buildFundPoolDetail() {
        AclFundPoolDetail aclFundPoolDetail = new AclFundPoolDetail();
        aclFundPoolDetail.setFundPoolId("4f01a2fc-c5d4-4738-9715-c7dafc0c1fad");
        aclFundPoolDetail.setLicenseType(LICENSE_TYPE);
        aclFundPoolDetail.setGrossAmount(new BigDecimal("0.55"));
        return aclFundPoolDetail;
    }

    private AclFundPool buildFundPool(boolean manualUploadFlag) {
        AclFundPool aclFundPool = new AclFundPool();
        aclFundPool.setName("Fund Pool Name");
        aclFundPool.setLicenseType(LICENSE_TYPE);
        aclFundPool.setManualUploadFlag(manualUploadFlag);
        return aclFundPool;
    }
}
