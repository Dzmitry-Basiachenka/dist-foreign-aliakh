package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link TypeOfUseFilterWidget}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 06/10/2021
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class TypeOfUserFilterWidgetTest {

    private static final String REPORTED_TYPE_OF_USE = "COPY_FOR_MYSELF";
    private final TypeOfUseFilterWidget typeOfUseFilterWidget =
        new TypeOfUseFilterWidget(() -> Collections.singletonList(REPORTED_TYPE_OF_USE), Collections.emptySet());

    @Test
    public void testLoadBeans() {
        List<String> typeOfUses = typeOfUseFilterWidget.loadBeans();
        assertEquals(1, typeOfUses.size());
        assertEquals(REPORTED_TYPE_OF_USE, typeOfUses.get(0));
    }

    @Test
    public void testGetBeanClass() {
        assertEquals(String.class, typeOfUseFilterWidget.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        assertEquals(REPORTED_TYPE_OF_USE, typeOfUseFilterWidget.getBeanItemCaption(REPORTED_TYPE_OF_USE));
    }

    @Test
    public void testOnSave() {
        FilterSaveEvent filterSaveEvent = createMock(FilterSaveEvent.class);
        expect(filterSaveEvent.getSelectedItemsIds()).andReturn(Collections.singleton(REPORTED_TYPE_OF_USE)).once();
        replay(filterSaveEvent);
        typeOfUseFilterWidget.onSave(filterSaveEvent);
        verify(filterSaveEvent);
    }
}
