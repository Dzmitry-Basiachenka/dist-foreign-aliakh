package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.same;
import static org.junit.Assert.assertEquals;
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

import com.vaadin.data.ValueProvider;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;
import java.util.List;

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
        expect(usagesController.getSelectedProductFamily()).andReturn("FAS").once();
        expect(usagesController.getUsageBatches("FAS")).andReturn(batches).once();
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
        Capture<ValueProvider<UsageBatch, List<String>>> providerCapture = new Capture<>();
        FilterWindow filterWindow = createMock(FilterWindow.class);
        Windows.showFilterWindow(eq("Batches Selector"), same(ntsUsageBatchSelectorWidget), capture(providerCapture));
        expectLastCall().andReturn(filterWindow).once();
        replay(Windows.class);
        ntsUsageBatchSelectorWidget.showFilterWindow();
        assertEquals(Collections.singletonList(USAGE_BATCH_NAME), providerCapture.getValue().apply(buildUsageBatch()));
        verify(Windows.class);
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setName(USAGE_BATCH_NAME);
        usageBatch.setId(USAGE_BATCH_ID);
        return usageBatch;
    }
}
