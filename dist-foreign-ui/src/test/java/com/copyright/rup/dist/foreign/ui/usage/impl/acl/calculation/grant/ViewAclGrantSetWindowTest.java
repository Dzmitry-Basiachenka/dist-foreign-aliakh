package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

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
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailController;
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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Verifies {@link ViewAclGrantSetWindow}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/21/2022
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, ForeignSecurityUtils.class, ViewAclGrantSetWindow.class})
public class ViewAclGrantSetWindowTest {

    private ViewAclGrantSetWindow window;
    private Grid<AclGrantSet> aclGrantSetGrid;
    private IAclGrantDetailController controller;

    @Before
    public void setUp() {
        mockStatic(ForeignSecurityUtils.class);
        controller = createMock(IAclGrantDetailController.class);
        aclGrantSetGrid = createMock(Grid.class);
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andReturn(true).once();
        expect(controller.getAllAclGrantSets()).andReturn(Collections.singletonList(buildAclGrantSet()));
        replay(ForeignSecurityUtils.class, controller);
        window = new ViewAclGrantSetWindow(controller);
        Whitebox.setInternalState(window, "grid", aclGrantSetGrid);
        verify(ForeignSecurityUtils.class, controller);
        reset(ForeignSecurityUtils.class, controller);
    }

    @Test
    public void testStructure() {
        verifyWindow(window, "View ACL Grant Set", 600, 550, Sizeable.Unit.PIXELS);
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(3, content.getComponentCount());
        assertTrue(content.getComponent(0) instanceof SearchWidget);
        Component component = content.getComponent(1);
        assertTrue(component instanceof Grid);
        Grid grid = (Grid) component;
        verifyGrid(grid, Arrays.asList(
            Triple.of("Grant Set Name", -1.0, 1),
            Triple.of("Grant Period", 110.0, -1),
            Triple.of("License Type", 100.0, -1),
            Triple.of("Editable", 80.0, -1)
        ));
        verifyButtonsLayout(content.getComponent(2), "Delete", "Close");
        assertEquals("view-acl-grant-set-window", window.getStyleName());
        assertEquals("view-acl-grant-set-window", window.getId());
    }

    @Test
    public void testDeleteClickListener() {
        mockStatic(Windows.class);
        Window confirmWindowCapture = createMock(Window.class);
        Button.ClickListener listener = getDeleteButtonClickListener();
        expect(aclGrantSetGrid.getSelectedItems()).andReturn(Collections.singleton(buildAclGrantSet())).once();
        expect(Windows.showConfirmDialog(
            eq("Are you sure you want to delete <i><b>'ACL Grant Set 2021'</b></i> grant set?"),
            anyObject())).andReturn(confirmWindowCapture).once();
        replay(Windows.class, confirmWindowCapture, controller, aclGrantSetGrid);
        listener.buttonClick(null);
        verify(Windows.class, confirmWindowCapture, controller, aclGrantSetGrid);
    }

    @Test
    public void testDeleteButtonEnabled() {
        VerticalLayout content = (VerticalLayout) window.getContent();
        Grid<AclGrantSet> grid = (Grid<AclGrantSet>) content.getComponent(1);
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(2);
        Button deleteButton = (Button) buttonsLayout.getComponent(0);
        assertFalse(deleteButton.isEnabled());
        grid.select(buildAclGrantSet());
        assertTrue(deleteButton.isEnabled());
    }

    @Test
    public void testPerformSearch() {
        SearchWidget searchWidget = createMock(SearchWidget.class);
        Whitebox.setInternalState(window, searchWidget);
        expect(aclGrantSetGrid.getDataProvider()).andReturn(new ListDataProvider(Collections.emptyList())).once();
        expect(searchWidget.getSearchValue()).andReturn("Search").once();
        aclGrantSetGrid.recalculateColumnWidths();
        expectLastCall().once();
        replay(searchWidget, aclGrantSetGrid);
        window.performSearch();
        verify(searchWidget, aclGrantSetGrid);
    }

    private Button.ClickListener getDeleteButtonClickListener() {
        VerticalLayout content = (VerticalLayout) window.getContent();
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(2);
        Button deleteButton = (Button) buttonsLayout.getComponent(0);
        Collection<?> listeners = deleteButton.getListeners(Button.ClickEvent.class);
        assertEquals(1, CollectionUtils.size(listeners));
        return (Button.ClickListener) listeners.iterator().next();
    }

    private AclGrantSet buildAclGrantSet() {
        AclGrantSet grantSet = new AclGrantSet();
        grantSet.setName("ACL Grant Set 2021");
        grantSet.setGrantPeriod(202112);
        grantSet.setLicenseType("ACL");
        grantSet.setEditable(true);
        return grantSet;
    }
}