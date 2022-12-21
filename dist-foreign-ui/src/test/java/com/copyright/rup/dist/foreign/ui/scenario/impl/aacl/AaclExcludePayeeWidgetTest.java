package com.copyright.rup.dist.foreign.ui.scenario.impl.aacl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.PayeeAccountAggregateLicenseeClassesPair;
import com.copyright.rup.dist.foreign.domain.PayeeTotalHolder;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclExcludePayeeController;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclExcludePayeeFilterController;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclExcludePayeeFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;
import com.copyright.rup.vaadin.ui.component.window.ConfirmActionDialogWindow.IListener;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.google.common.collect.Sets;
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

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Verifies {@link AaclExcludePayeeWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 11/05/2020
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class AaclExcludePayeeWidgetTest {

    private static final String REASON = "reason";
    private static final Long PAYEE_ACCOUNT_NUMBER_1 = 1000009094L;
    private static final Long PAYEE_ACCOUNT_NUMBER_2 = 7000429266L;

    private AaclExcludePayeeWidget widget;
    private IAaclExcludePayeeController controller;
    private IAaclExcludePayeeFilterController filterController;
    private IAaclExcludePayeeFilterWidget filterWidget;
    private Grid<PayeeTotalHolder> payeesGrid;
    private GridSelectionModel<PayeeTotalHolder> selectionModel;

    @Before
    public void setUp() {
        selectionModel = createMock(GridSelectionModel.class);
        controller = createMock(IAaclExcludePayeeController.class);
        payeesGrid = createMock(Grid.class);
        widget = new AaclExcludePayeeWidget();
        widget.setController(controller);
        filterController = createMock(IAaclExcludePayeeFilterController.class);
        filterWidget = new AaclExcludePayeeFilterWidget();
        filterWidget.setController(filterController);
        initWidget();
        Whitebox.setInternalState(widget, payeesGrid);
    }

    @Test
    public void testStructure() {
        verifyWindow(widget, "Exclude Details By Payee", 1200, 500, Unit.PIXELS);
        assertTrue(widget.isDraggable());
        assertTrue(widget.isResizable());
        assertEquals("exclude-details-by-payee-window", widget.getId());
        HorizontalSplitPanel splitPanel = (HorizontalSplitPanel) widget.getContent();
        VerticalLayout content = (VerticalLayout) splitPanel.getSecondComponent();
        assertEquals(3, content.getComponentCount());
        verifyToolbar(content.getComponent(0));
        Grid grid = (Grid) content.getComponent(1);
        assertThat(content.getComponent(1), instanceOf(Grid.class));
        verifyGrid(grid, Arrays.asList(
            Triple.of("Payee Account #", -1.0, -1),
            Triple.of("Payee Name", -1.0, -1),
            Triple.of("Gross Amt in USD", -1.0, -1),
            Triple.of("Service Fee Amount", -1.0, -1),
            Triple.of("Net Amt in USD", -1.0, -1)
        ));
        verifyButtonsLayout(content.getComponent(2));
    }

    @Test
    public void testClearButtonClick() {
        Button.ClickEvent clickEvent = createMock(Button.ClickEvent.class);
        expect(payeesGrid.getSelectionModel()).andReturn(selectionModel).once();
        selectionModel.deselectAll();
        expectLastCall().once();
        replay(clickEvent, payeesGrid);
        buttonClick(1, clickEvent);
        verify(clickEvent, payeesGrid);
    }

    @Test
    public void testExcludeDetailsButtonClick() {
        mockStatic(Windows.class);
        Button.ClickEvent clickEvent = createMock(Button.ClickEvent.class);
        Capture<IListener> actionDialogListenerCapture = newCapture();
        expect(payeesGrid.getSelectedItems())
            .andReturn(buildPayeeTotalHolder(Collections.singleton(PAYEE_ACCOUNT_NUMBER_2))).once();
        AggregateLicenseeClass aggregateLicenseeClass = buildAggregateLicenseeClass(171, "EXGP", "Arts & Humanities");
        List<PayeeAccountAggregateLicenseeClassesPair> pairs =
            Arrays.asList(buildPayeeAggLcPair(PAYEE_ACCOUNT_NUMBER_1, aggregateLicenseeClass),
                buildPayeeAggLcPair(PAYEE_ACCOUNT_NUMBER_2, aggregateLicenseeClass));
        expect(controller.getPayeeAggClassesPairs()).andReturn(pairs).once();
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
        expect(payeesGrid.getSelectedItems())
            .andReturn(buildPayeeTotalHolder(Sets.newHashSet(PAYEE_ACCOUNT_NUMBER_1, PAYEE_ACCOUNT_NUMBER_2))).once();
        AggregateLicenseeClass aggregateLicenseeClass1 = buildAggregateLicenseeClass(171, "EXGP", "Arts & Humanities");
        AggregateLicenseeClass aggregateLicenseeClass2 = buildAggregateLicenseeClass(120, "MU", "Business Management");
        List<PayeeAccountAggregateLicenseeClassesPair> pairs =
            Arrays.asList(buildPayeeAggLcPair(PAYEE_ACCOUNT_NUMBER_1, aggregateLicenseeClass1),
                buildPayeeAggLcPair(PAYEE_ACCOUNT_NUMBER_2, aggregateLicenseeClass1, aggregateLicenseeClass2));
        expect(controller.getPayeeAggClassesPairs()).andReturn(pairs).once();
        Windows.showNotificationWindow("1000009094, 7000429266 payee(s) cannot be excluded. " +
            "There will be no usages for the following Aggregate Licensee Class(es) after exclusion:" +
            "<ul><li><i><b>120 (MU - Business Management)<br><li>171 (EXGP - Arts & Humanities)</b></i></ul>");
        expectLastCall().once();
        replay(clickEvent, controller, payeesGrid, Windows.class);
        buttonClick(0, clickEvent);
        verify(clickEvent, controller, payeesGrid, Windows.class);
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

    private void verifyButtonsLayout(Component component) {
        UiTestHelper.verifyButtonsLayout(component, "Exclude Details", "Clear", "Close");
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertTrue(horizontalLayout.isSpacing());
        assertEquals(new MarginInfo(false, false), horizontalLayout.getMargin());
    }

    private void initWidget() {
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource()).andReturn(new SimpleImmutableEntry(createMock(Supplier.class),
            createMock(Supplier.class))).once();
        expect(controller.getCsvStreamSource()).andReturn(streamSource).once();
        expect(controller.getExcludePayeesFilterController()).andReturn(filterController).once();
        expect(controller.getPayeeTotalHolders()).andReturn(Collections.emptyList()).once();
        expect(filterController.initWidget()).andReturn(filterWidget).once();
        replay(controller, filterController, streamSource);
        widget.init();
        verify(controller, filterController, streamSource);
        reset(controller, filterController);
    }

    private Set<PayeeTotalHolder> buildPayeeTotalHolder(Set<Long> payeeAccountNumbers) {
        Set<PayeeTotalHolder> payeeTotalHolders = new HashSet<>();
        payeeAccountNumbers.forEach(payeeAccountNumber -> {
            PayeeTotalHolder payeeTotalHolder = new PayeeTotalHolder();
            payeeTotalHolder.getPayee().setAccountNumber(payeeAccountNumber);
            payeeTotalHolders.add(payeeTotalHolder);
        });
        return payeeTotalHolders;
    }

    private PayeeAccountAggregateLicenseeClassesPair buildPayeeAggLcPair(Long accountNumber,
                                                                         AggregateLicenseeClass... aggregateClasses) {
        PayeeAccountAggregateLicenseeClassesPair pair = new PayeeAccountAggregateLicenseeClassesPair();
        pair.setPayeeAccountNumber(accountNumber);
        pair.setAggregateLicenseeClasses(Sets.newHashSet(aggregateClasses));
        return pair;
    }

    private AggregateLicenseeClass buildAggregateLicenseeClass(int aggLcId, String enrollmentProfile,
                                                               String discipline) {
        AggregateLicenseeClass aggregateLicenseeClass = new AggregateLicenseeClass();
        aggregateLicenseeClass.setId(aggLcId);
        aggregateLicenseeClass.setDiscipline(discipline);
        aggregateLicenseeClass.setEnrollmentProfile(enrollmentProfile);
        return aggregateLicenseeClass;
    }
}
