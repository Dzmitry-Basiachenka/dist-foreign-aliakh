package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

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

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.reporting.impl.StreamSource;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.foreign.domain.AclGrantDetailDto;
import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.domain.filter.AclGrantDetailFilter;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclCalculationReportService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantDetailService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantSetService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBaselineService;
import com.copyright.rup.dist.foreign.service.impl.csv.AclGrantDetailCsvProcessor;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailWidget;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Verifies {@link AclGrantDetailController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/28/2022
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({StreamSource.class})
public class AclGrantDetailControllerTest {

    private static final String GRANT_SET_NAME = "Grant Set Name";
    private static final String GRANT_SET_ID = "4bbe8d57-c37a-40c0-b99b-cdeea5ceb54b";
    private static final List<String> ACL_SCENARIO_NAME = List.of("ACL Scenario");

    private final AclGrantDetailController controller = new AclGrantDetailController();
    private final AclGrantDetailFilter aclGrantDetailFilter = new AclGrantDetailFilter();

    private IUdmBaselineService udmBaselineService;
    private IAclGrantSetService aclGrantSetService;
    private IAclGrantDetailService aclGrantDetailService;
    private IAclGrantDetailFilterController aclGrantDetailFilterController;
    private IPrmIntegrationService prmIntegrationService;
    private IAclGrantDetailWidget aclGrantDetailWidget;
    private IAclGrantDetailFilterWidget aclGrantDetailFilterWidget;
    private IAclCalculationReportService aclCalculationReportService;
    private IStreamSourceHandler streamSourceHandler;
    private CsvProcessorFactory csvProcessorFactory;
    private IAclScenarioService aclScenarioService;

    @Before
    public void setUp() {
        udmBaselineService = createMock(IUdmBaselineService.class);
        aclGrantSetService = createMock(IAclGrantSetService.class);
        aclGrantDetailService = createMock(IAclGrantDetailService.class);
        aclGrantDetailFilterController = createMock(IAclGrantDetailFilterController.class);
        aclGrantDetailWidget = createMock(IAclGrantDetailWidget.class);
        prmIntegrationService = createMock(IPrmIntegrationService.class);
        aclGrantDetailFilterWidget = createMock(IAclGrantDetailFilterWidget.class);
        aclCalculationReportService = createMock(IAclCalculationReportService.class);
        csvProcessorFactory = createMock(CsvProcessorFactory.class);
        streamSourceHandler = createMock(IStreamSourceHandler.class);
        aclScenarioService = createMock(IAclScenarioService.class);
        Whitebox.setInternalState(controller, udmBaselineService);
        Whitebox.setInternalState(controller, aclGrantSetService);
        Whitebox.setInternalState(controller, aclGrantDetailFilterController);
        Whitebox.setInternalState(controller, aclGrantDetailService);
        Whitebox.setInternalState(controller, prmIntegrationService);
        Whitebox.setInternalState(controller, aclCalculationReportService);
        Whitebox.setInternalState(controller, csvProcessorFactory);
        Whitebox.setInternalState(controller, streamSourceHandler);
        Whitebox.setInternalState(controller, aclScenarioService);
    }

    @Test
    public void testGetBeansCount() {
        AclGrantDetailFilter filter = new AclGrantDetailFilter();
        expect(aclGrantDetailFilterController.getWidget()).andReturn(aclGrantDetailFilterWidget).once();
        expect(aclGrantDetailFilterWidget.getAppliedFilter()).andReturn(filter).once();
        expect(aclGrantDetailService.getCount(filter)).andReturn(1).once();
        replay(aclGrantDetailFilterController, aclGrantDetailFilterWidget, aclGrantDetailService);
        assertEquals(1, controller.getBeansCount());
        verify(aclGrantDetailFilterController, aclGrantDetailFilterWidget, aclGrantDetailService);
    }

    @Test
    public void testLoadBeans() {
        AclGrantDetailFilter filter = new AclGrantDetailFilter();
        List<AclGrantDetailDto> grantDetails = List.of(new AclGrantDetailDto());
        Capture<Pageable> pageableCapture = newCapture();
        expect(aclGrantDetailFilterController.getWidget()).andReturn(aclGrantDetailFilterWidget).once();
        expect(aclGrantDetailFilterWidget.getAppliedFilter()).andReturn(filter).once();
        expect(aclGrantDetailService.getDtos(eq(filter), capture(pageableCapture), isNull()))
            .andReturn(grantDetails).once();
        replay(aclGrantDetailFilterController, aclGrantDetailFilterWidget, aclGrantDetailService);
        assertSame(grantDetails, controller.loadBeans(0, 1, null));
        Pageable pageable = pageableCapture.getValue();
        assertEquals(1, pageable.getLimit());
        assertEquals(0, pageable.getOffset());
        verify(aclGrantDetailFilterController, aclGrantDetailFilterWidget, aclGrantDetailService);
    }

    @Test
    public void testInstantiateWidget() {
        aclGrantDetailWidget = controller.instantiateWidget();
        assertNotNull(aclGrantDetailWidget);
    }

    @Test
    public void testGetBaselinePeriods() {
        List<Integer> expectedPeriods = List.of(202106);
        expect(udmBaselineService.getPeriods()).andReturn(expectedPeriods).once();
        replay(udmBaselineService);
        List<Integer> periods = controller.getBaselinePeriods();
        assertEquals(expectedPeriods, periods);
        verify(udmBaselineService);
    }

    @Test
    public void testGrantSetExist() {
        expect(aclGrantSetService.isGrantSetExist(GRANT_SET_NAME)).andReturn(true).once();
        replay(aclGrantSetService);
        assertTrue(controller.isGrantSetExist(GRANT_SET_NAME));
        verify(aclGrantSetService);
    }

    @Test
    public void testInsertAclGrantSet() {
        AclGrantSet grantSet = buildAclGrantSet();
        expect(aclGrantSetService.insert(grantSet)).andReturn(1).once();
        aclGrantDetailService.populatePayeesAsync(grantSet.getId());
        expectLastCall().once();
        expect(aclGrantDetailFilterController.getWidget()).andReturn(aclGrantDetailFilterWidget).once();
        aclGrantDetailFilterWidget.clearFilter();
        expectLastCall().once();
        replay(aclGrantSetService, aclGrantDetailFilterController, aclGrantDetailFilterWidget);
        assertEquals(1, controller.insertAclGrantSet(grantSet));
        verify(aclGrantSetService, aclGrantDetailFilterController, aclGrantDetailFilterWidget);
    }

    @Test
    public void testInsertAclGrantDetails() {
        AclGrantSet grantSet = buildAclGrantSet();
        List<AclGrantDetailDto> grantDetailDtos = new ArrayList<>();
        aclGrantDetailService.addToGrantSet(grantSet, grantDetailDtos);
        expectLastCall().once();
        aclGrantDetailService.populatePayeesAsync(grantDetailDtos);
        expectLastCall().once();
        expect(aclGrantDetailFilterController.getWidget()).andReturn(aclGrantDetailFilterWidget).once();
        aclGrantDetailFilterWidget.clearFilter();
        expectLastCall().once();
        replay(aclGrantDetailService, aclGrantDetailFilterController, aclGrantDetailFilterWidget);
        controller.insertAclGrantDetails(grantSet, grantDetailDtos);
        verify(aclGrantDetailService, aclGrantDetailFilterController, aclGrantDetailFilterWidget);
    }

    @Test
    public void testGetAllAclGrantSets() {
        List<AclGrantSet> grantSets = List.of(new AclGrantSet());
        expect(aclGrantSetService.getAll()).andReturn(grantSets).once();
        replay(aclGrantSetService);
        assertEquals(grantSets, controller.getAllAclGrantSets());
        verify(aclGrantSetService);
    }

    @Test
    public void testDeleteAclGrantSet() {
        AclGrantSet grantSet = new AclGrantSet();
        aclGrantSetService.deleteAclGrantSet(grantSet);
        expectLastCall().once();
        expect(aclGrantDetailFilterController.getWidget()).andReturn(aclGrantDetailFilterWidget).once();
        aclGrantDetailFilterWidget.clearFilter();
        expectLastCall().once();
        replay(aclGrantSetService, aclGrantDetailFilterController, aclGrantDetailFilterWidget);
        controller.deleteAclGrantSet(grantSet);
        verify(aclGrantSetService, aclGrantDetailFilterController, aclGrantDetailFilterWidget);
    }

    @Test
    public void testGetRightsholder() {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(123456789L);
        rightsholder.setName("Rh Name");
        expect(prmIntegrationService.getRightsholder(123456789L)).andReturn(rightsholder);
        replay(prmIntegrationService);
        Rightsholder actualRightsholder = controller.getRightsholder(123456789L);
        assertNotNull(actualRightsholder);
        assertEquals(rightsholder.getAccountNumber(), actualRightsholder.getAccountNumber());
        assertEquals(rightsholder.getName(), actualRightsholder.getName());
        verify(prmIntegrationService);
    }

    @Test
    public void testUpdateAclGrantDetails() {
        Set<AclGrantDetailDto> grantDetailDtos = Set.of(new AclGrantDetailDto());
        aclGrantDetailService.updateGrants(grantDetailDtos, true);
        expectLastCall().once();
        aclGrantDetailService.populatePayeesAsync(grantDetailDtos);
        expectLastCall().once();
        replay(aclGrantDetailService);
        controller.updateAclGrants(grantDetailDtos, true);
        verify(aclGrantDetailService);
    }

    @Test
    public void testGetCsvProcessor() {
        String grantSetId = "9fec7e74-e9ba-4e3e-835c-db02a0575912";
        AclGrantDetailCsvProcessor processor = new AclGrantDetailCsvProcessor();
        expect(csvProcessorFactory.getAclGrantDetailCvsProcessor(grantSetId)).andReturn(processor).once();
        replay(csvProcessorFactory);
        assertSame(processor, controller.getCsvProcessor(grantSetId));
        verify(csvProcessorFactory);
    }

    @Test
    public void testGetExportAclGrantDetailsStreamSource() {
        OffsetDateTime date = OffsetDateTime.of(2019, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
        mockStatic(OffsetDateTime.class);
        Capture<Supplier<String>> fileNameSupplierCapture = newCapture();
        Capture<Consumer<PipedOutputStream>> posConsumerCapture = newCapture();
        String fileName = "export_grant_set_";
        Supplier<String> fileNameSupplier = () -> fileName;
        Supplier<InputStream> inputStreamSupplier =
                () -> IOUtils.toInputStream(StringUtils.EMPTY, StandardCharsets.UTF_8);
        PipedOutputStream pos = new PipedOutputStream();
        expect(OffsetDateTime.now()).andReturn(date).once();
        expect(aclGrantDetailFilterController.getWidget()).andReturn(aclGrantDetailFilterWidget).once();
        expect(aclGrantDetailFilterWidget.getAppliedFilter()).andReturn(aclGrantDetailFilter).once();
        expect(streamSourceHandler.getCsvStreamSource(capture(fileNameSupplierCapture), capture(posConsumerCapture)))
            .andReturn(new StreamSource(fileNameSupplier, "csv", inputStreamSupplier)).once();
        aclCalculationReportService.writeAclGrantDetailCsvReport(aclGrantDetailFilter, pos);
        expectLastCall().once();
        replay(OffsetDateTime.class, aclGrantDetailFilterWidget, aclGrantDetailFilterController, streamSourceHandler,
            aclCalculationReportService);
        IStreamSource streamSource = controller.getExportAclGrantDetailsStreamSource();
        assertEquals("export_grant_set_01_02_2019_03_04.csv", streamSource.getSource().getKey().get());
        assertEquals(fileName, fileNameSupplierCapture.getValue().get());
        Consumer<PipedOutputStream> posConsumer = posConsumerCapture.getValue();
        posConsumer.accept(pos);
        assertNotNull(posConsumer);
        verify(OffsetDateTime.class, aclGrantDetailFilterWidget, aclGrantDetailFilterController, streamSourceHandler,
            aclCalculationReportService);
    }

    @Test
    public void testGetScenarioNamesAssociatedWithFundPool() {
        expect(aclScenarioService.getScenarioNamesByFundPoolId(GRANT_SET_ID)).andReturn(ACL_SCENARIO_NAME).once();
        replay(aclScenarioService);
        assertEquals(ACL_SCENARIO_NAME, aclScenarioService.getScenarioNamesByFundPoolId(GRANT_SET_ID));
        verify(aclScenarioService);
    }

    @Test
    public void testGetAclGrantSetById() {
        AclGrantSet aclGrantSet = new AclGrantSet();
        aclGrantSet.setId(GRANT_SET_ID);
        expect(aclGrantSetService.getById(GRANT_SET_ID)).andReturn(aclGrantSet).once();
        replay(aclGrantSetService);
        assertEquals(aclGrantSet, aclGrantSetService.getById(GRANT_SET_ID));
        verify(aclGrantSetService);
    }

    @Test
    public void testCopyAclGrantSet() {
        AclGrantSet aclGrantSet = new AclGrantSet();
        aclGrantSet.setId(GRANT_SET_ID);
        expect(aclGrantSetService.copyGrantSet(aclGrantSet, GRANT_SET_ID)).andReturn(1).once();
        expect(aclGrantDetailFilterController.getWidget()).andReturn(aclGrantDetailFilterWidget).once();
        aclGrantDetailFilterWidget.clearFilter();
        expectLastCall().once();
        replay(aclGrantSetService, aclGrantDetailFilterController, aclGrantDetailFilterWidget);
        assertEquals(1, controller.copyAclGrantSet(aclGrantSet, GRANT_SET_ID));
        verify(aclGrantSetService, aclGrantDetailFilterController, aclGrantDetailFilterWidget);
    }

    @Test
    public void testRefreshPayeesAsync() {
        aclGrantDetailService.populatePayeesAsync(GRANT_SET_ID);
        expectLastCall().once();
        replay(aclGrantDetailService);
        controller.refreshPayeesAsync(GRANT_SET_ID);
        verify(aclGrantDetailService);
    }

    private AclGrantSet buildAclGrantSet() {
        AclGrantSet aclGrantSet = new AclGrantSet();
        aclGrantSet.setId(GRANT_SET_ID);
        aclGrantSet.setName(GRANT_SET_NAME);
        aclGrantSet.setGrantPeriod(202012);
        aclGrantSet.setPeriods(Set.of(202112));
        aclGrantSet.setLicenseType("ACL");
        aclGrantSet.setEditable(true);
        return aclGrantSet;
    }
}
