package com.copyright.rup.dist.foreign.ui.common;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;
import java.util.Set;

/**
 * Verifies {@link UdmBatchFilterWidget}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 05/04/21
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class UdmBatchFilterWidgetTest {

    private static final String UDM_BATCH_NAME = "Udm batch";
    private static final String UDM_BATCH_UID = "a30c747a-13ee-4e53-bd10-ac6aa988b537";

    private UdmBatchFilterWidget udmBatchFilterWidget;

    @Before
    public void setUp() {
        udmBatchFilterWidget = new UdmBatchFilterWidget(Collections::emptyList);
    }

    @Test
    public void testGetBeanClass() {
        assertEquals(UdmBatch.class, udmBatchFilterWidget.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        assertEquals(UDM_BATCH_NAME, udmBatchFilterWidget.getBeanItemCaption(buildUdmBatch()));
    }

    @Test
    public void testOnSave() {
        FilterSaveEvent filterSaveEvent = createMock(FilterSaveEvent.class);
        Set usageBatches = Collections.singleton(UDM_BATCH_NAME);
        expect(filterSaveEvent.getSelectedItemsIds()).andReturn(usageBatches).once();
        replay(filterSaveEvent);
        udmBatchFilterWidget.onSave(filterSaveEvent);
        verify(filterSaveEvent);
    }

    private UdmBatch buildUdmBatch() {
        UdmBatch udmBatch = new UdmBatch();
        udmBatch.setName(UDM_BATCH_NAME);
        udmBatch.setId(UDM_BATCH_UID);
        return udmBatch;
    }
}
