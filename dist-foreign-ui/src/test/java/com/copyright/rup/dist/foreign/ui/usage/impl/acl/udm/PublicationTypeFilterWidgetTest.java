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

import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.data.ValueProvider;

import org.easymock.Capture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link PublicationTypeFilterWidget}.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 01/19/2022
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class PublicationTypeFilterWidgetTest {

    private final PublicationType publicationType = buildPublicationType();
    private final PublicationTypeFilterWidget publicationTypeFilterWidget =
        new PublicationTypeFilterWidget(() -> List.of(publicationType));

    @Test
    public void testLoadBeans() {
        List<PublicationType> publicationTypes = publicationTypeFilterWidget.loadBeans();
        assertEquals(1, publicationTypes.size());
        assertEquals(publicationType, publicationTypes.get(0));
    }

    @Test
    public void testGetBeanClass() {
        assertEquals(PublicationType.class, publicationTypeFilterWidget.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        assertEquals("OT - Other", publicationTypeFilterWidget.getBeanItemCaption(publicationType));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testOnSave() {
        FilterSaveEvent filterSaveEvent = createMock(FilterSaveEvent.class);
        expect(filterSaveEvent.getSelectedItemsIds()).andReturn(Set.of(publicationType)).once();
        replay(filterSaveEvent);
        publicationTypeFilterWidget.onSave(filterSaveEvent);
        verify(filterSaveEvent);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testShowFilterWindow() {
        mockStatic(Windows.class);
        FilterWindow filterWindow = createMock(FilterWindow.class);
        Capture<ValueProvider<PublicationType, List<String>>> providerCapture = newCapture();
        expect(Windows.showFilterWindow(eq("Pub Types filter"), same(publicationTypeFilterWidget),
            capture(providerCapture))).andReturn(filterWindow).once();
        filterWindow.setSelectedItemsIds(Set.of());
        expectLastCall().once();
        expect(filterWindow.getId()).andReturn("id").once();
        filterWindow.addStyleName("publication-type-filter-window");
        expectLastCall().once();
        filterWindow.setSelectAllButtonVisible();
        expectLastCall().once();
        filterWindow.setSearchPromptString("Enter Publication Type");
        expectLastCall().once();
        replay(filterWindow, Windows.class);
        publicationTypeFilterWidget.showFilterWindow();
        assertEquals(Arrays.asList("OT", "Other"), providerCapture.getValue().apply(publicationType));
        verify(filterWindow, Windows.class);
    }

    @Test
    public void testConstructorWithSelectedItems() {
        Set<PublicationType> selectedPublicationTypes = Set.of(publicationType);
        PublicationTypeFilterWidget pubTypeFilterWidget =
            new PublicationTypeFilterWidget(() -> List.of(publicationType), selectedPublicationTypes);
        assertEquals(selectedPublicationTypes, pubTypeFilterWidget.getSelectedItemsIds());
        assertEquals("Pub Types", pubTypeFilterWidget.getComponent(1).getCaption());
    }

    private PublicationType buildPublicationType() {
        PublicationType pubType = new PublicationType();
        pubType.setId("ad8df236-5200-4acf-be55-cf82cd342f14");
        pubType.setName("OT");
        pubType.setDescription("Other");
        return pubType;
    }
}
