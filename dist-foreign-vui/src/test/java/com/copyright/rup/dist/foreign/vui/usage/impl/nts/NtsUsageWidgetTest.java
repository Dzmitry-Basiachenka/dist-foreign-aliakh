package com.copyright.rup.dist.foreign.vui.usage.impl.nts;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButton;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyFileDownloader;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyMenuBar;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.expectNew;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.vui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.vui.usage.api.IFasNtsUsageFilterController;
import com.copyright.rup.dist.foreign.vui.usage.api.nts.INtsUsageController;
import com.copyright.rup.dist.foreign.vui.usage.impl.FasNtsUsageFilterWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Verifies {@link NtsUsageWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/10/2019
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({NtsUsageWidget.class, Dialog.class, ForeignSecurityUtils.class, Windows.class, UI.class})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
//TODO {aliakh} adjust tests if necessary
//TODO {aliakh} use the 'var' keyword
public class NtsUsageWidgetTest {

    private NtsUsageWidget widget;
    private INtsUsageController controller;

    @Before
    public void setUp() {
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).times(2);
        expect(ui.getUIId()).andReturn(1).times(2);
        controller = createMock(INtsUsageController.class);
        widget = new NtsUsageWidget(controller);
        widget.setController(controller);
        var filterWidget = new FasNtsUsageFilterWidget(createMock(IFasNtsUsageFilterController.class));
        filterWidget.getFilter().setUsageBatchesIds(Set.of("e0f2287a-f7f4-437f-95ad-56bd1b1c51cf"));
        expect(controller.initUsagesFilterWidget()).andReturn(filterWidget).once();
        IStreamSource streamSource = createMock(IStreamSource.class);
        Map.Entry<Supplier<String>, Supplier<InputStream>> source =
            new SimpleImmutableEntry<>(() -> "file_name.txt", () -> new ByteArrayInputStream(new byte[]{}));
        expect(streamSource.getSource()).andReturn(source).times(2);
        expect(controller.getExportUsagesStreamSource()).andReturn(streamSource).once();
        replay(UI.class, ui, controller, streamSource);
        widget.init();
        verify(controller);
        reset(UI.class, ui, controller, streamSource);
    }

    @Test
    public void testWidgetStructure() {
        assertThat(widget.getPrimaryComponent(), instanceOf(FasNtsUsageFilterWidget.class));
        Component secondComponent = widget.getSecondaryComponent();
        assertThat(secondComponent, instanceOf(VerticalLayout.class));
        VerticalLayout contentLayout = (VerticalLayout) secondComponent;
        assertEquals(2, contentLayout.getComponentCount());
        var toolbarLayout = (HorizontalLayout) contentLayout.getComponentAt(0);
        verifyButtonsLayout((HorizontalLayout) toolbarLayout.getComponentAt(0));
        verifyGrid((Grid<?>) contentLayout.getComponentAt(1));
    }

    @Test
    public void testGridValues() {
        var usages = loadExpectedUsageDtos("usage_dto_926720c0.json");
        expect(controller.loadBeans(0, Integer.MAX_VALUE, List.of())).andReturn(usages).once();
        expect(controller.getBeansCount()).andReturn(1).once();
        replay(controller);
        Grid<?> grid = (Grid<?>) ((VerticalLayout) widget.getSecondaryComponent()).getComponentAt(1);
        Object[][] expectedCells = {{
            "926720c0-d83a-4339-b21c-e62d3ea20b76", "PAID", "NTS", "Paid batch", "1000000004",
            "Computers for Design and Construction", "1000002859", "John Wiley & Sons - Books", "243904752",
            "100 ROAD MOVIES", "1008902112317555XX", "VALISBN13", "FY2021", "02/12/2021", "100 ROAD MOVIES",
            "some article", "some publisher", "02/13/2021", "2", "3,000.00", "500.00", "lib", "1980", "2000",
            "author", "usage from usages_10.csv"
        }};
        verifyGridItems(grid, usages, expectedCells);
        verify(controller);
    }

    @Test
    public void testSelectFundPoolMenuItems() {
        //TODO {aliakh} implement
    }

    @Test
    public void testSelectAdditionalFundsMenuItems() {
        //TODO {aliakh} implement
    }

    @Test
    public void testAddToScenarioButtonClickListener() {
        //TODO {aliakh} implement
    }

    @Test
    public void testAddToScenarioButtonClickListenerProcessingBatches() {
        //TODO {aliakh} implement
    }

    @Test
    public void testAddToScenarioButtonClickListenerBatchInScenario() {
        //TODO {aliakh} implement
    }

    @Test
    public void testAddToScenarioButtonClickListenerUnclassifiedUsages() {
        //TODO {aliakh} implement
    }

    @Test
    public void testAddToScenarioButtonClickListenerNoStmRhs() {
        //TODO {aliakh} implement
    }

    @Test
    public void testAddToScenarioButtonClickListenerNoStmAndNonStmRhs() {
        //TODO {aliakh} implement
    }

    @Test
    public void testInitMediator() throws Exception {
        NtsUsageMediator mediator = createMock(NtsUsageMediator.class);
        expectNew(NtsUsageMediator.class).andReturn(mediator).once();
        mediator.setAddToScenarioButton(anyObject(Button.class));
        expectLastCall().once();
        mediator.setAssignClassificationButton(anyObject(Button.class));
        expectLastCall().once();
        mediator.setAdditionalFundsMenuBar(anyObject(MenuBar.class));
        expectLastCall().once();
        mediator.setLoadFundPoolMenuItem(anyObject(MenuItem.class));
        expectLastCall().once();
        replay(NtsUsageMediator.class, mediator, controller);
        assertNotNull(widget.initMediator());
        verify(NtsUsageMediator.class, mediator, controller);
    }

    private void verifyButtonsLayout(HorizontalLayout layout) {
        assertEquals(5, layout.getComponentCount());
        verifyMenuBar(layout.getComponentAt(0), "Fund Pool", true, List.of("Load", "View"));
        verifyMenuBar(layout.getComponentAt(1), "Additional Funds", true, List.of("Create", "View"));
        verifyButton(layout.getComponentAt(2), "Assign Classification", true, true);
        verifyButton(layout.getComponentAt(3), "Add To Scenario", true, true);
        verifyFileDownloader(layout.getComponentAt(4), "Export", true, true);
    }

    private void verifyGrid(Grid grid) {
        List<Column> columns = grid.getColumns();
        assertEquals(List.of("Detail ID", "Detail Status", "Product Family", "Usage Batch Name",
            "RRO Account #", "RRO Name", "RH Account #", "RH Name", "Wr Wrk Inst", "System Title", "Standard Number",
            "Standard Number Type", "Fiscal Year", "Payment Date", "Title", "Article", "Publisher", "Pub Date",
            "Number of Copies", "Reported Value", "Gross Amt in USD", "Market", "Market Period From",
            "Market Period To", "Author", "Comment"),
            columns.stream().map(Column::getHeaderText).collect(Collectors.toList()));
    }

    private List<UsageDto> loadExpectedUsageDtos(String fileName) {
        try {
            var content = TestUtils.fileToString(this.getClass(), fileName);
            var mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
            return mapper.readValue(content, new TypeReference<List<UsageDto>>() {
            });
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
