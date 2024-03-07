package com.copyright.rup.dist.foreign.vui.usage.impl.aacl;

import static com.copyright.rup.dist.foreign.vui.GridColumnVerifier.verifyBigDecimalColumn;
import static com.copyright.rup.dist.foreign.vui.GridColumnVerifier.verifyDateColumnLongFormat;
import static com.copyright.rup.dist.foreign.vui.GridColumnVerifier.verifyStringColumnIgnoreCase;
import static com.copyright.rup.dist.foreign.vui.IVaadinComponentFinder.getButton;
import static com.copyright.rup.dist.foreign.vui.IVaadinJsonConverter.assertJsonSnapshot;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getDialogContent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getFooterLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifySearchWidget;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.expectNew;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;
import com.copyright.rup.dist.foreign.vui.UiTestHelper;
import com.copyright.rup.dist.foreign.vui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.vui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link ViewAaclFundPoolWindow}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 02/05/2020
 *
 * @author Stanislau Rudak
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, ForeignSecurityUtils.class, ViewAaclFundPoolDetailsWindow.class,
    ViewAaclFundPoolWindow.class})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class ViewAaclFundPoolWindowTest {

    private static final String FUND_POOL_ID = "fbd514e0-4ed3-4e3b-a18c-2171f3f9bd1f";
    private static final String FUND_POOL_NAME = "Fund Pool";
    private static final String GRID = "grid";

    private ViewAaclFundPoolWindow window;
    private IAaclUsageController controller;

    @Before
    public void setUp() {
        mockStatic(ForeignSecurityUtils.class);
        controller = createMock(IAaclUsageController.class);
        expect(ForeignSecurityUtils.hasDeleteFundPoolPermission()).andReturn(true).once();
        expect(controller.getFundPools()).andReturn(List.of(buildFundPool())).once();
        replay(ForeignSecurityUtils.class, controller);
        window = new ViewAaclFundPoolWindow(controller);
        verify(ForeignSecurityUtils.class, controller);
        reset(ForeignSecurityUtils.class, controller);
    }

    @Test
    public void testStructure() {
        verifyWindow(window, "View Fund Pool", "902px", "600px", Unit.PIXELS, true);
        var content = (VerticalLayout) getDialogContent(window);
        assertEquals(2, content.getComponentCount());
        verifySearchWidget(content.getComponentAt(0), "Enter Fund Pool Name");
        verifyGrid((Grid<FundPool>) content.getComponentAt(1));
        verifyButtonsLayout(getFooterLayout(window), true, "View", "Delete", "Close");
    }

    @Test
    public void testJsonSnapshot() {
        assertJsonSnapshot("usage/impl/aacl/view-aacl-fund-pool-window.json", window);
    }

    @Test
    public void testViewClickListener() throws Exception {
        mockStatic(Windows.class);
        var fundPool = buildFundPool();
        var fundPoolDetails = buildFundPoolDetail();
        var viewAaclFundPoolDetailsWindow = createMock(ViewAaclFundPoolDetailsWindow.class);
        expectNew(ViewAaclFundPoolDetailsWindow.class, fundPool, fundPoolDetails)
            .andReturn(viewAaclFundPoolDetailsWindow).once();
        Windows.showModalWindow(viewAaclFundPoolDetailsWindow);
        expectLastCall().once();
        Grid<FundPool> grid = createMock(Grid.class);
        Whitebox.setInternalState(window, GRID, grid);
        expect(grid.getSelectedItems()).andReturn(Set.of(fundPool)).once();
        expect(controller.getFundPoolDetails(FUND_POOL_ID)).andReturn(fundPoolDetails).once();
        replay(Windows.class, ViewAaclFundPoolDetailsWindow.class, grid, controller);
        getViewButton().click();
        verify(Windows.class, ViewAaclFundPoolDetailsWindow.class, grid, controller);
    }

    @Test
    public void testDeleteClickListener() {
        mockStatic(Windows.class);
        Dialog confirmWindow = createMock(Dialog.class);
        expect(Windows.showConfirmDialog(
            eq("Are you sure you want to delete <i><b>'Fund Pool'</b></i> fund pool?"), anyObject()))
            .andReturn(confirmWindow).once();
        Grid<FundPool> grid = createMock(Grid.class);
        Whitebox.setInternalState(window, GRID, grid);
        expect(grid.getSelectedItems()).andReturn(Set.of(buildFundPool())).once();
        expect(controller.getScenarioNameAssociatedWithFundPool(FUND_POOL_ID)).andReturn(null).once();
        replay(Windows.class, confirmWindow, grid, controller);
        getDeleteButton().click();
        verify(Windows.class, confirmWindow, grid, controller);
    }

    @Test
    public void testDeleteClickListenerWithAssociatedScenarios() {
        mockStatic(Windows.class);
        Windows.showNotificationWindow(
            eq("Fund pool cannot be deleted because it is associated with the following scenario: Scenario"));
        expectLastCall().once();
        Grid<FundPool> grid = createMock(Grid.class);
        Whitebox.setInternalState(window, GRID, grid);
        expect(grid.getSelectedItems()).andReturn(Set.of(buildFundPool())).once();
        expect(controller.getScenarioNameAssociatedWithFundPool(FUND_POOL_ID)).andReturn("Scenario").once();
        replay(Windows.class, grid, controller);
        getDeleteButton().click();
        verify(Windows.class, grid, controller);
    }

    @Test
    public void testSelectionChangedListener() {
        Button viewButton = getViewButton();
        Button deleteButton = getDeleteButton();
        assertFalse(viewButton.isEnabled());
        assertFalse(deleteButton.isEnabled());
        Grid<FundPool> grid = Whitebox.getInternalState(window, GRID);
        grid.select(buildFundPool());
        assertTrue(viewButton.isEnabled());
        assertTrue(deleteButton.isEnabled());
        grid.deselectAll();
        assertFalse(viewButton.isEnabled());
        assertFalse(deleteButton.isEnabled());
    }

    @Test
    public void testPerformSearch() {
        SearchWidget searchWidget = createMock(SearchWidget.class);
        Grid<?> grid = createMock(Grid.class);
        Whitebox.setInternalState(window, searchWidget);
        Whitebox.setInternalState(window, grid);
        expect(grid.getDataProvider()).andReturn(new ListDataProvider(List.of())).once();
        expect(searchWidget.getSearchValue()).andReturn("Search").once();
        replay(searchWidget, grid);
        window.performSearch();
        verify(searchWidget, grid);
    }

    private static void verifyGrid(Grid<FundPool> grid) {
        UiTestHelper.verifyGrid(grid, List.of(
            Pair.of("Fund Pool Name", "200px"),
            Pair.of("Gross Fund Pool Total", "220px"),
            Pair.of("Created By", "330px"),
            Pair.of("Created Date", "150px")));
        assertEquals(4, grid.getColumns().size());
        verifyStringColumnIgnoreCase(grid, 0, FundPool::new, FundPool::setName);
        verifyBigDecimalColumn(grid, 1, FundPool::new, FundPool::setTotalAmount);
        verifyStringColumnIgnoreCase(grid, 2, FundPool::new, FundPool::setCreateUser);
        verifyDateColumnLongFormat(grid, 3, FundPool::new, FundPool::setCreateDate);
    }

    private Button getViewButton() {
        return getButton(window, "View");
    }

    private Button getDeleteButton() {
        return getButton(window, "Delete");
    }

    private FundPool buildFundPool() {
        var fundPool = new FundPool();
        fundPool.setId(FUND_POOL_ID);
        fundPool.setName(FUND_POOL_NAME);
        return fundPool;
    }

    private List<FundPoolDetail> buildFundPoolDetail() {
        var fundPoolDetail = new FundPoolDetail();
        fundPoolDetail.setAggregateLicenseeClass(buildAggregateLicenseeClass(108, "EXGP", "Life Sciences"));
        fundPoolDetail.setGrossAmount(BigDecimal.ONE);
        return List.of(fundPoolDetail);
    }

    private AggregateLicenseeClass buildAggregateLicenseeClass(Integer id, String enrollmentProfile,
                                                               String discipline) {
        var aggregateLicenseeClass = new AggregateLicenseeClass();
        aggregateLicenseeClass.setId(id);
        aggregateLicenseeClass.setEnrollmentProfile(enrollmentProfile);
        aggregateLicenseeClass.setDiscipline(discipline);
        return aggregateLicenseeClass;
    }
}
