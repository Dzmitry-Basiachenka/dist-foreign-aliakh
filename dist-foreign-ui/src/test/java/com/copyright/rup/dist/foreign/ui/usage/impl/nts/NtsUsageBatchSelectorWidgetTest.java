package com.copyright.rup.dist.foreign.ui.usage.impl.nts;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.ui.usage.api.nts.INtsUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.nts.IWorkClassificationController;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
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

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
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
    private INtsUsageController usagesController;

    @Before
    public void setUp() {
        usagesController = createMock(INtsUsageController.class);
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
        IWorkClassificationController workClassificationController = createMock(IWorkClassificationController.class);
        FilterSaveEvent<UsageBatch> filterSaveEvent = createMock(FilterSaveEvent.class);
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(filterSaveEvent.getSelectedItemsIds()).andReturn(Collections.singleton(buildUsageBatch())).once();
        expect(usagesController.getWorkClassificationController()).andReturn(workClassificationController).once();
        expect(workClassificationController.getWorkClassificationThreshold()).andReturn(5).once();
        expect(workClassificationController.getClassificationCount(Collections.singleton(USAGE_BATCH_ID), null))
            .andReturn(10).once();
        expect(workClassificationController.getExportWorkClassificationStreamSource(
            anyObject(Set.class), anyObject(Supplier.class))).andReturn(streamSource).once();
        expect(streamSource.getSource())
            .andReturn(new SimpleImmutableEntry(createMock(Supplier.class), createMock(Supplier.class))).once();
        Windows.showModalWindow(anyObject(WorkClassificationWindow.class));
        expectLastCall().once();
        replay(usagesController, workClassificationController, streamSource, filterSaveEvent, Windows.class);
        ntsUsageBatchSelectorWidget.onSave(filterSaveEvent);
        verify(usagesController, workClassificationController, streamSource, filterSaveEvent, Windows.class);
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
        assertThat(component, instanceOf(VerticalLayout.class));
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(3, verticalLayout.getComponentCount());
        verifySearchWidget(verticalLayout.getComponent(0));
        verifyCheckBoxGroupLayout(verticalLayout.getComponent(1));
        verifyButtonsLayout(verticalLayout.getComponent(2));
    }

    private void verifySearchWidget(Component component) {
        assertThat(component, instanceOf(SearchWidget.class));
        SearchWidget searchWidget = (SearchWidget) component;
        verifySize(searchWidget);
        assertEquals("Enter Usage Batch Name",
            Whitebox.getInternalState(searchWidget, TextField.class).getPlaceholder());
    }

    private void verifyCheckBoxGroupLayout(Component component) {
        assertThat(component, instanceOf(Panel.class));
        Panel panel = (Panel) component;
        assertEquals(1, panel.getComponentCount());
        CheckBoxGroup checkBoxGroup = (CheckBoxGroup) panel.getContent();
        DataProvider dataProvider = checkBoxGroup.getDataProvider();
        Stream<?> stream = dataProvider.fetch(new Query<>());
        assertEquals(Collections.singletonList(buildUsageBatch()), stream.collect(Collectors.toList()));
    }

    private void verifyButtonsLayout(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(4, layout.getComponentCount());
        Button saveButton = verifyButton(layout.getComponent(0), "Continue");
        Button selectAllButton = verifyButton(layout.getComponent(1), "Select All");
        Button clearButton = verifyButton(layout.getComponent(2), "Clear");
        Button closeButton = verifyButton(layout.getComponent(3), "Close");
        assertTrue(saveButton.isVisible());
        assertFalse(selectAllButton.isVisible());
        assertTrue(clearButton.isVisible());
        assertTrue(closeButton.isVisible());
        assertEquals(1, saveButton.getListeners(ClickEvent.class).size());
        assertEquals(1, selectAllButton.getListeners(ClickEvent.class).size());
        assertEquals(1, clearButton.getListeners(ClickEvent.class).size());
        assertEquals(1, closeButton.getListeners(ClickEvent.class).size());
    }

    private Button verifyButton(Component component, String caption) {
        assertThat(component, instanceOf(Button.class));
        assertEquals(caption, component.getCaption());
        return (Button) component;
    }

    private void verifySize(Component component) {
        assertEquals(100, component.getWidth(), 0);
        assertEquals(-1, component.getHeight(), 0);
        assertEquals(Unit.PIXELS, component.getHeightUnits());
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setName(USAGE_BATCH_NAME);
        usageBatch.setId(USAGE_BATCH_ID);
        return usageBatch;
    }
}
