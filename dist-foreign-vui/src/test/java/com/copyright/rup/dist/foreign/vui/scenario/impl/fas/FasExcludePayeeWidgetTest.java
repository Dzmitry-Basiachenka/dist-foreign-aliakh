package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getDialogContent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getFooterComponent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createPartialMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.PayeeTotalHolder;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasExcludePayeeController;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasExcludePayeeFilterController;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasExcludePayeeFilterWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.ConfirmDialogWindow;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.ConfirmWindows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSelectionModel;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;

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
import java.math.BigDecimal;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
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
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
@PrepareForTest({UI.class, Windows.class, ConfirmWindows.class, ConfirmDialogWindow.class})
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
        payeesGrid = createMock(Grid.class);
        controller = createMock(IFasExcludePayeeController.class);
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
        initWidget();
        verifyWindow(widget, "Exclude Details By Payee", "1500px", "500px", Unit.PIXELS, true);
        assertEquals("exclude-details-by-payee-window", widget.getId().orElseThrow());
        assertTrue(widget.isDraggable());
        assertTrue(widget.isResizable());
        Component component = getDialogContent(widget);
        assertThat(component, instanceOf(SplitLayout.class));
        var splitLayout = (SplitLayout) component;
        var content = (VerticalLayout) splitLayout.getSecondaryComponent();
        assertEquals(2, content.getComponentCount());
        verifyToolbar(content.getComponentAt(0));
        assertThat(content.getComponentAt(1), instanceOf(Grid.class));
        assertThat(splitLayout.getPrimaryComponent(), instanceOf(VerticalLayout.class));
        Grid grid = (Grid) content.getComponentAt(1);
        verifyGrid(grid, List.of(
            Pair.of("Payee Account #", null),
            Pair.of("Payee Name", null),
            Pair.of("Gross Amt in USD", null),
            Pair.of("Service Fee Amount", null),
            Pair.of("Net Amt in USD", null),
            Pair.of("Participating", null)
        ));
        verifyButtonsLayout(getFooterComponent(widget, 2), true, "Exclude Details",
            "Redesignate Details", "Clear", "Close");
    }

    @Test
    public void testGridValues() {
        //TODO: {Vaadin23 - aazarenka} will implement later
    }

    @Test
    public void testExcludeDetailsButtonClick() {
        mockStatic(Windows.class);
        Capture<Consumer<String>> actionDialogListenerCapture = newCapture();
        expect(payeesGrid.getSelectedItems()).andReturn(buildPayeeTotalHolder()).once();
        expect(controller.getAccountNumbersInvalidForExclude(Set.of(PAYEE_ACCOUNT_NUMBER))).andReturn(Set.of()).once();
        createPartialMock(ConfirmWindows.class, "showConfirmDialogWithReason", String.class, String.class,
            String.class, String.class, Consumer.class, List.class);
        ConfirmWindows.showConfirmDialogWithReason(
            eq("Confirm action"),
            eq("Are you sure you want to exclude details with selected Payees?"),
            eq("Yes"),
            eq("Cancel"),
            capture(actionDialogListenerCapture),
            anyObject(List.class));
        expectLastCall().once();
        controller.excludeDetails(anyObject(), eq(REASON));
        expectLastCall().once();
        replay(controller, payeesGrid, Windows.class, ConfirmWindows.class, ConfirmDialogWindow.class);
        buttonClick(0);
        actionDialogListenerCapture.getValue().accept(REASON);
        verify(controller, payeesGrid, Windows.class, ConfirmWindows.class, ConfirmDialogWindow.class);
    }

    @Test
    public void testRedesignateDetailsButtonClickInvalidPayees() {
        mockStatic(Windows.class);
        expect(payeesGrid.getSelectedItems()).andReturn(buildPayeeTotalHolder()).once();
        expect(controller.getAccountNumbersInvalidForExclude(Set.of(PAYEE_ACCOUNT_NUMBER)))
            .andReturn(Set.of(PAYEE_ACCOUNT_NUMBER)).once();
        Windows.showNotificationWindow(
            "The following payee(s) have different participation statuses: <i><b>[1000009094]</b></i>");
        expectLastCall().once();
        replay(controller, payeesGrid, Windows.class, ConfirmWindows.class, ConfirmDialogWindow.class);
        buttonClick(1);
        verify(controller, payeesGrid, Windows.class, ConfirmWindows.class, ConfirmDialogWindow.class);
    }

    @Test
    public void testRedesignateDetailsButtonClickWithoutSelectionPayees() {
        mockStatic(Windows.class);
        Windows.showNotificationWindow("Please select at least one Payee");
        expectLastCall().once();
        expect(payeesGrid.getSelectedItems()).andReturn(Set.of()).once();
        replay(controller, payeesGrid, Windows.class, ConfirmWindows.class, ConfirmDialogWindow.class);
        buttonClick(1);
        verify(controller, payeesGrid, Windows.class);
    }

    @Test
    public void testClearButtonClick() {
        expect(payeesGrid.getSelectionModel()).andReturn(selectionModel).once();
        selectionModel.deselectAll();
        expectLastCall().once();
        replay(payeesGrid);
        buttonClick(2);
        verify(payeesGrid);
    }

    private void buttonClick(int buttonIndex) {
        var buttonsLayout = (HorizontalLayout) getFooterComponent(widget, 2);
        var button = (Button) buttonsLayout.getComponentAt(buttonIndex);
        button.click();
    }

    private void verifyToolbar(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        var layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyExportButton(layout.getComponentAt(0));
        verifySearchWidget(((HorizontalLayout) layout.getComponentAt(1)).getComponentAt(0));
    }

    private void verifySearchWidget(Component component) {
        var searchLayout = (HorizontalLayout) component;
        assertThat(searchLayout, instanceOf(SearchWidget.class));
        var searchWidget = (SearchWidget) searchLayout;
        assertEquals("Enter Payee Name/Account #",
            Whitebox.getInternalState(searchWidget, TextField.class).getPlaceholder());
    }

    private void verifyExportButton(Component component) {
        assertThat(component, instanceOf(OnDemandFileDownloader.class));
        var fileDownloader = (OnDemandFileDownloader) component;
        fileDownloader.getChildren()
            .findFirst()
            .ifPresent(button -> assertEquals("Export", ((Button) button).getText()));
    }

    private Set<PayeeTotalHolder> buildPayeeTotalHolder() {
        Set<PayeeTotalHolder> payeeTotalHolders = new HashSet<>();
        var payeeTotalHolder = new PayeeTotalHolder();
        payeeTotalHolder.getPayee().setAccountNumber(PAYEE_ACCOUNT_NUMBER);
        payeeTotalHolder.getPayee().setName("Chinese Medical Association");
        payeeTotalHolder.setGrossTotal(new BigDecimal("15403080.62"));
        payeeTotalHolder.setServiceFeeTotal(new BigDecimal("2464492.90"));
        payeeTotalHolder.setNetTotal(new BigDecimal("12938587.72"));
        payeeTotalHolder.setPayeeParticipating(true);
        payeeTotalHolders.add(payeeTotalHolder);
        return payeeTotalHolders;
    }

    private void initWidget() {
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).once();
        expect(ui.getUIId()).andReturn(1).once();
        IStreamSource streamSource = createMock(IStreamSource.class);
        Map.Entry<Supplier<String>, Supplier<InputStream>> fileSource =
            new SimpleImmutableEntry<>(() -> "file.txt", () -> new ByteArrayInputStream(new byte[]{}));
        expect(streamSource.getSource()).andReturn(fileSource).once();
        expect(controller.getCsvStreamSource()).andReturn(streamSource).once();
        expect(controller.getExcludePayeesFilterController()).andReturn(filterController).once();
        expect(controller.getPayeeTotalHolders()).andReturn(new ArrayList<>(buildPayeeTotalHolder())).once();
        expect(filterController.initWidget()).andReturn(filterWidget).once();
        replay(controller, filterController, streamSource, ui, UI.class);
        widget.init();
        verify(controller, filterController, streamSource, ui, UI.class);
        reset(controller, filterController, streamSource, ui, UI.class);
    }
}
