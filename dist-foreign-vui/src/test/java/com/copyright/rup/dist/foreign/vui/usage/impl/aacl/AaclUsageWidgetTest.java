package com.copyright.rup.dist.foreign.vui.usage.impl.aacl;

import static com.copyright.rup.dist.foreign.vui.GridColumnVerifier.verifyEnumColumn;
import static com.copyright.rup.dist.foreign.vui.GridColumnVerifier.verifyIntegerColumn;
import static com.copyright.rup.dist.foreign.vui.GridColumnVerifier.verifyLocalDateColumnShortFormat;
import static com.copyright.rup.dist.foreign.vui.GridColumnVerifier.verifyLongColumn;
import static com.copyright.rup.dist.foreign.vui.GridColumnVerifier.verifyStringColumn;
import static com.copyright.rup.dist.foreign.vui.IVaadinComponentFinder.getButton;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getDialogContent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButton;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyFileDownloader;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyMenuBar;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.newCapture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.expectNew;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.AaclUsage;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.vui.UiTestHelper;
import com.copyright.rup.dist.foreign.vui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.vui.usage.api.ICommonUsageFilterController;
import com.copyright.rup.dist.foreign.vui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.dist.foreign.vui.usage.api.aacl.IAaclUsageFilterController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.NotificationWindow;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.apache.commons.lang3.tuple.Pair;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Verifies {@link AaclUsageWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/23/2019
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({AaclUsageWidget.class, ForeignSecurityUtils.class, Windows.class, UI.class})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class AaclUsageWidgetTest {

    private static final String DATE =
        CommonDateUtils.format(LocalDate.now(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT);
    private static final String BATCH_ID = "3a070817-03ae-4ebd-b25f-dd3168a7ffb0";
    private static final String AACL_PRODUCT_FAMILY = "AACL";
    private static final String AACL_SCENARIO_NAME_PREFIX = "AACL Distribution ";
    private static final String LOAD = "Load";
    private static final String VIEW = "View";
    private static final String ADD_TO_SCENARIO = "Add To Scenario";
    private static final String WIDTH_140 = "140px";

    private AaclUsageWidget widget;
    private IAaclUsageController controller;

    @Before
    public void setUp() {
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).times(2);
        expect(ui.getUIId()).andReturn(1).times(2);
        controller = createMock(IAaclUsageController.class);
        var filterWidget = new AaclUsageFilterWidget(createMock(IAaclUsageFilterController.class));
        filterWidget.getAppliedFilter().setUsageBatchesIds(Set.of(BATCH_ID));
        ICommonUsageFilterController filterController = createMock(ICommonUsageFilterController.class);
        expect(controller.getUsageFilterController()).andReturn(filterController).once();
        expect(filterController.initWidget()).andReturn(filterWidget).once();
        IStreamSource streamSource = createMock(IStreamSource.class);
        Map.Entry<Supplier<String>, Supplier<InputStream>> source =
            new SimpleImmutableEntry<>(() -> "file_name.txt", () -> new ByteArrayInputStream(new byte[]{}));
        expect(streamSource.getSource()).andReturn(source).times(2);
        expect(controller.getExportUsagesStreamSource()).andReturn(streamSource).once();
        expect(controller.getSendForClassificationUsagesStreamSource()).andReturn(streamSource).once();
        replay(UI.class, ui, controller, filterController, streamSource);
        widget = new AaclUsageWidget(controller);
        widget.setController(controller);
        widget.init();
        verify(UI.class, ui, controller, filterController, streamSource);
        reset(UI.class, ui, controller, filterController, streamSource);
    }

    @Test
    public void testWidgetStructure() {
        assertThat(widget.getPrimaryComponent(), instanceOf(AaclUsageFilterWidget.class));
        var secondaryComponent = widget.getSecondaryComponent();
        assertThat(secondaryComponent, instanceOf(VerticalLayout.class));
        var contentLayout = (VerticalLayout) secondaryComponent;
        var toolbarLayout = (HorizontalLayout) contentLayout.getComponentAt(0);
        verifyButtonsLayout((HorizontalLayout) toolbarLayout.getComponentAt(0));
        verifyGrid((Grid<UsageDto>) contentLayout.getComponentAt(1));
    }

    @Test
    public void testGetController() {
        assertSame(controller, widget.getController());
    }

    @Test
    public void testAddToScenarioButtonClickListener() {
        mockStatic(Windows.class);
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).times(6);
        expect(ui.getLocale()).andReturn(null).times(3);
        var addToScenarioButton = getButton(widget, ADD_TO_SCENARIO);
        expect(controller.getBeansCount()).andReturn(1).once();
        prepareCreateScenarioExpectation();
        expect(controller.isValidFilteredUsageStatus(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(List.of()).once();
        expect(controller.getProcessingBatchesNames(Set.of(BATCH_ID))).andReturn(List.of()).once();
        expect(controller.getBatchesNamesToScenariosNames(Set.of(BATCH_ID))).andReturn(Map.of()).once();
        expect(controller.getIneligibleBatchesNames(Set.of(BATCH_ID))).andReturn(List.of()).once();
        Windows.showModalWindow(anyObject(CreateAaclScenarioWindow.class));
        expectLastCall().once();
        replay(controller, Windows.class, UI.class, ui);
        addToScenarioButton.click();
        verify(controller, Windows.class, UI.class, ui);
    }

    @Test
    public void testAddToScenarioButtonClickListenerWithEmptyUsages() {
        mockStatic(Windows.class);
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).times(6);
        expect(ui.getLocale()).andReturn(null).times(3);
        var addToScenarioButton = getButton(widget, ADD_TO_SCENARIO);
        expect(controller.getBeansCount()).andReturn(0).once();
        prepareCreateScenarioExpectation();
        Windows.showNotificationWindow("Scenario cannot be created. There are no usages to include into scenario");
        expectLastCall().once();
        replay(controller, Windows.class, UI.class, ui);
        addToScenarioButton.click();
        verify(controller, Windows.class, UI.class, ui);
    }

    @Test
    public void testAddToScenarioButtonClickListenerWithInvalidStatus() {
        mockStatic(Windows.class);
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).times(6);
        expect(ui.getLocale()).andReturn(null).times(3);
        var addToScenarioButton = getButton(widget, ADD_TO_SCENARIO);
        prepareCreateScenarioExpectation();
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.isValidFilteredUsageStatus(UsageStatusEnum.ELIGIBLE)).andReturn(false).once();
        Windows.showNotificationWindow("Only usages in ELIGIBLE status can be added to scenario");
        expectLastCall().once();
        replay(controller, Windows.class, UI.class, ui);
        addToScenarioButton.click();
        verify(controller, Windows.class, UI.class, ui);
    }

    @Test
    public void testAddToScenarioButtonClickListenerWithInvalidRightsholders() {
        mockStatic(Windows.class);
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).times(6);
        expect(ui.getLocale()).andReturn(null).times(3);
        var addToScenarioButton = getButton(widget, ADD_TO_SCENARIO);
        prepareCreateScenarioExpectation();
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.isValidFilteredUsageStatus(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(List.of(700000000L)).once();
        Windows.showNotificationWindow(
            "Scenario cannot be created. The following rightsholder(s) are absent in PRM: <i><b>[700000000]</b></i>");
        expectLastCall().once();
        replay(controller, Windows.class, UI.class, ui);
        addToScenarioButton.click();
        verify(controller, Windows.class, UI.class, ui);
    }

    @Test
    public void testAddToScenarioButtonClickListenerWithProcessingBatches() {
        mockStatic(Windows.class);
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).times(6);
        expect(ui.getLocale()).andReturn(null).times(3);
        var addToScenarioButton = getButton(widget, ADD_TO_SCENARIO);
        prepareCreateScenarioExpectation();
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.isValidFilteredUsageStatus(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(List.of()).once();
        expect(controller.getProcessingBatchesNames(Set.of(BATCH_ID))).andReturn(List.of("Batch Name")).once();
        Windows.showNotificationWindow("Please wait while batch(es) processing is completed:" +
            "<ul><li><i><b>Batch Name</b></i></ul>");
        expectLastCall().once();
        replay(controller, Windows.class, UI.class, ui);
        addToScenarioButton.click();
        verify(controller, Windows.class, UI.class, ui);
    }

    @Test
    public void testAddToScenarioButtonClickListenerWithAttachedToScenarioBatches() {
        mockStatic(Windows.class);
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).times(6);
        expect(ui.getLocale()).andReturn(null).times(3);
        var addToScenarioButton = getButton(widget, ADD_TO_SCENARIO);
        prepareCreateScenarioExpectation();
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.isValidFilteredUsageStatus(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(List.of()).once();
        expect(controller.getProcessingBatchesNames(Set.of(BATCH_ID))).andReturn(List.of()).once();
        expect(controller.getBatchesNamesToScenariosNames(Set.of(BATCH_ID)))
            .andReturn(Map.of("Batch Name", "Scenario Name")).once();
        Windows.showNotificationWindow("The following batch(es) already associated with scenario(s):" +
            "<ul><li><i><b>Batch Name : Scenario Name</b></i></ul>");
        expectLastCall().once();
        replay(controller, Windows.class, UI.class, ui);
        addToScenarioButton.click();
        verify(controller, Windows.class, UI.class, ui);
    }

    @Test
    public void testAddToScenarioButtonClickListenerWithIneligibleBatches() {
        mockStatic(Windows.class);
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).times(6);
        expect(ui.getLocale()).andReturn(null).times(3);
        var addToScenarioButton = getButton(widget, ADD_TO_SCENARIO);
        prepareCreateScenarioExpectation();
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.isValidFilteredUsageStatus(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(List.of()).once();
        expect(controller.getProcessingBatchesNames(Set.of(BATCH_ID))).andReturn(List.of()).once();
        expect(controller.getBatchesNamesToScenariosNames(Set.of(BATCH_ID))).andReturn(Map.of()).once();
        expect(controller.getIneligibleBatchesNames(Set.of(BATCH_ID))).andReturn(List.of("Batch Name")).once();
        Windows.showNotificationWindow("The following batches have usages that are not in ELIGIBLE status:" +
            "<ul><li><i><b>Batch Name</b></i></ul>");
        expectLastCall().once();
        replay(controller, Windows.class, UI.class, ui);
        addToScenarioButton.click();
        verify(controller, Windows.class, UI.class, ui);
    }

    @Test
    public void testInitMediator() throws Exception {
        AaclUsageMediator mediator = createMock(AaclUsageMediator.class);
        expectNew(AaclUsageMediator.class).andReturn(mediator).once();
        mediator.setLoadUsageBatchMenuItem(anyObject(MenuItem.class));
        expectLastCall().once();
        mediator.setLoadFundPoolMenuItem(anyObject(MenuItem.class));
        expectLastCall().once();
        mediator.setSendForClassificationButton(anyObject(Button.class));
        expectLastCall().once();
        mediator.setSendForClassificationDownloader(anyObject(OnDemandFileDownloader.class));
        expectLastCall().once();
        mediator.setLoadClassifiedUsagesButton(anyObject(Button.class));
        expectLastCall().once();
        mediator.setAddToScenarioButton(anyObject(Button.class));
        expectLastCall().once();
        replay(AaclUsageMediator.class, mediator, controller);
        assertNotNull(widget.initMediator());
        verify(AaclUsageMediator.class, mediator, controller);
    }

    @Test
    public void testLoadUsageBatch() {
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).times(12);
        expect(ui.getLocale()).andReturn(null).times(6);
        mockStatic(Windows.class);
        Windows.showModalWindow(anyObject(AaclUsageBatchUploadWindow.class));
        expectLastCall().once();
        replay(UI.class, ui, controller, Windows.class);
        var menuItems = getMenuBar(0).getItems();
        assertEquals(1, menuItems.size());
        var menuItem = menuItems.get(0);
        assertEquals("Usage Batch", menuItem.getText());
        var subMenuItems = menuItem.getSubMenu().getItems();
        assertEquals(2, subMenuItems.size());
        var subMenuItem = subMenuItems.get(0);
        assertEquals(LOAD, subMenuItem.getText());
        clickMenuItem(subMenuItem);
        verify(UI.class, ui, controller, Windows.class);
    }

    @Test
    public void testViewUsageBatch() {
        mockStatic(UI.class);
        expect(controller.getSelectedProductFamily()).andReturn(AACL_PRODUCT_FAMILY).once();
        expect(controller.getUsageBatches(AACL_PRODUCT_FAMILY)).andReturn(List.of()).once();
        mockStatic(ForeignSecurityUtils.class);
        expect(ForeignSecurityUtils.hasDeleteUsagePermission()).andReturn(true).once();
        mockStatic(Windows.class);
        Windows.showModalWindow(anyObject(ViewAaclUsageBatchWindow.class));
        expectLastCall().once();
        replay(controller, ForeignSecurityUtils.class, Windows.class);
        var menuItems = getMenuBar(0).getItems();
        assertEquals(1, menuItems.size());
        var menuItem = menuItems.get(0);
        assertEquals("Usage Batch", menuItem.getText());
        var subMenuItems = menuItem.getSubMenu().getItems();
        assertEquals(2, subMenuItems.size());
        var subMenuItem = subMenuItems.get(1);
        assertEquals(VIEW, subMenuItem.getText());
        clickMenuItem(subMenuItem);
        verify(controller, ForeignSecurityUtils.class, Windows.class);
    }

    @Test
    public void testLoadFundPool() {
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).times(4);
        expect(ui.getLocale()).andReturn(null).times(2);
        mockStatic(Windows.class);
        Windows.showModalWindow(anyObject(AaclFundPoolUploadWindow.class));
        expectLastCall().once();
        replay(UI.class, ui, controller, Windows.class);
        var menuItems = getMenuBar(1).getItems();
        assertEquals(1, menuItems.size());
        var menuItem = menuItems.get(0);
        assertEquals("Fund Pool", menuItem.getText());
        var subMenuItems = menuItem.getSubMenu().getItems();
        assertEquals(2, subMenuItems.size());
        var subMenuItem = subMenuItems.get(0);
        assertEquals(LOAD, subMenuItem.getText());
        clickMenuItem(subMenuItem);
        verify(UI.class, ui, controller, Windows.class);
    }

    @Test
    public void testViewFundPool() {
        mockStatic(UI.class);
        expect(controller.getFundPools()).andReturn(List.of()).once();
        mockStatic(ForeignSecurityUtils.class);
        expect(ForeignSecurityUtils.hasDeleteFundPoolPermission()).andReturn(true).once();
        mockStatic(Windows.class);
        Windows.showModalWindow(anyObject(ViewAaclFundPoolWindow.class));
        expectLastCall().once();
        replay(controller, ForeignSecurityUtils.class, Windows.class);
        var menuItems = getMenuBar(1).getItems();
        assertEquals(1, menuItems.size());
        var menuItem = menuItems.get(0);
        assertEquals("Fund Pool", menuItem.getText());
        var subMenuItems = menuItem.getSubMenu().getItems();
        assertEquals(2, subMenuItems.size());
        var subMenuItem = subMenuItems.get(1);
        assertEquals(VIEW, subMenuItem.getText());
        clickMenuItem(subMenuItem);
        verify(controller, ForeignSecurityUtils.class, Windows.class);
    }

    @Test
    public void testSendForClassification() {
        mockStatic(Windows.class);
        Capture<Dialog> notificationWindowCapture = newCapture();
        Button sendForClassificationButton = Whitebox.getInternalState(widget, "sendForClassificationButton");
        expect(controller.isValidForClassification()).andReturn(true).once();
        Windows.showModalWindow(capture(notificationWindowCapture));
        expectLastCall().once();
        replay(controller, Windows.class);
        sendForClassificationButton.click();
        var notificationWindow = notificationWindowCapture.getValue();
        assertThat(notificationWindow, instanceOf(NotificationWindow.class));
        assertEquals("<span>File download is in progress. Please wait</span>",
            ((VerticalLayout) getDialogContent(notificationWindow)).getComponentAt(0).getElement().toString());
        verify(controller, Windows.class);
    }

    @Test
    public void testSendForClassificationInvalidUsagesState() {
        mockStatic(Windows.class);
        Button sendForClassificationButton = Whitebox.getInternalState(widget, "sendForClassificationButton");
        var grid = new Grid<>();
        Whitebox.setInternalState(widget, grid);
        expect(controller.isValidForClassification()).andReturn(false).once();
        Windows.showNotificationWindow("Only non baseline usages in RH_FOUND status can be sent for classification");
        expectLastCall().once();
        replay(Windows.class, controller);
        sendForClassificationButton.click();
        verify(Windows.class, controller);
    }

    @Test
    public void testLoadClassifiedUsagesButtonClickListener() {
        mockStatic(Windows.class);
        Button loadClassifiedUsagesButton = Whitebox.getInternalState(widget, "loadClassifiedUsagesButton");
        assertTrue(loadClassifiedUsagesButton.isDisableOnClick());
        Windows.showModalWindow(anyObject(ClassifiedUsagesUploadWindow.class));
        expectLastCall().once();
        replay(Windows.class, controller);
        loadClassifiedUsagesButton.click();
        verify(Windows.class, controller);
    }

    private void verifyButtonsLayout(HorizontalLayout buttonsLayout) {
        assertEquals(6, buttonsLayout.getComponentCount());
        verifyMenuBar(buttonsLayout.getComponentAt(0), "Usage Batch", true, List.of(LOAD, VIEW));
        verifyMenuBar(buttonsLayout.getComponentAt(1), "Fund Pool", true, List.of(LOAD, VIEW));
        verifyFileDownloader(buttonsLayout.getComponentAt(2), "Send for Classification", true, true);
        verifyButton(buttonsLayout.getComponentAt(3), "Load Classified Details", true, true);
        verifyButton(buttonsLayout.getComponentAt(4), ADD_TO_SCENARIO, true, true);
        verifyFileDownloader(buttonsLayout.getComponentAt(5), "Export", true, true);
    }

    private void verifyGrid(Grid<UsageDto> grid) {
        UiTestHelper.verifyGrid(grid, List.of(
            Pair.of("Detail ID", "300px"),
            Pair.of("Detail Status", "180px"),
            Pair.of("Product Family", "160px"),
            Pair.of("Usage Batch Name", "200px"),
            Pair.of("Period End Date", "155px"),
            Pair.of("RH Account #", "150px"),
            Pair.of("RH Name", "300px"),
            Pair.of("Wr Wrk Inst", "140px"),
            Pair.of("System Title", "300px"),
            Pair.of("Standard Number", "180px"),
            Pair.of("Standard Number Type", "225px"),
            Pair.of("Det LC ID", "105px"),
            Pair.of("Det LC Enrollment", "180px"),
            Pair.of("Det LC Discipline", "155px"),
            Pair.of("Pub Type", WIDTH_140),
            Pair.of("Institution", WIDTH_140),
            Pair.of("Usage Period", "135px"),
            Pair.of("Usage Source", WIDTH_140),
            Pair.of("Number of Copies", "185px"),
            Pair.of("Number of Pages", "165px"),
            Pair.of("Right Limitation", "160px"),
            Pair.of("Comment", "200px")));
        assertEquals(22, grid.getColumns().size());
        Supplier<UsageDto> itemSupplier = () -> {
            var usage = new UsageDto();
            usage.setAaclUsage(new AaclUsage());
            return usage;
        };
        verifyStringColumn(grid, 0, UsageDto::new, UsageDto::setId);
        verifyEnumColumn(grid, 1, UsageDto::new, UsageDto::setStatus,
            UsageStatusEnum.NEW, UsageStatusEnum.ARCHIVED);
        verifyStringColumn(grid, 2, UsageDto::new, UsageDto::setProductFamily);
        verifyStringColumn(grid, 3, UsageDto::new, UsageDto::setBatchName);
        verifyLocalDateColumnShortFormat(grid, 4, itemSupplier,
            (bean, value) -> bean.getAaclUsage().setBatchPeriodEndDate(value));
        verifyLongColumn(grid, 5, UsageDto::new, UsageDto::setRhAccountNumber);
        verifyStringColumn(grid, 6, UsageDto::new, UsageDto::setRhName);
        verifyLongColumn(grid, 7, UsageDto::new, UsageDto::setWrWrkInst);
        verifyStringColumn(grid, 8, UsageDto::new, UsageDto::setSystemTitle);
        verifyStringColumn(grid, 9, UsageDto::new, UsageDto::setStandardNumber);
        verifyStringColumn(grid, 10, UsageDto::new, UsageDto::setStandardNumberType);
        verifyIntegerColumn(grid, 11, itemSupplier,
            (bean, value) -> bean.getAaclUsage().getDetailLicenseeClass().setId(value));
        verifyStringColumn(grid, 12, itemSupplier,
            (bean, value) -> bean.getAaclUsage().getDetailLicenseeClass().setEnrollmentProfile(value));
        verifyStringColumn(grid, 13, itemSupplier,
            (bean, value) -> bean.getAaclUsage().getDetailLicenseeClass().setDiscipline(value));
        verifyStringColumn(grid, 14, itemSupplier,
            (bean, value) -> bean.getAaclUsage().getPublicationType().setName(value));
        verifyStringColumn(grid, 15, itemSupplier,
            (bean, value) -> bean.getAaclUsage().setInstitution(value));
        verifyIntegerColumn(grid, 16, itemSupplier,
            (bean, value) -> bean.getAaclUsage().getUsageAge().setPeriod(value));
        verifyStringColumn(grid, 17, itemSupplier,
            (bean, value) -> bean.getAaclUsage().setUsageSource(value));
        verifyIntegerColumn(grid, 18, UsageDto::new, UsageDto::setNumberOfCopies);
        verifyIntegerColumn(grid, 19, itemSupplier,
            (bean, value) -> bean.getAaclUsage().setNumberOfPages(value));
        verifyStringColumn(grid, 20, itemSupplier,
            (bean, value) -> bean.getAaclUsage().setRightLimitation(value));
        verifyStringColumn(grid, 21, UsageDto::new, UsageDto::setComment);
    }

    private void prepareCreateScenarioExpectation() {
        expect(controller.getSelectedProductFamily()).andReturn(AACL_PRODUCT_FAMILY).once();
        expect(controller.getUsageAges()).andReturn(List.of(new UsageAge())).once();
        expect(controller.getPublicationTypes()).andReturn(List.of(new PublicationType())).once();
        expect(controller.getDetailLicenseeClasses()).andReturn(List.of(new DetailLicenseeClass())).once();
        expect(controller.getFundPoolsNotAttachedToScenario()).andReturn(List.of(new FundPool())).once();
        expect(controller.scenarioExists(AACL_SCENARIO_NAME_PREFIX + DATE)).andReturn(false).once();
    }

    private MenuBar getMenuBar(int toolbarIdx) {
        var secondaryComponent = (VerticalLayout) widget.getSecondaryComponent();
        var contentLayout = (HorizontalLayout) secondaryComponent.getComponentAt(0);
        var toolbarLayout = (HorizontalLayout) contentLayout.getComponentAt(0);
        return (MenuBar) toolbarLayout.getComponentAt(toolbarIdx);
    }

    private void clickMenuItem(MenuItem menuItem) {
        var eventListeners = (List<ComponentEventListener<ClickEvent<MenuItem>>>)
            ComponentUtil.getListeners(menuItem, ClickEvent.class);
        assertEquals(1, eventListeners.size());
        var eventListener = eventListeners.get(0);
        eventListener.onComponentEvent(createMock(ClickEvent.class));
    }
}
