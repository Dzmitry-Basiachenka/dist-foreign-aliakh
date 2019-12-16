package com.copyright.rup.dist.foreign.ui.usage.impl.nts;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow.FilterSaveEvent;

import com.google.common.collect.Sets;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Set;

/**
 * Verifies {@link PreServiceFeeFundBatchesFilterWidget}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 03/27/2019
 *
 * @author Aliaksandr Liakh
 */
public class PreServiceFeeFundBatchesFilterWidgetTest {

    private static final String USAGE_BATCH_ID = "c141d1ea-bb35-4ae8-8c33-ca9ac7f21785";
    private static final String USAGE_BATCH_NAME = "DALRO 29May18";

    private PreServiceFeeFundBatchesFilterWidget widget;

    @Before
    public void setUp() {
        widget = new PreServiceFeeFundBatchesFilterWidget(() -> Collections.singletonList(buildUsageBatch()));
    }

    @Test
    public void testLoadBeans() {
        assertEquals(1, widget.loadBeans().size());
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
    public void testOnSave() {
        FilterSaveEvent filterSaveEvent = createMock(FilterSaveEvent.class);
        Set usageBatches = Sets.newHashSet(USAGE_BATCH_NAME);
        expect(filterSaveEvent.getSelectedItemsIds()).andReturn(usageBatches).once();
        replay(filterSaveEvent);
        widget.onSave(filterSaveEvent);
        verify(filterSaveEvent);
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(USAGE_BATCH_ID);
        usageBatch.setName(USAGE_BATCH_NAME);
        return usageBatch;
    }
}
