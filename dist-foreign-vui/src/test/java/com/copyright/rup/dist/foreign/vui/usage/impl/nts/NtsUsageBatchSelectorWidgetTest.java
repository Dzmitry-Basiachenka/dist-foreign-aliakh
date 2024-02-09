package com.copyright.rup.dist.foreign.vui.usage.impl.nts;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getCheckboxGroup;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getDialogContent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getFooterLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButton;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifySearchWidget;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.vui.usage.api.nts.INtsUsageController;
import com.copyright.rup.dist.foreign.vui.usage.api.nts.IWorkClassificationController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.Query;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Verifies {@link NtsUsageBatchSelectorWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/12/2019
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, UI.class})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class NtsUsageBatchSelectorWidgetTest {

    private static final String USAGE_BATCH_ID = "87607fdd-e342-4e81-bc7f-4f5eddd353f0";
    private static final String USAGE_BATCH_NAME = "Usage Batch";
    private static final String FAS_PRODUCT_FAMILY = "FAS";

    private NtsUsageBatchSelectorWidget widget;
    private INtsUsageController controller;

    @Before
    public void setUp() {
        controller = createMock(INtsUsageController.class);
        widget = new NtsUsageBatchSelectorWidget(controller);
    }

    @Test
    public void testLoadBeans() {
        var usageBatches = List.of(buildUsageBatch());
        expect(controller.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).once();
        expect(controller.getUsageBatches(FAS_PRODUCT_FAMILY)).andReturn(usageBatches).once();
        replay(controller);
        assertEquals(usageBatches, widget.loadBeans());
        verify(controller);
    }

    @Test
    public void testGetBeanClass() {
        assertEquals(UsageBatch.class, widget.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        assertEquals(USAGE_BATCH_NAME, widget.getBeanItemCaption(buildUsageBatch()));
    }

    @Test
    public void testOnComponentEvent() {
        mockStatic(Windows.class);
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).once();
        expect(ui.getUIId()).andReturn(1).once();
        IStreamSource streamSource = createMock(IStreamSource.class);
        Map.Entry<Supplier<String>, Supplier<InputStream>> source =
            new SimpleImmutableEntry<>(() -> "file_name.txt", () -> new ByteArrayInputStream(new byte[]{}));
        expect(streamSource.getSource()).andReturn(source).once();
        IWorkClassificationController workClassificationController = createMock(IWorkClassificationController.class);
        expect(controller.getWorkClassificationController()).andReturn(workClassificationController).once();
        expect(workClassificationController.getWorkClassificationThreshold()).andReturn(5).once();
        expect(workClassificationController.getClassificationCount(Set.of(USAGE_BATCH_ID), null)).andReturn(10).once();
        expect(workClassificationController.getExportWorkClassificationStreamSource(
            anyObject(Set.class), anyObject(Supplier.class))).andReturn(streamSource).once();
        FilterSaveEvent<UsageBatch> filterSaveEvent = createMock(FilterSaveEvent.class);
        expect(filterSaveEvent.getSelectedItemsIds()).andReturn(Set.of(buildUsageBatch())).once();
        Windows.showModalWindow(anyObject(WorkClassificationWindow.class));
        expectLastCall().once();
        replay(Windows.class, UI.class, ui, streamSource, controller, workClassificationController, filterSaveEvent);
        widget.onComponentEvent(filterSaveEvent);
        verify(Windows.class, UI.class, ui, streamSource, controller, workClassificationController, filterSaveEvent);
    }

    @Test
    public void testShowFilterWindow() {
        mockStatic(Windows.class);
        expect(controller.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).once();
        var usageBatches = List.of(buildUsageBatch());
        expect(controller.getUsageBatches(FAS_PRODUCT_FAMILY)).andReturn(usageBatches).once();
        Windows.showModalWindow(anyObject(Dialog.class));
        replay(Windows.class, controller);
        var filterWindow = widget.showFilterWindow();
        assertEquals("Batches filter", filterWindow.getHeaderTitle());
        verifyRootLayout(getDialogContent(filterWindow));
        verifyButtonsLayout(getFooterLayout(filterWindow));
        verify(Windows.class, controller);
    }

    private void verifyRootLayout(Component component) {
        assertThat(component, instanceOf(VerticalLayout.class));
        var rootLayout = (VerticalLayout) component;
        assertEquals(2, rootLayout.getComponentCount());
        verifySearchWidget(rootLayout.getComponentAt(0), "Enter Usage Batch Name", "100%");
        verifyCheckboxGroupLayout(rootLayout.getComponentAt(1));
    }

    private void verifyCheckboxGroupLayout(Component component) {
        var checkboxGroup = getCheckboxGroup((Scroller) component);
        var dataProvider = checkboxGroup.getDataProvider();
        assertEquals(List.of(buildUsageBatch()),
            dataProvider.fetch(new Query<>()).collect(Collectors.toList()));
    }

    private void verifyButtonsLayout(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        var buttonsLayout = (HorizontalLayout) component;
        assertEquals(4, buttonsLayout.getComponentCount());
        verifyButton(buttonsLayout.getComponentAt(0), "Continue", true, true);
        verifyButton(buttonsLayout.getComponentAt(1), "Select All", false, true);
        verifyButton(buttonsLayout.getComponentAt(2), "Clear", true, true);
        verifyButton(buttonsLayout.getComponentAt(3), "Close", true, true);
    }

    private UsageBatch buildUsageBatch() {
        var usageBatch = new UsageBatch();
        usageBatch.setId(USAGE_BATCH_ID);
        usageBatch.setName(USAGE_BATCH_NAME);
        return usageBatch;
    }
}
