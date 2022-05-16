package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.usage;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isNull;
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
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.foreign.domain.AclUsageBatch;
import com.copyright.rup.dist.foreign.domain.AclUsageDto;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.filter.AclUsageFilter;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;
import com.copyright.rup.dist.foreign.service.api.IPublicationTypeService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclCalculationReportService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageWidget;

import com.google.common.collect.Sets;

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
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Verifies {@link AclUsageController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/30/2022
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({StreamSource.class})
public class AclUsageControllerTest {

    private static final String ACL_USAGE_BATCH_NAME = "ACL Usage Batch 2021";

    private final AclUsageController controller = new AclUsageController();

    private IUdmUsageService udmUsageService;
    private IAclUsageBatchService aclUsageBatchService;
    private IAclUsageService aclUsageService;
    private IAclUsageFilterController aclUsageFilterController;
    private IAclUsageFilterWidget aclUsageFilterWidget;
    private IAclCalculationReportService aclCalculationReportService;
    private IStreamSourceHandler streamSourceHandler;
    private IPublicationTypeService publicationTypeService;
    private ILicenseeClassService licenseeClassService;

    @Before
    public void setUp() {
        udmUsageService = createMock(IUdmUsageService.class);
        aclUsageBatchService = createMock(IAclUsageBatchService.class);
        aclUsageService = createMock(IAclUsageService.class);
        aclUsageFilterController = createMock(IAclUsageFilterController.class);
        aclUsageFilterWidget = createMock(IAclUsageFilterWidget.class);
        aclCalculationReportService = createMock(IAclCalculationReportService.class);
        streamSourceHandler = createMock(IStreamSourceHandler.class);
        publicationTypeService = createMock(IPublicationTypeService.class);
        licenseeClassService = createMock(ILicenseeClassService.class);
        Whitebox.setInternalState(controller, udmUsageService);
        Whitebox.setInternalState(controller, aclUsageBatchService);
        Whitebox.setInternalState(controller, aclUsageService);
        Whitebox.setInternalState(controller, aclUsageFilterController);
        Whitebox.setInternalState(controller, aclCalculationReportService);
        Whitebox.setInternalState(controller, streamSourceHandler);
        Whitebox.setInternalState(controller, publicationTypeService);
        Whitebox.setInternalState(controller, licenseeClassService);
    }

    @Test
    public void testLoadBeans() {
        List<AclUsageDto> aclUsageDtos = Collections.singletonList(new AclUsageDto());
        AclUsageFilter filter = buildAclUsageFilter();
        Capture<Pageable> pageableCapture = newCapture();
        expect(aclUsageFilterController.getWidget()).andReturn(aclUsageFilterWidget).once();
        expect(aclUsageFilterWidget.getAppliedFilter()).andReturn(filter);
        expect(aclUsageService.getDtos(eq(filter), capture(pageableCapture), isNull())).andReturn(aclUsageDtos).once();
        replay(aclUsageFilterController, aclUsageFilterWidget, aclUsageService);
        assertSame(aclUsageDtos, controller.loadBeans(0, 10, null));
        assertEquals(10, pageableCapture.getValue().getLimit());
        assertEquals(0, pageableCapture.getValue().getOffset());
        verify(aclUsageFilterController, aclUsageFilterWidget, aclUsageService);
    }

    @Test
    public void testGetBeansCount() {
        AclUsageFilter filter = buildAclUsageFilter();
        expect(aclUsageFilterController.getWidget()).andReturn(aclUsageFilterWidget).once();
        expect(aclUsageFilterWidget.getAppliedFilter()).andReturn(filter);
        expect(aclUsageService.getCount(filter)).andReturn(10).once();
        replay(aclUsageFilterController, aclUsageFilterWidget, aclUsageService);
        assertEquals(10, controller.getBeansCount());
        verify(aclUsageFilterController, aclUsageFilterWidget, aclUsageService);
    }

    @Test
    public void testGetAllPeriods() {
        List<Integer> periods = Collections.singletonList(202106);
        expect(udmUsageService.getPeriods()).andReturn(periods).once();
        replay(udmUsageService);
        assertEquals(periods, controller.getAllPeriods());
        verify(udmUsageService);
    }

    @Test
    public void testIsAclUsageBatchExist() {
        expect(aclUsageBatchService.isAclUsageBatchExist(ACL_USAGE_BATCH_NAME)).andReturn(true).once();
        replay(aclUsageBatchService);
        assertTrue(controller.isAclUsageBatchExist(ACL_USAGE_BATCH_NAME));
        verify(aclUsageBatchService);
    }

    @Test
    public void testInsertAclUsageBatch() {
        expect(aclUsageBatchService.insert(buildAclUsageBatch())).andReturn(1).once();
        expect(aclUsageFilterController.getWidget()).andReturn(aclUsageFilterWidget).once();
        aclUsageFilterWidget.clearFilter();
        expectLastCall().once();
        replay(aclUsageFilterController, aclUsageFilterWidget, aclUsageBatchService);
        assertEquals(1, controller.insertAclUsageBatch(buildAclUsageBatch()));
        verify(aclUsageFilterController, aclUsageFilterWidget, aclUsageBatchService);
    }

    @Test
    public void testInstantiateWidget() {
        IAclUsageWidget widget = controller.instantiateWidget();
        assertNotNull(widget);
        assertEquals(AclUsageWidget.class, widget.getClass());
    }

    @Test
    public void testGetExportAclUsagesStreamSource() {
        OffsetDateTime date = OffsetDateTime.of(2019, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
        mockStatic(OffsetDateTime.class);
        AclUsageFilter aclUsageFilter = new AclUsageFilter();
        Capture<Supplier<String>> fileNameSupplierCapture = newCapture();
        Capture<Consumer<PipedOutputStream>> posConsumerCapture = newCapture();
        String fileName = "export_acl_usage_";
        Supplier<String> fileNameSupplier = () -> fileName;
        Supplier<InputStream> isSupplier = () -> IOUtils.toInputStream(StringUtils.EMPTY, StandardCharsets.UTF_8);
        PipedOutputStream pos = new PipedOutputStream();
        expect(OffsetDateTime.now()).andReturn(date).once();
        expect(aclUsageFilterController.getWidget()).andReturn(aclUsageFilterWidget).once();
        expect(aclUsageFilterWidget.getAppliedFilter()).andReturn(aclUsageFilter).once();
        expect(streamSourceHandler.getCsvStreamSource(capture(fileNameSupplierCapture), capture(posConsumerCapture)))
            .andReturn(new StreamSource(fileNameSupplier, "csv", isSupplier)).once();
        aclCalculationReportService.writeAclUsageCsvReport(aclUsageFilter, pos);
        expectLastCall().once();
        replay(OffsetDateTime.class, aclUsageFilterWidget, aclUsageFilterController, streamSourceHandler,
            aclCalculationReportService);
        IStreamSource streamSource = controller.getExportAclUsagesStreamSource();
        assertEquals("export_acl_usage_01_02_2019_03_04.csv", streamSource.getSource().getKey().get());
        assertEquals(fileName, fileNameSupplierCapture.getValue().get());
        Consumer<PipedOutputStream> posConsumer = posConsumerCapture.getValue();
        posConsumer.accept(pos);
        assertNotNull(posConsumer);
        verify(OffsetDateTime.class, aclUsageFilterWidget, aclUsageFilterController, streamSourceHandler,
            aclCalculationReportService);
    }

    @Test
    public void testGetPublicationTypes() {
        List<PublicationType> pubTypes = Collections.singletonList(buildPublicationType());
        expect(publicationTypeService.getPublicationTypes("ACL")).andReturn(pubTypes).once();
        replay(publicationTypeService);
        assertSame(pubTypes, controller.getPublicationTypes());
        verify(publicationTypeService);
    }

    @Test
    public void testGetDetailLicenseeClasses() {
        List<DetailLicenseeClass> licenseeClasses = Collections.singletonList(new DetailLicenseeClass());
        expect(licenseeClassService.getDetailLicenseeClasses("ACL")).andReturn(licenseeClasses).once();
        replay(licenseeClassService);
        assertSame(licenseeClasses, controller.getDetailLicenseeClasses());
        verify(licenseeClassService);
    }

    @Test
    public void testUpdateUsages() {
        aclUsageService.updateUsages(Collections.singleton(new AclUsageDto()));
        expectLastCall().once();
        replay(aclUsageService);
        controller.updateUsages(Collections.singleton(new AclUsageDto()));
        verify(aclUsageService);
    }

    private AclUsageBatch buildAclUsageBatch() {
        AclUsageBatch usageBatch = new AclUsageBatch();
        usageBatch.setName(ACL_USAGE_BATCH_NAME);
        usageBatch.setDistributionPeriod(202112);
        usageBatch.setPeriods(Sets.newHashSet(202106, 202112));
        usageBatch.setEditable(true);
        return usageBatch;
    }

    private AclUsageFilter buildAclUsageFilter() {
        AclUsageFilter aclUsageFilter = new AclUsageFilter();
        aclUsageFilter.setUsageBatchName(ACL_USAGE_BATCH_NAME);
        return aclUsageFilter;
    }

    private PublicationType buildPublicationType() {
        PublicationType pubType = new PublicationType();
        pubType.setName("BK");
        pubType.setDescription("Book");
        return pubType;
    }
}
