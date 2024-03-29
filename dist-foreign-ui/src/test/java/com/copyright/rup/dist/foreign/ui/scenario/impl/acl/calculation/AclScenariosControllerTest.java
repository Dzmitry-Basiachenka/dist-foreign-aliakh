package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.newCapture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.reporting.impl.StreamSource;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.domain.AclPublicationType;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDto;
import com.copyright.rup.dist.foreign.domain.AclUsageBatch;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.domain.filter.AclScenarioFilter;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;
import com.copyright.rup.dist.foreign.service.api.IPublicationTypeService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclCalculationReportService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclFundPoolService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantSetService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioUsageService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageService;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioActionHandler;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioHistoryController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosFilterController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosFilterWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosWidget;
import com.copyright.rup.vaadin.security.SecurityUtils;
import com.copyright.rup.vaadin.ui.component.window.ConfirmActionDialogWindow;
import com.copyright.rup.vaadin.ui.component.window.ConfirmDialogWindow.IListener;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.data.Validator;

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
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Verifies {@link AclScenariosController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/22/2022
 *
 * @author Dzmitry Basiahenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, ForeignSecurityUtils.class, OffsetDateTime.class, StreamSource.class,
    SecurityUtils.class, RupContextUtils.class})
public class AclScenariosControllerTest {

    private static final String SCENARIO_UID = "7ed0e17d-6baf-454c-803f-1d9be3cb3192";
    private static final String BATCH_UID = "47b4d40a-a6bd-4fe1-b463-4aa52bf0e56f";
    private static final String GRANT_SET_ID = "38931a03-d5a3-4576-99d3-722ef1ae49f9";
    private static final int DISTRIBUTION_PERIOD = 202212;
    private static final List<Integer> PERIOD_PRIORS = List.of(0);
    private static final String LICENSE_TYPE = "ACL";
    private static final String ACL_PRODUCT_FAMILY = "ACL";
    private static final String USER_NAME = "user@copyright.com";

    private IAclScenariosWidget scenariosWidget;
    private AclScenariosController aclScenariosController;
    private IAclScenarioService aclScenarioService;
    private IAclScenarioUsageService aclScenarioUsageService;
    private IAclUsageBatchService usageBatchService;
    private IAclGrantSetService grantSetService;
    private IAclFundPoolService fundPoolService;
    private IPublicationTypeService publicationTypeService;
    private IAclScenarioController aclScenarioController;
    private IAclScenarioHistoryController aclScenarioHistoryController;
    private ILicenseeClassService licenseeClassService;
    private IAclUsageService aclUsageService;
    private IStreamSourceHandler streamSourceHandler;
    private IAclCalculationReportService aclCalculationReportService;
    private IAclScenariosFilterController aclScenariosFilterController;
    private AclScenario aclScenario;

