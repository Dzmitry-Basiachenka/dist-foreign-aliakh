package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

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

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
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
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link ViewAaclFundPoolWindow}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 02/05/20
 *
 * @author Stanislau Rudak
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, ForeignSecurityUtils.class,
    ViewAaclFundPoolDetailsWindow.class, ViewAaclFundPoolWindow.class})
public class ViewAaclFundPoolWindowTest {

    private static final String FUND_POOL_ID = RupPersistUtils.generateUuid();
    private static final String UNCHECKED = "unchecked";

    private ViewAaclFundPoolWindow viewAaclFundPoolWindow;
    private IAaclUsageController controller;
    private final FundPool fundPool = buildFundPool();

    @Before
    public void setUp() {
        mockStatic(ForeignSecurityUtils.class);
        controller = createMock(IAaclUsageController.class);
        expect(ForeignSecurityUtils.hasDeleteFundPoolPermission()).andReturn(true).once();
        expect(controller.getFundPools()).andReturn(List.of(fundPool)).once();
        replay(controller, ForeignSecurityUtils.class);
        viewAaclFundPoolWindow = new ViewAaclFundPoolWindow(controller);
        verify(controller, ForeignSecurityUtils.class);
        reset(controller, ForeignSecurityUtils.class);
    }

    @Test
    public void testStructure() {
        verifyWindow(viewAaclFundPoolWindow, "View Fund Pool", 900, 550, Unit.PIXELS);
        VerticalLayout content = (VerticalLayout) viewAaclFundPoolWindow.getContent();
        assertEquals(3, content.getComponentCount());
        assertEquals(SearchWidget.class, content.getComponent(0).getClass());
        Component component = content.getComponent(1);
        assertEquals(Grid.class, component.getClass());
        verifyGrid((Grid) component, List.of(
            Triple.of("Fund Pool Name", -1.0, 1),
            Triple.of("Gross Fund Pool Total", 170.0, -1),
            Triple.of("Created By", 170.0, -1),
            Triple.of("Created Date", -1.0, 0)));
        assertEquals(1, content.getExpandRatio(component), 0);
        verifyButtonsLayout(content.getComponent(2), "View", "Delete", "Close");
    }

