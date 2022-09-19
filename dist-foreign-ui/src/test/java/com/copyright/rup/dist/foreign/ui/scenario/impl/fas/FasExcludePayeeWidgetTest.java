package com.copyright.rup.dist.foreign.ui.scenario.impl.fas;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.PayeeTotalHolder;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasExcludePayeeController;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasExcludePayeeFilterController;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasExcludePayeeFilterWidget;
import com.copyright.rup.vaadin.ui.component.window.ConfirmActionDialogWindow;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.Validator;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.GridSelectionModel;

import org.apache.commons.lang3.tuple.Triple;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Verifies {@link FasExcludePayeeWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/14/19
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class FasExcludePayeeWidgetTest {

    private static final String REASON = "reason";
    private static final Long PAYEE_ACCOUNT_NUMBER = 1000009094L;

    private FasExcludePayeeWidget widget;
    private IFasExcludePayeeController controller;
    private IFasExcludePayeeFilterController filterController;
    private IFasExcludePayeeFilterWidget filterWidget;
    private Grid<PayeeTotalHolder> payeesGrid;
    private GridSelectionModel<PayeeTotalHolder> selectionModel;

    @Before
    public void setUp() {
        selectionModel = createMock(GridSelectionModel.class);
        controller = createMock(IFasExcludePayeeController.class);
        payeesGrid = createMock(Grid.class);
        widget = new FasExcludePayeeWidget();
        widget.setController(controller);
        filterController = createMock(IFasExcludePayeeFilterController.class);
        filterWidget = new FasExcludePayeeFilterWidget();
        filterWidget.setController(filterController);
        initWidget();
        Whitebox.setInternalState(widget, payeesGrid);
    }

    @Test
    public void testStructure() {
        verifyWindow(widget, "Exclude Details By Payee", 1200, 500, Unit.PIXELS);
        assertEquals("exclude-details-by-payee-window", widget.getId());
        assertTrue(widget.isDraggable());
        assertTrue(widget.isResizable());
        HorizontalSplitPanel splitPanel = (HorizontalSplitPanel) widget.getContent();
        VerticalLayout content = (VerticalLayout) splitPanel.getSecondComponent();
        assertEquals(3, content.getComponentCount());
        verifyToolbar(content.getComponent(0));
        assertThat(content.getComponent(1), instanceOf(Grid.class));
        Grid grid = (Grid) content.getComponent(1);
        verifyGrid(grid, Arrays.asList(
            Triple.of("Payee Account #", -1.0, -1),
            Triple.of("Payee Name", -1.0, -1),
            Triple.of("Gross Amt in USD", -1.0, -1),
            Triple.of("Service Fee Amount", -1.0, -1),
            Triple.of("Net Amt in USD", -1.0, -1),
            Triple.of("Participating", -1.0, -1)
        ));
        verifyButtonsLayout(content.getComponent(2), "Exclude Details", "Redesignate Details", "Clear",
            "Close");
    }

    @Test
    public void testGridValues() {
        Grid<?> grid = (Grid<?>) ((VerticalLayout) ((HorizontalSplitPanel) widget.getContent())
            .getSecondComponent()).getComponent(1);
        Object[][] expectedCells = {
            {PAYEE_ACCOUNT_NUMBER, "Chinese Medical Association", "15,403,080.62", "2,464,492.90", "12,938,587.72", 'Y'}
        };
        verifyGridItems(grid, new ArrayList<>(buildPayeeTotalHolder()), expectedCells);
    }

    @Test
    public void testExcludeDetailsButtonClick() {
        mockStatic(Windows.class);
        Button.ClickEvent clickEvent = createMock(Button.ClickEvent.class);
        Capture<ConfirmActionDialogWindow.IListener> actionDialogListenerCapture = new Capture<>();
        expect(payeesGrid.getSelectedItems()).andReturn(buildPayeeTotalHolder()).once();
        expect(controller.getAccountNumbersInvalidForExclude(Collections.singleton(PAYEE_ACCOUNT_NUMBER)))
            .andReturn(Collections.emptySet()).once();
        Windows.showConfirmDialogWithReason(eq("Confirm action"),
            eq("Are you sure you want to exclude details with selected Payees?"),
            eq("Yes"), eq("Cancel"), capture(actionDialogListenerCapture), anyObject(Validator.class));
        expectLastCall().once();
        controller.excludeDetails(anyObject(), eq(REASON));
        expectLastCall().once();
        replay(clickEvent, controller, payeesGrid, Windows.class);
        buttonClick(0, clickEvent);
        actionDialogListenerCapture.getValue().onActionConfirmed(REASON);
        verify(clickEvent, controller, payeesGrid, Windows.class);
    }

    @Test
    public void testExcludeDetailsButtonClickInvalidPayees() {
        mockStatic(Windows.class);
        Button.ClickEvent clickEvent = createMock(Button.ClickEvent.class);
        expect(payeesGrid.getSelectedItems()).andReturn(buildPayeeTotalHolder()).once();
        expect(controller.getAccountNumbersInvalidForExclude(Collections.singleton(PAYEE_ACCOUNT_NUMBER)))
            .andReturn(Collections.singleton(PAYEE_ACCOUNT_NUMBER)).once();
        Windows.showNotificationWindow(
            "The following payee(s) have different participation statuses: <i><b>[1000009094]</b></i>");
        expectLastCall().once();
        replay(clickEvent, controller, payeesGrid, Windows.class);
        buttonClick(0, clickEvent);
        verify(clickEvent, controller, payeesGrid, Windows.class);
    }

    @Test
    public void testExcludeDetailsButtonClickWithoutSelectionPayees() {
        mockStatic(Windows.class);
        Button.ClickEvent clickEvent = createMock(Button.ClickEvent.class);
        Windows.showNotificationWindow("Please select at least one Payee");
        expectLastCall().once();
        expect(payeesGrid.getSelectedItems()).andReturn(Collections.emptySet()).once();
        replay(clickEvent, controller, payeesGrid, Windows.class);
        buttonClick(0, clickEvent);
        verify(clickEvent, controller, payeesGrid, Windows.class);
    }

    @Test
    public void testRedesignateDetailsButtonClick() {
        mockStatic(Windows.class);
        Button.ClickEvent clickEvent = createMock(Button.ClickEvent.class);
        Capture<ConfirmActionDialogWindow.IListener> actionDialogListenerCapture = new Capture<>();
        expect(payeesGrid.getSelectedItems()).andReturn(buildPayeeTotalHolder()).once();
        expect(controller.getAccountNumbersInvalidForExclude(Collections.singleton(PAYEE_ACCOUNT_NUMBER)))
            .andReturn(Collections.emptySet()).once();
        Windows.showConfirmDialogWithReason(eq("Confirm action"),
            eq("Are you sure you want to redesignate details with selected Payees?"),
            eq("Yes"), eq("Cancel"), capture(actionDialogListenerCapture), anyObject(Validator.class));
        expectLastCall().once();
        controller.redesignateDetails(anyObject(), eq(REASON));
        expectLastCall().once();
        replay(clickEvent, controller, payeesGrid, Windows.class);
        buttonClick(1, clickEvent);
        actionDialogListenerCapture.getValue().onActionConfirmed(REASON);
        verify(clickEvent, controller, payeesGrid, Windows.class);
    }

    @Test
    public void testRedesignateDetailsButtonClickInvalidPayees() {
        mockStatic(Windows.class);
        Button.ClickEvent clickEvent = createMock(Button.ClickEvent.class);
        expect(payeesGrid.getSelectedItems()).andReturn(buildPayeeTotalHolder()).once();
        expect(controller.getAccountNumbersInvalidForExclude(Collections.singleton(PAYEE_ACCOUNT_NUMBER)))
            .andReturn(Collections.singleton(PAYEE_ACCOUNT_NUMBER)).once();
        Windows.showNotificationWindow(
            "The following payee(s) have different participation statuses: <i><b>[1000009094]</b></i>");
        expectLastCall().once();
        replay(clickEvent, controller, payeesGrid, Windows.class);
        buttonClick(1, clickEvent);
        verify(clickEvent, controller, payeesGrid, Windows.class);
    }

    @Test
    public void testRedesignateDetailsButtonClickWithoutSelectionPayees() {
        mockStatic(Windows.class);
        Button.ClickEvent clickEvent = createMock(Button.ClickEvent.class);
        Windows.showNotificationWindow("Please select at least one Payee");
        expectLastCall().once();
        expect(payeesGrid.getSelectedItems()).andReturn(Collections.emptySet()).once();
        replay(clickEvent, controller, payeesGrid, Windows.class);
        buttonClick(1, clickEvent);
        verify(clickEvent, controller, payeesGrid, Windows.class);
    }

    @Test
    public void testClearButtonClick() {
        Button.ClickEvent clickEvent = createMock(Button.ClickEvent.class);
        expect(payeesGrid.getSelectionModel()).andReturn(selectionModel).once();
        selectionModel.deselectAll();
        expectLastCall().once();
        replay(clickEvent, payeesGrid);
        buttonClick(2, clickEvent);
        verify(clickEvent, payeesGrid);
    }

    private Set<PayeeTotalHolder> buildPayeeTotalHolder() {
        Set<PayeeTotalHolder> payeeTotalHolders = new HashSet<>();
        PayeeTotalHolder payeeTotalHolder = new PayeeTotalHolder();
        payeeTotalHolder.getPayee().setAccountNumber(PAYEE_ACCOUNT_NUMBER);
        payeeTotalHolder.getPayee().setName("Chinese Medical Association");
        payeeTotalHolder.setGrossTotal(new BigDecimal("15403080.62"));
        payeeTotalHolder.setServiceFeeTotal(new BigDecimal("2464492.90"));
        payeeTotalHolder.setNetTotal(new BigDecimal("12938587.72"));
        payeeTotalHolder.setPayeeParticipating(true);
        payeeTotalHolders.add(payeeTotalHolder);
        return payeeTotalHolders;
    }

    private void buttonClick(int buttonIndex, Button.ClickEvent clickEvent) {
        HorizontalSplitPanel splitPanel = (HorizontalSplitPanel) widget.getContent();
        VerticalLayout content = (VerticalLayout) splitPanel.getSecondComponent();
        HorizontalLayout horizontalLayout = (HorizontalLayout) content.getComponent(2);
        Button button = (Button) horizontalLayout.getComponent(buttonIndex);
        Collection<?> listeners = button.getListeners(Button.ClickEvent.class);
        assertEquals(1, listeners.size());
        Button.ClickListener clickListener = (Button.ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
    }

    private void verifyToolbar(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(new MarginInfo(false, true, false, true), layout.getMargin());
        assertEquals(2, layout.getComponentCount());
        verifyExportButton(layout.getComponent(0));
        verifySearchWidget(layout.getComponent(1));
    }

    private void verifySearchWidget(Component component) {
        assertThat(component, instanceOf(SearchWidget.class));
        SearchWidget searchWidget = (SearchWidget) component;
        assertEquals("Enter Payee Name/Account #",
            Whitebox.getInternalState(searchWidget, TextField.class).getPlaceholder());
    }

    private void verifyExportButton(Component component) {
        assertThat(component, instanceOf(Button.class));
        Button button = (Button) component;
        assertEquals("Export", button.getCaption());
        assertEquals(1, button.getExtensions().size());
    }

    private void initWidget() {
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource()).andReturn(new SimpleImmutableEntry(createMock(Supplier.class),
            createMock(Supplier.class))).once();
        expect(controller.getCsvStreamSource()).andReturn(streamSource).once();
        expect(controller.getExcludePayeesFilterController()).andReturn(filterController).once();
        expect(controller.getPayeeTotalHolders()).andReturn(new ArrayList<>(buildPayeeTotalHolder())).once();
        expect(filterController.initWidget()).andReturn(filterWidget).once();
        replay(controller, filterController, streamSource);
        widget.init();
        verify(controller, filterController, streamSource);
        reset(controller, filterController, streamSource);
    }
}
