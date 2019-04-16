package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBoxGroup;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Verifies {@link NtsUsageBatchSelectorWidget}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/12/2019
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class NtsUsageBatchSelectorWidgetTest {

    private static final String USAGE_BATCH_NAME = "Usage batch";
    private static final String USAGE_BATCH_ID = RupPersistUtils.generateUuid();
    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private NtsUsageBatchSelectorWidget ntsUsageBatchSelectorWidget;
    private IUsagesController usagesController;

    @Before
    public void setUp() {
        usagesController = createMock(IUsagesController.class);
        ntsUsageBatchSelectorWidget = new NtsUsageBatchSelectorWidget(usagesController);
    }

    @Test
    public void testLoadBeans() {
        List<UsageBatch> batches = Collections.singletonList(buildUsageBatch());
        expect(usagesController.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).once();
        expect(usagesController.getUsageBatches(FAS_PRODUCT_FAMILY)).andReturn(batches).once();
        replay(usagesController);
        assertEquals(batches, ntsUsageBatchSelectorWidget.loadBeans());
        verify(usagesController);
    }

    @Test
    public void testGetBeanClass() {
        assertEquals(UsageBatch.class, ntsUsageBatchSelectorWidget.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        assertEquals(USAGE_BATCH_NAME, ntsUsageBatchSelectorWidget.getBeanItemCaption(buildUsageBatch()));
    }

    @Test
    public void testOnSave() {
        mockStatic(Windows.class);
        FilterSaveEvent<UsageBatch> filterSaveEvent = createMock(FilterSaveEvent.class);
        expect(filterSaveEvent.getSelectedItemsIds()).andReturn(Collections.singleton(buildUsageBatch())).once();
        expect(usagesController.getWorkClassificationController())
            .andReturn(createMock(WorkClassificationController.class)).once();
        Windows.showModalWindow(anyObject(WorkClassificationWindow.class));
        expectLastCall().once();
        replay(usagesController, filterSaveEvent, Windows.class);
        ntsUsageBatchSelectorWidget.onSave(filterSaveEvent);
        verify(usagesController, filterSaveEvent, Windows.class);
    }

    @Test
    public void testShowFilterWindow() {
        mockStatic(Windows.class);
        expect(usagesController.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).once();
        List<UsageBatch> batches = Collections.singletonList(buildUsageBatch());
        expect(usagesController.getUsageBatches(FAS_PRODUCT_FAMILY)).andReturn(batches).once();
        Windows.showModalWindow(anyObject(Window.class));
        replay(Windows.class, usagesController);
        FilterWindow filterWindow = ntsUsageBatchSelectorWidget.showFilterWindow();
        assertEquals("Batches filter", filterWindow.getCaption());
        verifyRootLayout(filterWindow.getContent());
        verify(Windows.class, usagesController);
    }

    private void verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(3, verticalLayout.getComponentCount());
        verifySearchWidget(verticalLayout.getComponent(0));
        verifyCheckBoxGroupLayout(verticalLayout.getComponent(1));
        verifyButtonsLayout(verticalLayout.getComponent(2));
    }

    private void verifySearchWidget(Component component) {
        assertTrue(component instanceof SearchWidget);
        SearchWidget searchWidget = (SearchWidget) component;
        verifySize(searchWidget, Unit.PERCENTAGE, 100, Unit.PIXELS, -1);
        assertEquals("Enter Usage Batch Name",
            Whitebox.getInternalState(searchWidget, TextField.class).getPlaceholder());
    }

    private void verifyCheckBoxGroupLayout(Component component) {
        assertTrue(component instanceof Panel);
        Panel panel = (Panel) component;
        assertEquals(1, panel.getComponentCount());
        CheckBoxGroup checkBoxGroup = (CheckBoxGroup) panel.getContent();
        DataProvider dataProvider = checkBoxGroup.getDataProvider();
        Stream<?> stream = dataProvider.fetch(new Query<>());
        assertEquals(Collections.singletonList(buildUsageBatch()), stream.collect(Collectors.toList()));
    }

    private void verifyButtonsLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(3, layout.getComponentCount());
        Button saveButton = verifyButton(layout.getComponent(0), "Continue");
        Button clearButton = verifyButton(layout.getComponent(1), "Clear");
        Button closeButton = verifyButton(layout.getComponent(2), "Close");
        assertEquals(1, saveButton.getListeners(ClickEvent.class).size());
        assertEquals(1, clearButton.getListeners(ClickEvent.class).size());
        assertEquals(1, closeButton.getListeners(ClickEvent.class).size());
    }

    private Button verifyButton(Component component, String caption) {
        assertTrue(component instanceof Button);
        assertEquals(caption, component.getCaption());
        return (Button) component;
    }

    private void verifySize(Component component, Unit widthUnit, float width, Unit heightUnit, float height) {
        assertEquals(width, component.getWidth(), 0);
        assertEquals(height, component.getHeight(), 0);
        assertEquals(heightUnit, component.getHeightUnits());
        assertEquals(widthUnit, component.getWidthUnits());
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setName(USAGE_BATCH_NAME);
        usageBatch.setId(USAGE_BATCH_ID);
        return usageBatch;
    }
}