    @Test
    public void testViewClickListener() throws Exception {
        mockStatic(Windows.class);
        List<FundPoolDetail> fundPoolDetails = buildFundPoolDetail();
        expect(controller.getFundPoolDetails(FUND_POOL_ID)).andReturn(fundPoolDetails).once();
        ViewAaclFundPoolDetailsWindow viewDetailsWindowMock = createMock(ViewAaclFundPoolDetailsWindow.class);
        Grid<FundPool> grid = createMock(Grid.class);
        Whitebox.setInternalState(viewAaclFundPoolWindow, "grid", grid);
        Button.ClickListener listener = getButtonClickListener(0, 2);
        expect(grid.getSelectedItems()).andReturn(Set.of(fundPool)).once();
        expectNew(ViewAaclFundPoolDetailsWindow.class, fundPool, fundPoolDetails).andReturn(viewDetailsWindowMock)
            .once();
        Windows.showModalWindow(viewDetailsWindowMock);
        expectLastCall().once();
        replay(controller, grid, Windows.class, ViewAaclFundPoolDetailsWindow.class);
        listener.buttonClick(createMock(ClickEvent.class));
        verify(controller, grid, Windows.class, ViewAaclFundPoolDetailsWindow.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testDeleteClickListener() {
        mockStatic(Windows.class);
        Window confirmWindowMock = createMock(Window.class);
        Grid<FundPool> grid = createMock(Grid.class);
        Whitebox.setInternalState(viewAaclFundPoolWindow, "grid", grid);
        Button.ClickListener listener = getButtonClickListener(1, 1);
        expect(grid.getSelectedItems()).andReturn(Set.of(fundPool)).once();
        expect(controller.getScenarioNameAssociatedWithFundPool(FUND_POOL_ID)).andReturn(null).once();
        expect(
            Windows.showConfirmDialog(
                eq("Are you sure you want to delete <i><b>'AACL Fund Pool'</b></i> fund pool?"), anyObject()))
            .andReturn(confirmWindowMock).once();
        replay(controller, confirmWindowMock, grid, Windows.class);
        listener.buttonClick(createMock(ClickEvent.class));
        verify(controller, confirmWindowMock, grid, Windows.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testDeleteClickListenerWithAssociatedScenarios() {
        mockStatic(Windows.class);
        Grid<FundPool> grid = createMock(Grid.class);
        Whitebox.setInternalState(viewAaclFundPoolWindow, "grid", grid);
        Button.ClickListener listener = getButtonClickListener(1, 1);
        expect(grid.getSelectedItems()).andReturn(Set.of(fundPool)).once();
        expect(controller.getScenarioNameAssociatedWithFundPool(FUND_POOL_ID)).andReturn("Scenario 1").once();
        Windows.showNotificationWindow(
            eq("Fund pool cannot be deleted because it is associated with the following scenario: Scenario 1"));
        expectLastCall().once();
        replay(controller, grid, Windows.class);
        listener.buttonClick(createMock(ClickEvent.class));
        verify(controller, grid, Windows.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testSelectionChangedListener() {
        Button viewButton = getButton(0);
        Button deleteButton = getButton(1);
        assertFalse(viewButton.isEnabled());
        assertFalse(deleteButton.isEnabled());
        Grid<FundPool> grid = (Grid) ((VerticalLayout) viewAaclFundPoolWindow.getContent()).getComponent(1);
        grid.select(fundPool);
        assertTrue(viewButton.isEnabled());
        assertTrue(deleteButton.isEnabled());
        grid.deselectAll();
        assertFalse(viewButton.isEnabled());
        assertFalse(deleteButton.isEnabled());
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testPerformSearch() {
        SearchWidget searchWidget = createMock(SearchWidget.class);
        Grid grid = createMock(Grid.class);
        Whitebox.setInternalState(viewAaclFundPoolWindow, searchWidget);
        Whitebox.setInternalState(viewAaclFundPoolWindow, grid);
        expect(grid.getDataProvider()).andReturn(new ListDataProvider(List.of())).once();
        expect(searchWidget.getSearchValue()).andReturn("Search").once();
        grid.recalculateColumnWidths();
        expectLastCall().once();
        replay(searchWidget, grid);
        viewAaclFundPoolWindow.performSearch();
        verify(searchWidget, grid);
    }

    private Button getButton(int buttonIndex) {
        VerticalLayout content = (VerticalLayout) viewAaclFundPoolWindow.getContent();
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(2);
        return (Button) buttonsLayout.getComponent(buttonIndex);
    }

    private Button.ClickListener getButtonClickListener(int buttonIndex, int listenersCount) {
        Collection<?> listeners = getButton(buttonIndex).getListeners(Button.ClickEvent.class);
        assertEquals(listenersCount, CollectionUtils.size(listeners));
        return (Button.ClickListener) listeners.iterator().next();
    }

    private FundPool buildFundPool() {
        FundPool aaclFundPool = new FundPool();
        aaclFundPool.setId(FUND_POOL_ID);
        aaclFundPool.setName("AACL Fund Pool");
        return aaclFundPool;
    }

    private List<FundPoolDetail> buildFundPoolDetail() {
        FundPoolDetail fundPoolDetail = new FundPoolDetail();
        fundPoolDetail.setAggregateLicenseeClass(buildAggregateLicenseeClass(108, "EXGP", "Life Sciences"));
        fundPoolDetail.setGrossAmount(BigDecimal.ONE);
        return List.of(fundPoolDetail);
    }

    private AggregateLicenseeClass buildAggregateLicenseeClass(Integer id, String enrollmentProfile,
                                                               String discipline) {
        AggregateLicenseeClass aggregateLicenseeClass = new AggregateLicenseeClass();
        aggregateLicenseeClass.setId(id);
        aggregateLicenseeClass.setEnrollmentProfile(enrollmentProfile);
        aggregateLicenseeClass.setDiscipline(discipline);
        return aggregateLicenseeClass;
    }
}
