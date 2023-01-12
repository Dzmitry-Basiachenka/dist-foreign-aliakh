package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link ViewAclFundPoolWindow}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 05/18/2022
 *
 * @author Anton Azarenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, ForeignSecurityUtils.class})
public class ViewAclFundPoolWindowTest {

    private static final String FUND_POOL_ID = "8e440297-b89e-4cf2-b3cd-0420aea06a9d";
    private static final List<String> SCENARIO_NAMES = List.of("ACL Scenario 2021", "ACL Scenario 2022");

    private ViewAclFundPoolWindow window;
    private Grid<AclFundPool> aclFundPoolGrid;
    private IAclFundPoolController controller;

    @Before
    public void setUp() {
        mockStatic(ForeignSecurityUtils.class);
        controller = createMock(IAclFundPoolController.class);
        aclFundPoolGrid = createMock(Grid.class);
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andReturn(true).once();
        expect(controller.getAllAclFundPools()).andReturn(List.of(buildAclFundPool(true)));
        replay(ForeignSecurityUtils.class, controller);
        window = new ViewAclFundPoolWindow(controller);
        Whitebox.setInternalState(window, "grid", aclFundPoolGrid);
        verify(ForeignSecurityUtils.class, controller);
        reset(ForeignSecurityUtils.class, controller);
    }

    @Test
    public void testGridValues() {
        List<AclFundPool> fundPools = List.of(buildAclFundPool(true));
        Grid<?> grid = (Grid<?>) ((VerticalLayout) window.getContent()).getComponent(1);
        Object[][] expectedCells = {
            {"ACL Fund Pool 2021", "ACL", new BigDecimal("26618664.46"), new BigDecimal("18370661.72"), "Manual",
                "user@copyright.com", "08/31/2022 12:00 AM"}
        };
        verifyGridItems(grid, fundPools, expectedCells);
    }

    @Test
    public void testStructure() {
        verifyWindow(window, "View Fund Pool", 1000, 550, Sizeable.Unit.PIXELS);
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(3, content.getComponentCount());
        assertThat(content.getComponent(0), instanceOf(SearchWidget.class));
        Component component = content.getComponent(1);
        assertThat(component, instanceOf(Grid.class));
        Grid grid = (Grid) component;
        verifyGrid(grid, List.of(
            Triple.of("Fund Pool Name", -1.0, 1),
            Triple.of("License Type", 100.0, -1),
            Triple.of("Gross Amount", 110.0, -1),
            Triple.of("Net Amount", 110.0, -1),
            Triple.of("Source", 80.0, -1),
            Triple.of("Created By", 170.0, -1),
            Triple.of("Created Date", 130.0, -1)
        ));
        verifyButtonsLayout(content.getComponent(2), "Delete", "Close");
        assertEquals("view-acl-fund-pool-window", window.getStyleName());
        assertEquals("view-acl-fund-pool-window", window.getId());
    }

    @Test
    public void testDeleteClickListener() {
        mockStatic(Windows.class);
        Window confirmWindowCapture = createMock(Window.class);
        Button.ClickListener listener = getDeleteButtonClickListener();
        expect(aclFundPoolGrid.getSelectedItems()).andReturn(Set.of(buildAclFundPool(true))).once();
        expect(controller.getScenarioNamesAssociatedWithFundPool(FUND_POOL_ID)).andReturn(List.of()).once();
        expect(Windows.showConfirmDialog(
            eq("Are you sure you want to delete <i><b>'ACL Fund Pool 2021'</b></i> fund pool?"),
            anyObject())).andReturn(confirmWindowCapture).once();
        replay(Windows.class, confirmWindowCapture, controller, aclFundPoolGrid);
        listener.buttonClick(null);
        verify(Windows.class, confirmWindowCapture, controller, aclFundPoolGrid);
    }