    @Before
    public void setUp() {
        aclScenariosController = new AclScenariosController();
        scenariosWidget = createMock(IAclScenariosWidget.class);
        aclScenarioService = createMock(IAclScenarioService.class);
        aclScenarioUsageService = createMock(IAclScenarioUsageService.class);
        usageBatchService = createMock(IAclUsageBatchService.class);
        grantSetService = createMock(IAclGrantSetService.class);
        fundPoolService = createMock(IAclFundPoolService.class);
        publicationTypeService = createMock(IPublicationTypeService.class);
        licenseeClassService = createMock(ILicenseeClassService.class);
        aclUsageService = createMock(IAclUsageService.class);
        aclScenarioController = createMock(IAclScenarioController.class);
        aclScenarioHistoryController = createMock(IAclScenarioHistoryController.class);
        streamSourceHandler = createMock(IStreamSourceHandler.class);
        aclCalculationReportService = createMock(IAclCalculationReportService.class);
        aclScenariosFilterController = createMock(IAclScenariosFilterController.class);
        aclScenario = buildAclScenario();
        aclScenariosController.initActionHandlers();
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.getUserName()).andReturn("user@copyright.com").anyTimes();
        replay(SecurityUtils.class);
        mockStatic(ForeignSecurityUtils.class);
        Whitebox.setInternalState(aclScenariosController, "widget", scenariosWidget);
        Whitebox.setInternalState(aclScenariosController, aclScenarioService);
        Whitebox.setInternalState(aclScenariosController, aclScenarioUsageService);
        Whitebox.setInternalState(aclScenariosController, usageBatchService);
        Whitebox.setInternalState(aclScenariosController, grantSetService);
        Whitebox.setInternalState(aclScenariosController, fundPoolService);
        Whitebox.setInternalState(aclScenariosController, publicationTypeService);
        Whitebox.setInternalState(aclScenariosController, licenseeClassService);
        Whitebox.setInternalState(aclScenariosController, aclUsageService);
        Whitebox.setInternalState(aclScenariosController, aclScenarioController);
        Whitebox.setInternalState(aclScenariosController, aclScenarioHistoryController);
        Whitebox.setInternalState(aclScenariosController, streamSourceHandler);
        Whitebox.setInternalState(aclScenariosController, aclCalculationReportService);
        Whitebox.setInternalState(aclScenariosController, aclScenariosFilterController);
    }

    @Test
    public void testGetScenarios() {
        AclScenarioFilter filter = new AclScenarioFilter();
        List<AclScenario> scenarios = List.of(buildAclScenario());
        IAclScenariosFilterWidget aclScenariosFilterWidget = createMock(IAclScenariosFilterWidget.class);
        expect(aclScenariosFilterController.getWidget()).andReturn(aclScenariosFilterWidget).once();
        expect(aclScenariosFilterWidget.getAppliedFilter()).andReturn(filter).once();
        expect(aclScenarioService.getScenarios(filter)).andReturn(scenarios).once();
        replay(aclScenariosFilterController, aclScenariosFilterWidget, aclScenarioService);
        assertSame(scenarios, aclScenariosController.getScenarios());
        verify(aclScenariosFilterController, aclScenariosFilterWidget, aclScenarioService);
    }

    @Test
    public void testInstantiateWidget() {
        IAclScenariosWidget widget = aclScenariosController.instantiateWidget();
        assertNotNull(widget);
        assertEquals(AclScenariosWidget.class, widget.getClass());
    }

    @Test
    public void testGetScenarioWithAmountsAndLastAction() {
        AclScenarioDto scenario = new AclScenarioDto();
        scenario.setId(SCENARIO_UID);
        expect(aclScenarioUsageService.getAclScenarioWithAmountsAndLastAction(scenario.getId())).andReturn(scenario)
            .once();
        replay(aclScenarioUsageService);
        assertSame(scenario, aclScenariosController.getAclScenarioWithAmountsAndLastAction(scenario.getId()));
        verify(aclScenarioUsageService);
    }

    @Test
    public void testGetCriteriaHtmlRepresentation() {
        AclScenario scenario = new AclScenario();
        scenario.setUsageBatchId("c5df0a33-c734-4b56-acbe-468142c0fde1");
        scenario.setGrantSetId("34503049-9022-4c81-a70f-f8d5ff68602f");
        scenario.setFundPoolId("416ed2e4-b2e6-4a70-96fe-2182c4dc0929");
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        AclUsageBatch usageBatch = new AclUsageBatch();
        usageBatch.setName("ACL Usage Batch");
        expect(usageBatchService.getById(scenario.getUsageBatchId())).andReturn(usageBatch).once();
        AclGrantSet grantSet = new AclGrantSet();
        grantSet.setName("ACL Grant Set");
        expect(grantSetService.getById(scenario.getGrantSetId())).andReturn(grantSet).once();
        AclFundPool fundPool = new AclFundPool();
        fundPool.setName("ACL Fund Pool");
        expect(fundPoolService.getById(scenario.getFundPoolId())).andReturn(fundPool).once();
        replay(scenariosWidget, usageBatchService, grantSetService, fundPoolService);
        assertEquals("<b>Selection Criteria:</b><ul>" +
            "<li><b><i>Usage Batch </i></b>(ACL Usage Batch)</li>" +
            "<li><b><i>Grant Set </i></b>(ACL Grant Set)</li>" +
            "<li><b><i>Fund Pool </i></b>(ACL Fund Pool)</li>" +
            "</ul>", aclScenariosController.getCriteriaHtmlRepresentation());
        verify(scenariosWidget, usageBatchService, grantSetService, fundPoolService);
    }

    @Test
    public void testGetFundPoolsByLicenseTypeAndPeriod() {
        List<AclFundPool> fundPools = List.of(buildFundPool());
        expect(fundPoolService.getFundPoolsByLicenseTypeAndPeriod(LICENSE_TYPE, DISTRIBUTION_PERIOD))
            .andReturn(fundPools).once();
        replay(fundPoolService);
        assertSame(fundPools, aclScenariosController.getFundPoolsByLicenseTypeAndPeriod(LICENSE_TYPE,
            DISTRIBUTION_PERIOD));
        verify(fundPoolService);
    }

    @Test
    public void testGetUsageBatchesByPeriod() {
        List<AclUsageBatch> usageBatches = List.of(buildAclUsageBatch());
        expect(usageBatchService.getUsageBatchesByPeriod(DISTRIBUTION_PERIOD, true)).andReturn(usageBatches).once();
        replay(usageBatchService);
        assertSame(usageBatches, aclScenariosController.getUsageBatchesByPeriod(DISTRIBUTION_PERIOD, true));
        verify(usageBatchService);
    }

    @Test
    public void testAclScenarioExists() {
        expect(aclScenarioService.aclScenarioExists("ACL Scenario 2022")).andReturn(true).once();
        replay(aclScenarioService);
        assertTrue(aclScenariosController.aclScenarioExists("ACL Scenario 2022"));
        verify(aclScenarioService);
    }

    @Test
    public void testGetGrantSetsByLicenseTypeAndPeriod() {
        List<AclGrantSet> grantSets = List.of(buildAclGrantSet());
        expect(grantSetService.getGrantSetsByLicenseTypeAndPeriod(LICENSE_TYPE, DISTRIBUTION_PERIOD, true))
            .andReturn(grantSets).once();
        replay(grantSetService);
        assertSame(grantSets, aclScenariosController.getGrantSetsByLicenseTypeAndPeriod(LICENSE_TYPE,
            DISTRIBUTION_PERIOD, true));
        verify(grantSetService);
    }

    @Test
    public void testGetAclUsagePeriods() {
        List<Integer> periods = List.of(202106);
        expect(usageBatchService.getPeriods()).andReturn(periods).once();
        replay(usageBatchService);
        assertEquals(periods, aclScenariosController.getAllPeriods());
        verify(usageBatchService);
    }

    @Test
    public void testGetDetailLicenseeClasses() {
        List<DetailLicenseeClass> licenseeClasses = List.of(new DetailLicenseeClass());
        expect(licenseeClassService.getDetailLicenseeClasses(ACL_PRODUCT_FAMILY)).andReturn(licenseeClasses).once();
        replay(licenseeClassService);
        assertSame(licenseeClasses, aclScenariosController.getDetailLicenseeClasses());
        verify(licenseeClassService);
    }

    @Test
    public void testGetAggregateLicenseeClasses() {
        List<AggregateLicenseeClass> licenseeClasses = List.of(new AggregateLicenseeClass());
        expect(licenseeClassService.getAggregateLicenseeClasses(ACL_PRODUCT_FAMILY)).andReturn(licenseeClasses).once();
        replay(licenseeClassService);
        assertSame(licenseeClasses, aclScenariosController.getAggregateLicenseeClasses());
        verify(licenseeClassService);
    }

    @Test
    public void testCreateAclScenario() {
        List<UsageAge> usageAges = List.of();
        AclScenario scenario = new AclScenario();
        scenario.setUsageAges(usageAges);
        aclScenarioService.insertScenario(scenario);
        expectLastCall().once();
        replay(aclScenarioService, licenseeClassService, aclUsageService);
        aclScenariosController.createAclScenario(scenario);
        verify(aclScenarioService, licenseeClassService, aclUsageService);
    }

    @Test
    public void testGetAclScenarioController() {
        assertSame(aclScenarioController, aclScenariosController.getAclScenarioController());
    }

    @Test
    public void testIsValidUsageBatch() {
        expect(aclUsageService.getCountInvalidUsages(BATCH_UID, GRANT_SET_ID, DISTRIBUTION_PERIOD,
            PERIOD_PRIORS)).andReturn(0).once();
        replay(aclUsageService);
        assertTrue(aclScenariosController.isValidUsageBatch(BATCH_UID, GRANT_SET_ID, DISTRIBUTION_PERIOD,
            PERIOD_PRIORS));
        verify(aclUsageService);
    }

    @Test
    public void testGetCsvStreamSource() {
        OffsetDateTime now = OffsetDateTime.of(2022, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
        mockStatic(OffsetDateTime.class);
        expect(OffsetDateTime.now()).andReturn(now).once();
        Capture<Supplier<String>> fileNameSupplierCapture = newCapture();
        Capture<Consumer<PipedOutputStream>> posConsumerCapture = newCapture();
        String fileName = "detail_ids_missing_values_";
        Supplier<String> fileNameSupplier = () -> fileName;
        Supplier<InputStream> inputStreamSupplier =
            () -> IOUtils.toInputStream(StringUtils.EMPTY, StandardCharsets.UTF_8);
        expect(streamSourceHandler.getCsvStreamSource(capture(fileNameSupplierCapture), capture(posConsumerCapture)))
            .andReturn(new StreamSource(fileNameSupplier, "csv", inputStreamSupplier)).once();
        PipedOutputStream pos = new PipedOutputStream();
        aclUsageService.writeInvalidUsagesCsvReport(BATCH_UID, GRANT_SET_ID, DISTRIBUTION_PERIOD, PERIOD_PRIORS, pos);
        expectLastCall().once();
        replay(OffsetDateTime.class, streamSourceHandler, aclUsageService);
        IStreamSource streamSource = aclScenariosController.getInvalidUsagesStreamSource(BATCH_UID, GRANT_SET_ID,
            DISTRIBUTION_PERIOD, PERIOD_PRIORS);
        assertEquals("detail_ids_missing_values_01_02_2022_03_04.csv", streamSource.getSource().getKey().get());
        assertEquals(fileName, fileNameSupplierCapture.getValue().get());
        Consumer<PipedOutputStream> posConsumer = posConsumerCapture.getValue();
        posConsumer.accept(pos);
        verify(OffsetDateTime.class, streamSourceHandler, aclUsageService);
    }

    @Test
    public void testGetPublicationTypes() {
        List<PublicationType> publicationTypes = List.of();
        expect(publicationTypeService.getPublicationTypes(ACL_PRODUCT_FAMILY)).andReturn(publicationTypes).once();
        replay(publicationTypeService);
        assertSame(publicationTypes, aclScenariosController.getPublicationTypes());
        verify(publicationTypeService);
    }

    @Test
    public void testGetAclHistoricalPublicationTypes() {
        List<AclPublicationType> publicationTypes = List.of();
        expect(publicationTypeService.getAclHistoricalPublicationTypes()).andReturn(publicationTypes).once();
        replay(publicationTypeService);
        assertSame(publicationTypes, aclScenariosController.getAclHistoricalPublicationTypes());
        verify(publicationTypeService);
    }

    @Test
    public void testGetUsageAgeWeights() {
        UsageAge usageAge = buildUsageAge();
        expect(aclUsageService.getDefaultUsageAgesWeights()).andReturn(List.of(usageAge)).once();
        replay(aclUsageService);
        assertSame(usageAge, aclScenariosController.getUsageAgeWeights().get(0));
        verify(aclUsageService);
    }

    @Test
    public void testInsertAclHistoricalPublicationType() {
        AclPublicationType aclPublicationType = new AclPublicationType();
        publicationTypeService.insertAclHistoricalPublicationType(aclPublicationType);
        expectLastCall().once();
        replay(publicationTypeService);
        aclScenariosController.insertAclHistoricalPublicationType(aclPublicationType);
        verify(publicationTypeService);
    }

    @Test
    public void testGetFundPoolDetailsNotToBeDistributed() {
        String grantSetId = "b9f283cf-5d48-4c69-960a-cc30c24d2282";
        String fundPoolId = "b9f283cf-5d48-4c69-960a-cc30c24d2282";
        DetailLicenseeClass detailLicenseeClass = new DetailLicenseeClass();
        expect(aclScenarioService.getFundPoolDetailsNotToBeDistributed(BATCH_UID, fundPoolId, grantSetId,
            List.of(detailLicenseeClass))).andReturn(Set.of()).once();
        replay(aclScenarioService);
        assertEquals(Set.of(), aclScenariosController.getFundPoolDetailsNotToBeDistributed(
            BATCH_UID, fundPoolId, grantSetId, List.of(detailLicenseeClass)));
        verify(aclScenarioService);
    }

    @Test
    public void testGetScenarioById() {
        AclScenario scenario = buildAclScenario();
        expect(aclScenarioService.getScenarioById(SCENARIO_UID)).andReturn(scenario).once();
        replay(aclScenarioService);
        assertSame(scenario, aclScenariosController.getScenarioById(SCENARIO_UID));
        verify(aclScenarioService);
    }

    @Test
    public void testGetUsageAgeWeightsByScenarioId() {
        List<UsageAge> usageAges = List.of();
        expect(aclScenarioService.getUsageAgeWeightsByScenarioId(SCENARIO_UID)).andReturn(usageAges).once();
        replay(aclScenarioService);
        assertSame(usageAges, aclScenariosController.getUsageAgeWeightsByScenarioId(SCENARIO_UID));
        verify(aclScenarioService);
    }

    @Test
    public void testGetAclPublicationTypesByScenarioId() {
        List<AclPublicationType> publicationTypes = List.of();
        expect(aclScenarioService.getAclPublicationTypesByScenarioId(SCENARIO_UID)).andReturn(publicationTypes).once();
        replay(aclScenarioService);
        assertSame(publicationTypes, aclScenariosController.getAclPublicationTypesByScenarioId(SCENARIO_UID));
        verify(aclScenarioService);
    }

    @Test
    public void testGetDetailLicenseeClassesByScenarioId() {
        List<DetailLicenseeClass> licenseeClasses = List.of();
        expect(aclScenarioService.getDetailLicenseeClassesByScenarioId(SCENARIO_UID)).andReturn(licenseeClasses).once();
        replay(aclScenarioService);
        assertSame(licenseeClasses, aclScenariosController.getDetailLicenseeClassesByScenarioId(SCENARIO_UID));
        verify(aclScenarioService);
    }

    @Test
    public void testOnDeleteButtonClicked() {
        mockStatic(Windows.class);
        Capture<IListener> listenerCapture = newCapture();
        AclScenario scenario = buildAclScenario();
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        expect(Windows.showConfirmDialog(
            eq("Are you sure you want to delete <i><b>'" + "ACL Scenario name" + "'</b></i> scenario?"),
            capture(listenerCapture))).andReturn(null).once();
        aclScenarioService.deleteAclScenario(scenario);
        expectLastCall().once();
        scenariosWidget.refresh();
        expectLastCall().once();
        replay(scenariosWidget, aclScenarioService, Windows.class);
        aclScenariosController.onDeleteButtonClicked();
        listenerCapture.getValue().onActionConfirmed();
        verify(scenariosWidget, aclScenarioService, Windows.class);
    }

    @Test
    public void testGetExportAclSummaryOfWorkSharesByAggLcStreamSource() {
        OffsetDateTime date = OffsetDateTime.of(2022, 10, 6, 3, 20, 20, 6, ZoneOffset.ofHours(0));
        mockStatic(OffsetDateTime.class);
        Capture<Supplier<String>> fileNameSupplierCapture = newCapture();
        Capture<Consumer<PipedOutputStream>> posConsumerCapture = newCapture();
        String fileName = "summary_of_work_shares_by_aggregate_licensee_class_report_";
        Supplier<String> fileNameSupplier = () -> fileName;
        Supplier<InputStream> inputStreamSupplier =
            () -> IOUtils.toInputStream(StringUtils.EMPTY, StandardCharsets.UTF_8);
        PipedOutputStream pos = new PipedOutputStream();
        expect(OffsetDateTime.now()).andReturn(date).once();
        expect(streamSourceHandler.getCsvStreamSource(capture(fileNameSupplierCapture), capture(posConsumerCapture)))
            .andReturn(new StreamSource(fileNameSupplier, "csv", inputStreamSupplier)).once();
        aclCalculationReportService.writeSummaryOfWorkSharesByAggLcCsvReport(null, pos);
        expectLastCall().once();
        replay(OffsetDateTime.class, streamSourceHandler, aclCalculationReportService);
        IStreamSource streamSource =
            aclScenariosController.getExportAclSummaryOfWorkSharesByAggLcStreamSource();
        assertEquals("summary_of_work_shares_by_aggregate_licensee_class_report_10_06_2022_03_20.csv",
            streamSource.getSource().getKey().get());
        assertEquals(fileName, fileNameSupplierCapture.getValue().get());
        Consumer<PipedOutputStream> posConsumer = posConsumerCapture.getValue();
        posConsumer.accept(pos);
        verify(OffsetDateTime.class, streamSourceHandler, aclCalculationReportService);
    }

    @Test
    public void testCanUserApproveAclScenarioUserIsSubmitter() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        List<ScenarioAuditItem> auditItems = List.of(
            buildScenarioAuditItem(ScenarioActionTypeEnum.SUBMITTED, USER_NAME),
            buildScenarioAuditItem(ScenarioActionTypeEnum.ADDED_USAGES, USER_NAME));
        expect(aclScenarioHistoryController.getActions(SCENARIO_UID)).andReturn(auditItems).once();
        replay(RupContextUtils.class, aclScenarioHistoryController);
        assertFalse(aclScenariosController.canUserApproveAclScenario(SCENARIO_UID));
        verify(RupContextUtils.class, aclScenarioHistoryController);
    }

    @Test
    public void testCanUserApproveAclScenarioUserIsNotSubmitter() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        List<ScenarioAuditItem> auditItems = List.of(
            buildScenarioAuditItem(ScenarioActionTypeEnum.SUBMITTED, "ajohn@copyright.com"),
            buildScenarioAuditItem(ScenarioActionTypeEnum.ADDED_USAGES, USER_NAME));
        expect(aclScenarioHistoryController.getActions(SCENARIO_UID)).andReturn(auditItems).once();
        replay(RupContextUtils.class, aclScenarioHistoryController);
        assertTrue(aclScenariosController.canUserApproveAclScenario(SCENARIO_UID));
        verify(RupContextUtils.class, aclScenarioHistoryController);
    }

    @Test
    public void testCanUserApproveAclScenarioUserIsNotLastSubmitter() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        List<ScenarioAuditItem> auditItems = List.of(
            buildScenarioAuditItem(ScenarioActionTypeEnum.SUBMITTED, "ajohn@copyright.com"),
            buildScenarioAuditItem(ScenarioActionTypeEnum.REJECTED, USER_NAME),
            buildScenarioAuditItem(ScenarioActionTypeEnum.SUBMITTED, USER_NAME),
            buildScenarioAuditItem(ScenarioActionTypeEnum.ADDED_USAGES, USER_NAME));
        expect(aclScenarioHistoryController.getActions(SCENARIO_UID)).andReturn(auditItems).once();
        replay(RupContextUtils.class, aclScenarioHistoryController);
        assertTrue(aclScenariosController.canUserApproveAclScenario(SCENARIO_UID));
        verify(RupContextUtils.class, aclScenarioHistoryController);
    }

    @Test
    public void testHandleAction() {
        expect(scenariosWidget.getSelectedScenario()).andReturn(aclScenario).once();
        expect(aclScenarioService.isNotExistsSubmittedScenario(aclScenario)).andReturn(true).once();
        mockStatic(Windows.class);
        Windows.showConfirmDialogWithReason(eq("Confirm action"), eq("Are you sure you want to perform action?"),
            eq("Yes"), eq("Cancel"), anyObject(ConfirmActionDialogWindow.IListener.class), anyObject(Validator.class));
        expectLastCall().once();
        replay(Windows.class, aclScenarioService, scenariosWidget);
        aclScenariosController.handleAction(ScenarioActionTypeEnum.SUBMITTED);
        verify(Windows.class, aclScenarioService, scenariosWidget);
    }

    @Test
    public void testHandleActionApproved() {
        expect(scenariosWidget.getSelectedScenario()).andReturn(aclScenario).once();
        expect(aclScenarioService.isNotExistsSubmittedScenario(aclScenario)).andReturn(true).once();
        mockStatic(Windows.class);
        Windows.showConfirmDialogWithReason(eq("Confirm action"), eq("Are you sure you want to perform action?"),
            eq("Yes"), eq("Cancel"), anyObject(ConfirmActionDialogWindow.IListener.class), anyObject(Validator.class));
        expectLastCall().once();
        replay(Windows.class, aclScenarioService, scenariosWidget);
        aclScenariosController.handleAction(ScenarioActionTypeEnum.APPROVED);
        verify(Windows.class, aclScenarioService, scenariosWidget);
    }

    @Test
    public void testHandleActionRejected() {
        expect(scenariosWidget.getSelectedScenario()).andReturn(aclScenario).once();
        expect(aclScenarioService.isNotExistsSubmittedScenario(aclScenario)).andReturn(true).once();
        mockStatic(Windows.class);
        Windows.showConfirmDialogWithReason(eq("Confirm action"), eq("Are you sure you want to perform action?"),
            eq("Yes"), eq("Cancel"), anyObject(ConfirmActionDialogWindow.IListener.class), anyObject(Validator.class));
        expectLastCall().once();
        replay(Windows.class, aclScenarioService, scenariosWidget);
        aclScenariosController.handleAction(ScenarioActionTypeEnum.REJECTED);
        verify(Windows.class, aclScenarioService, scenariosWidget);
    }

    @Test
    public void testApplyScenarioAction() {
        expect(scenariosWidget.getSelectedScenario()).andReturn(aclScenario).once();
        scenariosWidget.refresh();
        expectLastCall().once();
        IAclScenarioActionHandler handler = createMock(IAclScenarioActionHandler.class);
        handler.handleAction(aclScenario, "reason");
        expectLastCall().once();
        replay(scenariosWidget, handler);
        aclScenariosController.applyScenarioAction(handler, "reason");
        verify(scenariosWidget, handler);
    }

    @Test
    public void testHandleActionNull() {
        expect(scenariosWidget.getSelectedScenario()).andReturn(aclScenario).once();
        expect(aclScenarioService.isNotExistsSubmittedScenario(aclScenario)).andReturn(true).once();
        mockStatic(Windows.class);
        replay(scenariosWidget, Windows.class, aclScenarioService);
        aclScenariosController.handleAction(null);
        verify(scenariosWidget, Windows.class, aclScenarioService);
    }

    @Test
    public void testHandleActionSubmittedScenario() {
        expect(scenariosWidget.getSelectedScenario()).andReturn(aclScenario).once();
        expect(aclScenarioService.isNotExistsSubmittedScenario(aclScenario)).andReturn(false).once();
        mockStatic(Windows.class);
        Windows.showNotificationWindow(
            eq("This scenario cannot be submitted for approval because scenario in 202212 period " +
                "and ACL license type has been already submitted"));
        expectLastCall().once();
        replay(scenariosWidget, Windows.class, aclScenarioService);
        aclScenariosController.handleAction(null);
        verify(scenariosWidget, Windows.class, aclScenarioService);
    }

    @Test
    public void testEditAclScenarioName() {
        String id = aclScenario.getId();
        String newName = "New scenario name";
        aclScenarioService.updateName(id, newName);
        expectLastCall().once();
        replay(aclScenarioService);
        aclScenariosController.editAclScenarioName(id, newName);
        verify(aclScenarioService);
    }

    private AclScenario buildAclScenario() {
        AclScenario scenario = new AclScenario();
        scenario.setId(SCENARIO_UID);
        scenario.setName("ACL Scenario name");
        scenario.setDescription("Description");
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        scenario.setEditableFlag(false);
        scenario.setPeriodEndDate(DISTRIBUTION_PERIOD);
        scenario.setLicenseType(LICENSE_TYPE);
        scenario.setCreateDate(Date.from(LocalDate.of(2022, 6, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        scenario.setCreateUser("user@copyright.com");
        return scenario;
    }

    private AclGrantSet buildAclGrantSet() {
        AclGrantSet grantSet = new AclGrantSet();
        grantSet.setName("ACL_GRANT_SET_NAME");
        grantSet.setGrantPeriod(202112);
        grantSet.setPeriods(Set.of(202106, 202112));
        grantSet.setLicenseType(LICENSE_TYPE);
        grantSet.setEditable(true);
        return grantSet;
    }

    private AclFundPool buildFundPool() {
        AclFundPool aclFundPool = new AclFundPool();
        aclFundPool.setName("Fund Pool Name");
        aclFundPool.setManualUploadFlag(true);
        aclFundPool.setLicenseType(LICENSE_TYPE);
        return aclFundPool;
    }

    private AclUsageBatch buildAclUsageBatch() {
        AclUsageBatch usageBatch = new AclUsageBatch();
        usageBatch.setName("ACL_USAGE_BATCH_NAME");
        usageBatch.setDistributionPeriod(202112);
        usageBatch.setPeriods(Set.of(202106, 202112));
        usageBatch.setEditable(true);
        return usageBatch;
    }

    private UsageAge buildUsageAge() {
        UsageAge usageAge = new UsageAge();
        usageAge.setPeriod(1);
        usageAge.setWeight(new BigDecimal("0.57"));
        return usageAge;
    }

    private ScenarioAuditItem buildScenarioAuditItem(ScenarioActionTypeEnum actionType, String createUser) {
        ScenarioAuditItem auditItem = new ScenarioAuditItem();
        auditItem.setActionType(actionType);
        auditItem.setCreateUser(createUser);
        return auditItem;
    }
}
