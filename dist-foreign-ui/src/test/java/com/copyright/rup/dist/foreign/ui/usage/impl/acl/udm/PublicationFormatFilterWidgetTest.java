package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.same;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.data.ValueProvider;

import org.easymock.Capture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link PublicationFormatFilterWidget}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 06/10/2021
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class PublicationFormatFilterWidgetTest {

    private static final String PUBLICATION_FORMAT = "Digital";
    private final PublicationFormatFilterWidget publicationFormatFilterWidget =
        new PublicationFormatFilterWidget(() -> List.of(PUBLICATION_FORMAT), Collections.emptySet());

    @Test
    public void testLoadBeans() {
        List<String> publicationFormats = publicationFormatFilterWidget.loadBeans();
        assertEquals(1, publicationFormats.size());
        assertEquals(PUBLICATION_FORMAT, publicationFormats.get(0));
    }

    @Test
    public void testGetBeanClass() {
        assertEquals(String.class, publicationFormatFilterWidget.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        assertEquals(PUBLICATION_FORMAT, publicationFormatFilterWidget.getBeanItemCaption(PUBLICATION_FORMAT));
    }

    @Test
    public void testOnSave() {
        FilterSaveEvent filterSaveEvent = createMock(FilterSaveEvent.class);
        expect(filterSaveEvent.getSelectedItemsIds()).andReturn(Collections.singleton(PUBLICATION_FORMAT)).once();
        replay(filterSaveEvent);
        publicationFormatFilterWidget.onSave(filterSaveEvent);
        verify(filterSaveEvent);
    }

    @Test
    public void testShowFilterWindow() {
        mockStatic(Windows.class);
        FilterWindow filterWindow = createMock(FilterWindow.class);
        Capture<ValueProvider<String, List<String>>> providerCapture = newCapture();
        expect(Windows.showFilterWindow(eq("Publication Formats filter"), same(publicationFormatFilterWidget),
            capture(providerCapture))).andReturn(filterWindow).once();
        filterWindow.setSelectedItemsIds(Collections.emptySet());
        expectLastCall().once();
        expect(filterWindow.getId()).andReturn("id").once();
        filterWindow.addStyleName("publication-format-filter-window");
        expectLastCall().once();
        filterWindow.setSelectAllButtonVisible();
        expectLastCall().once();
        filterWindow.setSearchPromptString("Enter Publication Format");
        expectLastCall().once();
        replay(filterWindow, Windows.class);
        publicationFormatFilterWidget.showFilterWindow();
        assertEquals(List.of(PUBLICATION_FORMAT), providerCapture.getValue().apply(PUBLICATION_FORMAT));
        verify(filterWindow, Windows.class);
    }
}
