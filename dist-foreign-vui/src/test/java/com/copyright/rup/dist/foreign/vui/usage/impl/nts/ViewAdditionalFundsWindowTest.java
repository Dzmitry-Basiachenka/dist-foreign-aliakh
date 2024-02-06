package com.copyright.rup.dist.foreign.vui.usage.impl.nts;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getDialogContent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getFooterComponent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButton;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.newCapture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.vui.UiTestHelper;
import com.copyright.rup.dist.foreign.vui.usage.api.nts.INtsUsageController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.IConfirmCancelListener;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.List;

/**
 * Verifies {@link ViewAdditionalFundsWindow}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 03/28/2019
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Windows.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class ViewAdditionalFundsWindowTest {

    private ViewAdditionalFundsWindow window;
    private INtsUsageController controller;
    private FundPool fundPool;

    @Before
    public void setUp() {
        fundPool = buildFundPool();
        controller = createMock(INtsUsageController.class);
        expect(controller.getAdditionalFunds()).andReturn(List.of(fundPool)).once();
        replay(controller);
        window = new ViewAdditionalFundsWindow(controller);
        verify(controller);
        reset(controller);
    }

    @Test
    public void testComponentStructure() {
        verifyWindow(window, "View NTS Pre-Service Fee Funds", "1100px", "450px", Unit.PIXELS, true);
        var content = (VerticalLayout) getDialogContent(window);
        assertEquals(2, content.getComponentCount());
        assertEquals(SearchWidget.class, content.getComponentAt(0).getClass());
        verifyGrid((Grid) content.getComponentAt(1));
        verifyButton(getFooterComponent(window, 1), "Close", true);
    }

    @Test
    public void testGridValues() {
        Grid<?> grid = Whitebox.getInternalState(window, "grid");
        Object[][] expectedCells = {{"Fund Pool", "1,234,567.89", "user@copyright.com", "comment", "Delete"}};
        verifyGridItems(grid, List.of(fundPool), expectedCells);
    }

    @Test
    public void testDeleteClickListenerConfirm() {
        mockStatic(Windows.class);
        Capture<IConfirmCancelListener> listenerCapture = newCapture();
        Dialog confirmWindowCapture = createMock(Dialog.class);
        expect(Windows.showConfirmDialog(
            eq("Are you sure you want to delete <i><b>'Fund Pool'</b></i> additional fund?"),
            capture(listenerCapture))).andReturn(confirmWindowCapture).once();
        expect(controller.getScenarioNameAssociatedWithAdditionalFund(fundPool.getId())).andReturn(null).once();
        controller.deleteAdditionalFund(fundPool);
        expectLastCall().once();
        expect(controller.getAdditionalFunds()).andReturn(List.of()).once();
        replay(Windows.class, confirmWindowCapture, controller);
        getDeleteButton().click();
        IConfirmCancelListener listener = listenerCapture.getValue();
        listener.confirm();
        verify(Windows.class, confirmWindowCapture, controller);
    }

    @Test
    public void testDeleteClickListenerCancel() {
        mockStatic(Windows.class);
        Capture<IConfirmCancelListener> listenerCapture = newCapture();
        Dialog confirmWindowCapture = createMock(Dialog.class);
        expect(Windows.showConfirmDialog(
            eq("Are you sure you want to delete <i><b>'Fund Pool'</b></i> additional fund?"),
            capture(listenerCapture))).andReturn(confirmWindowCapture).once();
        expect(controller.getScenarioNameAssociatedWithAdditionalFund(fundPool.getId())).andReturn(null).once();
        replay(Windows.class, confirmWindowCapture, controller);
        getDeleteButton().click();
        IConfirmCancelListener listener = listenerCapture.getValue();
        listener.cancel();
        verify(Windows.class, confirmWindowCapture, controller);
    }

    @Test
    public void testDeleteClickListenerFundAssociatedWithScenario() {
        mockStatic(Windows.class);
        expect(controller.getScenarioNameAssociatedWithAdditionalFund(fundPool.getId()))
            .andReturn("FAS Distribution 2019").once();
        Windows.showNotificationWindow("Pre-Service Fee Fund cannot be deleted because " +
            "it is associated with the following scenario: FAS Distribution 2019");
        expectLastCall().once();
        replay(Windows.class, controller);
        getDeleteButton().click();
        verify(Windows.class, controller);
    }

    @Test
    public void testSearchController() {
        SearchWidget searchWidget = Whitebox.getInternalState(window, "searchWidget");
        searchWidget.setSearchValue("value");
        Grid<?> grid = Whitebox.getInternalState(window, "grid");
        var dataProvider = (ListDataProvider<?>) grid.getDataProvider();
        assertNull(dataProvider.getFilter());
        window.performSearch();
        assertNotNull(dataProvider.getFilter());
    }

    private void verifyGrid(Grid grid) {
        UiTestHelper.verifyGrid(grid, List.of(
            Pair.of("Fund Name", null),
            Pair.of("Fund Amount", "100px"),
            Pair.of("Created By", "140px"),
            Pair.of("Comment", "320px"),
            Pair.of(StringUtils.EMPTY, "90px")
        ));
        assertEquals("Delete", getDeleteButton().getText());
    }

    private Button getDeleteButton() {
        Grid<?> grid = Whitebox.getInternalState(window, "grid");
        var column = grid.getColumnByKey("delete");
        var renderer = (ComponentRenderer<?, FundPool>) column.getRenderer();
        return (Button) renderer.createComponent(fundPool);
    }

    private FundPool buildFundPool() {
        var ntsFundPool = new FundPool();
        ntsFundPool.setId("be340eb2-950d-4cf7-a56d-3e4c8d690f8a");
        ntsFundPool.setTotalAmount(new BigDecimal("1234567.89"));
        ntsFundPool.setName("Fund Pool");
        ntsFundPool.setCreateUser("user@copyright.com");
        ntsFundPool.setComment("comment");
        return ntsFundPool;
    }
}