    @Test
    public void testDeleteLdmtClickListener() {
        mockStatic(Windows.class);
        Window confirmWindowCapture = createMock(Window.class);
        Button.ClickListener listener = getDeleteButtonClickListener();
        expect(aclFundPoolGrid.getSelectedItems()).andReturn(Set.of(buildAclFundPool(false))).once();
        expect(controller.getScenarioNamesAssociatedWithFundPool(FUND_POOL_ID)).andReturn(List.of()).once();
        expect(Windows.showConfirmDialog(
            eq("You are about to delete an <span style='color: red'>LDMT</span> <i><b>'ACL Fund Pool 2021'</b></i>" +
                " fund pool, the data relating to the fund pool will no longer be available." +
                " Are you sure you want to proceed?"),
            anyObject())).andReturn(confirmWindowCapture).once();
        replay(Windows.class, confirmWindowCapture, controller, aclFundPoolGrid);
        listener.buttonClick(null);
        verify(Windows.class, confirmWindowCapture, controller, aclFundPoolGrid);
    }

    @Test
    public void testDeleteClickListenerWithAssociatedScenarios() {
        mockStatic(Windows.class);
        Window confirmWindowCapture = createMock(Window.class);
        Button.ClickListener listener = getDeleteButtonClickListener();
        expect(aclFundPoolGrid.getSelectedItems()).andReturn(Set.of(buildAclFundPool(true))).once();
        expect(controller.getScenarioNamesAssociatedWithFundPool(FUND_POOL_ID))
            .andReturn(SCENARIO_NAMES).once();
        Windows.showNotificationWindow(
            eq("Fund pool cannot be deleted because it is associated with the following scenario(s):" +
                "<ul><li>ACL Scenario 2021</li><li>ACL Scenario 2022</li></ul>"));
        expectLastCall().once();
        replay(Windows.class, confirmWindowCapture, controller, aclFundPoolGrid);
        listener.buttonClick(null);
        verify(Windows.class, confirmWindowCapture, controller, aclFundPoolGrid);
    }

    @Test
    public void testDeleteButtonEnabled() {
        VerticalLayout content = (VerticalLayout) window.getContent();
        Grid<AclFundPool> grid = (Grid<AclFundPool>) content.getComponent(1);
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(2);
        Button deleteButton = (Button) buttonsLayout.getComponent(0);
        assertFalse(deleteButton.isEnabled());
        grid.select(buildAclFundPool(true));
        assertTrue(deleteButton.isEnabled());
    }

    @Test
    public void testPerformSearch() {
        SearchWidget searchWidget = createMock(SearchWidget.class);
        Whitebox.setInternalState(window, searchWidget);
        expect(aclFundPoolGrid.getDataProvider()).andReturn(new ListDataProvider(List.of())).once();
        expect(searchWidget.getSearchValue()).andReturn("Search").once();
        aclFundPoolGrid.recalculateColumnWidths();
        expectLastCall().once();
        replay(searchWidget, aclFundPoolGrid);
        window.performSearch();
        verify(searchWidget, aclFundPoolGrid);
    }

    private Button.ClickListener getDeleteButtonClickListener() {
        VerticalLayout content = (VerticalLayout) window.getContent();
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(2);
        Button deleteButton = (Button) buttonsLayout.getComponent(0);
        Collection<?> listeners = deleteButton.getListeners(Button.ClickEvent.class);
        assertEquals(2, CollectionUtils.size(listeners));
        return (Button.ClickListener) listeners.iterator().next();
    }

    private AclFundPool buildAclFundPool(boolean manualFlag) {
        AclFundPool fundPool = new AclFundPool();
        fundPool.setId(FUND_POOL_ID);
        fundPool.setName("ACL Fund Pool 2021");
        fundPool.setPeriod(202112);
        fundPool.setLicenseType("ACL");
        fundPool.setManualUploadFlag(manualFlag);
        fundPool.setTotalAmount(new BigDecimal("26618664.46"));
        fundPool.setNetAmount(new BigDecimal("18370661.72"));
        fundPool.setCreateUser("user@copyright.com");
        fundPool.setCreateDate(Date.from(LocalDate.of(2022, 8, 31).atStartOfDay(
            ZoneId.systemDefault()).toInstant()));
        fundPool.setUpdateDate(fundPool.getCreateDate());
        return fundPool;
    }
}
